package fuzs.bagofholding.common.world.item;

import fuzs.bagofholding.common.BagOfHolding;
import fuzs.bagofholding.common.config.ServerConfig;
import fuzs.bagofholding.common.world.inventory.BagItemMenu;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ItemStorageHolder;
import fuzs.puzzleslib.common.api.container.v1.ContainerMenuHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.component.ItemContainerContents;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BagOfHoldingItem extends Item {

    public BagOfHoldingItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult use(Level level, Player player, InteractionHand hand) {
        if (player.isSecondaryUseActive() || !BagOfHolding.CONFIG.get(ServerConfig.class).sneakToOpenBag) {
            if (level instanceof ServerLevel) {
                ItemStack itemInHand = player.getItemInHand(hand);
                ContainerMenuHelper.openMenu(player,
                        this.getMenuProvider(itemInHand.getHoverName(), hand),
                        new BagItemMenu.Data(itemInHand.typeHolder(), hand));
                player.awardStat(Stats.ITEM_USED.get(this));
            }

            playDropContentsSound(level, player);
            return InteractionResult.SUCCESS;
        } else {
            return super.use(level, player, hand);
        }
    }

    /**
     * @see net.minecraft.world.level.block.state.BlockBehaviour#getMenuProvider(BlockState, Level, BlockPos)
     */
    private MenuProvider getMenuProvider(Component title, InteractionHand hand) {
        return new SimpleMenuProvider((int containerId, Inventory inventory, Player player) -> {
            SlotAccess slotAccess = SlotAccess.forEquipmentSlot(player, hand.asEquipmentSlot());
            ItemStack itemStack = slotAccess.get();
            ItemStorageHolder holder = ItemStorageHolder.ofItem(itemStack);
            Container container = holder.storage().getWritableContainer(itemStack, player, slotAccess);
            return new BagItemMenu(containerId, inventory, container, holder, hand);
        }, title);
    }

    /**
     * @see net.minecraft.world.item.BundleItem#playDropContentsSound(Level, Entity)
     */
    private static void playDropContentsSound(Level level, Entity entity) {
        level.playSound(null,
                entity.blockPosition(),
                SoundEvents.BUNDLE_DROP_CONTENTS,
                SoundSource.PLAYERS,
                0.8F,
                0.8F + entity.level().getRandom().nextFloat() * 0.4F);
    }

    /**
     * @see net.minecraft.world.item.BlockItem#onDestroyed(ItemEntity)
     */
    @Override
    public void onDestroyed(ItemEntity itemEntity) {
        ItemContainerContents container = itemEntity.getItem()
                .set(DataComponents.CONTAINER, ItemContainerContents.EMPTY);
        if (container != null) {
            ItemUtils.onContainerDestroyed(itemEntity, container.nonEmptyItemCopyStream());
        }
    }

    @Override
    public boolean canFitInsideContainerItems() {
        return false;
    }
}
