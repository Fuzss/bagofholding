package fuzs.bagofholding.world.item;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.network.S2CLockSlotMessage;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.iteminteractions.api.v1.ItemContentsHelper;
import fuzs.iteminteractions.api.v1.provider.ItemContentsBehavior;
import fuzs.puzzleslib.api.util.v1.InteractionResultHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;
import java.util.stream.Stream;

public class BagOfHoldingItem extends Item {
    private final Holder<MenuType<BagItemMenu>> menuType;

    public BagOfHoldingItem(Properties properties, Holder<MenuType<BagItemMenu>> menuType) {
        super(properties);
        this.menuType = menuType;
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand interactionHand) {
        if (player.isSecondaryUseActive() || !BagOfHolding.CONFIG.get(ServerConfig.class).sneakToOpenBag) {
            ItemStack itemInHand = player.getItemInHand(interactionHand);
            if (!level.isClientSide) {
                player.openMenu(this.getMenuProvider(itemInHand));
                player.awardStat(Stats.ITEM_USED.get(this));
                this.lockMySlot(player, itemInHand);
            }
            player.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            return InteractionResultHelper.sidedSuccess(itemInHand, level.isClientSide);
        } else {
            return super.use(level, player, interactionHand);
        }
    }

    private MenuProvider getMenuProvider(ItemStack itemStack) {
        return new SimpleMenuProvider((containerId, inventory, player) -> {
            ItemContentsBehavior behavior = ItemContentsHelper.getItemContentsBehavior(itemStack);
            SimpleContainer itemContainer = behavior.getItemContainer(itemStack, player);
            return new BagItemMenu(this.menuType, behavior, containerId, inventory, itemContainer);
        }, itemStack.getHoverName());
    }

    private void lockMySlot(Player player, ItemStack itemStack) {
        if (!(player.containerMenu instanceof BagItemMenu menu)) return;
        NonNullList<ItemStack> items = menu.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == itemStack) {
                ((LockableInventorySlot) menu.getSlot(i)).lock();
                BagOfHolding.NETWORK.sendTo((ServerPlayer) player,
                        new S2CLockSlotMessage(menu.containerId, i).toClientboundMessage());
                return;
            }
        }
    }

    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        SimpleContainer container = ItemContentsHelper.getItemContentsBehavior(itemEntity.getItem())
                .getItemContainer(itemEntity.getItem(), null);
        Stream<ItemStack> stream = container.getItems().stream().filter(Predicate.not(ItemStack::isEmpty));
        ItemUtils.onContainerDestroyed(itemEntity, stream.toList());
    }
}
