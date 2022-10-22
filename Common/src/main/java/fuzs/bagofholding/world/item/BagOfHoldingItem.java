package fuzs.bagofholding.world.item;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.api.world.item.container.ContainerItemHelper;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.network.S2CLockSlotMessage;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.stream.Stream;

public class BagOfHoldingItem extends Item implements Vanishable, RecipesIgnoreTag {
    public final Type type;

    public BagOfHoldingItem(Properties properties, Type type) {
        super(properties);
        this.type = type;
    }

    public int getContainerRows() {
        return this.type.config().rows;
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return stack.getCount() == 1;
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickAction, Player player) {
        return ContainerItemHelper.overrideStackedOnOther(stack, null, this.getContainerRows(), slot, clickAction, player, this::mayPlaceInBag, SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack stackOnMe, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        return ContainerItemHelper.overrideOtherStackedOnMe(stack, null, this.getContainerRows(), stackOnMe, slot, clickAction, player, slotAccess, this::mayPlaceInBag, SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.openMenu(this.getMenuProvider(stack));
            this.playDropContentsSound(player);
            player.awardStat(Stats.ITEM_USED.get(this));
            this.lockMySlot(player, stack);
        }
        return InteractionResultHolder.consume(stack);
    }

    private MenuProvider getMenuProvider(ItemStack stack) {
        return new SimpleMenuProvider((containerId, inventory, player) -> {
            SimpleContainer container = ContainerItemHelper.loadItemContainer(stack, null, this.getContainerRows());
            return new BagItemMenu(this.type, containerId, inventory, container);
        }, stack.getHoverName());
    }

    private void lockMySlot(Player player, ItemStack stack) {
        if (!(player.containerMenu instanceof BagItemMenu menu)) return;
        NonNullList<ItemStack> items = menu.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == stack) {
                ((LockableInventorySlot) menu.getSlot(i)).lock();
                BagOfHolding.NETWORK.sendTo(new S2CLockSlotMessage(menu.containerId, i), (ServerPlayer) player);
                return;
            }
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return ContainerItemHelper.getTooltipImage(stack, null, this.getContainerRows(), this.type.backgroundColor);
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        Stream.Builder<ItemStack> builder = Stream.builder();
        SimpleContainer container = ContainerItemHelper.loadItemContainer(itemEntity.getItem(), null, this.getContainerRows());
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!stack.isEmpty()) {
                builder.add(stack);
            }
        }
        ItemUtils.onContainerDestroyed(itemEntity, builder.build());
    }

    private void playDropContentsSound(Entity entity) {
        entity.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + entity.getLevel().getRandom().nextFloat() * 0.4F);
    }

    private boolean mayPlaceInBag(ItemStack stack) {
        return mayPlaceInBag(this.type, stack);
    }

    public static boolean mayPlaceInBag(Type type, ItemStack stack) {
        Item item = stack.getItem();
        if (!type.config().bagWhitelist.isEmpty()) {
            return type.config().bagWhitelist.contains(item);
        }
        if (!item.canFitInsideContainerItems()) {
            return false;
        }
        return !type.config().bagBlacklist.contains(item);
    }
    
    public enum Type {
        LEATHER(DyeColor.BROWN),
        IRON(DyeColor.WHITE),
        GOLDEN(DyeColor.YELLOW);
        
        public final DyeColor backgroundColor;

        Type(DyeColor backgroundColor) {
            this.backgroundColor = backgroundColor;
        }
        
        public ServerConfig.BagOfHoldingConfig config() {
            return switch (this) {
                case LEATHER -> BagOfHolding.CONFIG.get(ServerConfig.class).leatherBagConfig;
                case IRON -> BagOfHolding.CONFIG.get(ServerConfig.class).ironBagConfig;
                case GOLDEN -> BagOfHolding.CONFIG.get(ServerConfig.class).goldenBagConfig;
            };
        }

        public MenuType<? extends BagItemMenu> menuType() {
            return switch (this) {
                case LEATHER -> ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.get();
                case IRON -> ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.get();
                case GOLDEN -> ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.get();
            };
        }
    }
}
