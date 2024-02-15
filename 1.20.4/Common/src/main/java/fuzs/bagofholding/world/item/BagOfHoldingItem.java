package fuzs.bagofholding.world.item;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.CommonConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.network.S2CLockSlotMessage;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.puzzlesapi.api.iteminteractions.v1.ContainerItemHelper;
import fuzs.puzzlesapi.api.iteminteractions.v1.provider.ItemContainerProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BagOfHoldingItem extends Item implements Vanishable, RecipesIgnoreTag {
    private final Type type;

    public BagOfHoldingItem(Properties properties, Type type) {
        super(properties);
        this.type = type;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (!level.isClientSide) {
            player.openMenu(this.getMenuProvider(stack));
            player.awardStat(Stats.ITEM_USED.get(this));
            this.lockMySlot(player, stack);
        }
        player.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide);
    }

    private MenuProvider getMenuProvider(ItemStack stack) {
        return new SimpleMenuProvider((containerId, inventory, player) -> {
            ItemContainerProvider provider = ContainerItemHelper.INSTANCE.getItemContainerProvider(stack);
            Objects.requireNonNull(provider, "provider is null");
            SimpleContainer container = provider.getItemContainer(stack, player, true);
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
    public void onDestroyed(ItemEntity itemEntity) {
        ItemContainerProvider provider = ContainerItemHelper.INSTANCE.getItemContainerProvider(itemEntity.getItem());
        Objects.requireNonNull(provider, "provider is null");
        SimpleContainer container = provider.getItemContainer(itemEntity.getItem(), null, true);
        Stream<ItemStack> stream = ContainerItemHelper.INSTANCE.getListFromContainer(container).stream().filter(Predicate.not(ItemStack::isEmpty));
        ItemUtils.onContainerDestroyed(itemEntity, stream);
    }

    public enum Type {
        LEATHER(DyeColor.BROWN, DyeColor.WHITE),
        IRON(DyeColor.LIGHT_GRAY, DyeColor.WHITE),
        GOLDEN(DyeColor.YELLOW, DyeColor.WHITE);

        public final DyeColor backgroundColor;
        public final DyeColor textColor;

        Type(DyeColor backgroundColor, DyeColor textColor) {
            this.backgroundColor = backgroundColor;
            this.textColor = textColor;
        }
        
        public CommonConfig.BagOfHoldingConfig config() {
            return switch (this) {
                case LEATHER -> BagOfHolding.CONFIG.get(CommonConfig.class).leatherBagConfig;
                case IRON -> BagOfHolding.CONFIG.get(CommonConfig.class).ironBagConfig;
                case GOLDEN -> BagOfHolding.CONFIG.get(CommonConfig.class).goldenBagConfig;
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
