package fuzs.bagofholding.world.item;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.network.message.S2CLockSlotMessage;
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
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import java.util.Optional;
import java.util.function.IntSupplier;
import java.util.stream.Stream;

public class BagOfHoldingItem extends Item implements Vanishable, RecipesIgnoreTag {
    private final IntSupplier containerRows;
    private final DyeColor backgroundColor;
    private final BagItemMenu.Factory menuFactory;

    public BagOfHoldingItem(Properties p_41383_, IntSupplier containerRows, DyeColor backgroundColor, BagItemMenu.Factory menuFactory) {
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
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        // changes to the tag otherwise trigger the re-equip animation
        return slotChanged;
    }

    @Override
    public boolean overrideStackedOnOther(ItemStack stack, Slot slot, ClickAction clickAction, Player player) {
        return ContainerItemHelper.overrideStackedOnOther(stack.getTag(), stack::getOrCreateTag, this.containerRows.getAsInt(), slot, clickAction, player, BagOfHoldingItem::mayPlaceInBag, SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
    }

    @Override
    public boolean overrideOtherStackedOnMe(ItemStack stack, ItemStack stackOnMe, Slot slot, ClickAction clickAction, Player player, SlotAccess slotAccess) {
        return ContainerItemHelper.overrideOtherStackedOnMe(stack.getTag(), stack::getOrCreateTag, this.containerRows.getAsInt(), stackOnMe, slot, clickAction, player, slotAccess, BagOfHoldingItem::mayPlaceInBag, SoundEvents.BUNDLE_INSERT, SoundEvents.BUNDLE_REMOVE_ONE);
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
            SimpleContainer container = ContainerItemHelper.loadItemContainer(stack.getTag(), stack::getOrCreateTag, this.containerRows.getAsInt());
            return this.menuFactory.create(containerId, inventory, container);
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
        return ContainerItemHelper.getTooltipImage(stack.getTag(), stack::getOrCreateTag, this.containerRows.getAsInt(), this.backgroundColor);
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        Stream.Builder<ItemStack> builder = Stream.builder();
        SimpleContainer container = ContainerItemHelper.loadItemContainer(itemEntity.getItem().getTag(), itemEntity.getItem()::getOrCreateTag, this.containerRows.getAsInt());
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
