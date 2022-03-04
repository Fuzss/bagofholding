package fuzs.bagofholding.world.item;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.network.message.S2CLockSlotMessage;
import fuzs.bagofholding.world.inventory.BagInventorySlot;
import fuzs.bagofholding.world.inventory.BagMenu;
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
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.stream.Stream;

public class BagItem extends Item implements Vanishable, RecipesIgnoreTag {
    private final int containerRows;
    private final DyeColor backgroundColor;
    private final BagMenu.BagOfHoldingMenuFactory menuFactory;

    public BagItem(Properties p_41383_, int containerRows, DyeColor backgroundColor, BagMenu.BagOfHoldingMenuFactory menuFactory) {
        super(p_41383_);
        this.backgroundColor = backgroundColor;
        this.containerRows = containerRows;
        this.menuFactory = menuFactory;
    }

    @Override
    public int getEnchantmentValue() {
        // required to be able to apply perseverance enchantment on enchanting tables
        return 1;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickAction, Player player) {
        return ContainerItemHelper.overrideStackedOnOther(stack.getTag(), stack::getOrCreateTag, this.containerRows, slot, clickAction, player, SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack stackOnMe, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        return ContainerItemHelper.overrideOtherStackedOnMe(stack.getTag(), stack::getOrCreateTag, this.containerRows, stackOnMe, slot, clickAction, player, slotAccess, SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
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
            SimpleContainer container = ContainerItemHelper.loadItemContainer(stack.getTag(), stack::getOrCreateTag, this.containerRows);
            return this.menuFactory.create(containerId, inventory, container);
        }, stack.getHoverName());
    }

    private void lockMySlot(Player player, ItemStack stack) {
        if (!(player.containerMenu instanceof BagMenu menu)) return;
        NonNullList<ItemStack> items = menu.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == stack) {
                ((BagInventorySlot) menu.getSlot(i)).lock();
                BagOfHolding.NETWORK.sendTo(new S2CLockSlotMessage(menu.containerId, i), (ServerPlayer) player);
                return;
            }
        }
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return ContainerItemHelper.getTooltipImage(stack.getTag(), stack::getOrCreateTag, this.containerRows, this.backgroundColor);
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        Stream.Builder<ItemStack> builder = Stream.builder();
        SimpleContainer container = ContainerItemHelper.loadItemContainer(itemEntity.getItem().getTag(), itemEntity.getItem()::getOrCreateTag, this.containerRows);
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

    public static boolean mayPlaceInBag(ItemStack stack) {
        Item item = stack.getItem();
        if (!item.canFitInsideContainerItems()) {
            return false;
        }
        return !BagOfHolding.CONFIG.server().bagBlacklist.contains(stack.getItem());
    }
}
