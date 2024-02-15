package fuzs.bagofholding.world.item;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.network.S2CLockSlotMessage;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.iteminteractions.api.v1.ContainerItemHelper;
import fuzs.iteminteractions.api.v1.provider.ItemContainerProvider;
import net.minecraft.core.NonNullList;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.stats.Stats;
import net.minecraft.world.*;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Vanishable;
import net.minecraft.world.level.Level;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class BagOfHoldingItem extends Item implements Vanishable, RecipesIgnoreTag {
    private final BagType type;

    public BagOfHoldingItem(Properties properties, BagType type) {
        super(properties);
        this.type = type;
    }

    @Override
    public boolean isEnchantable(ItemStack itemStack) {
        return itemStack.getCount() == 1;
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
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand interactionHand) {
        if (player.isSecondaryUseActive() || !BagOfHolding.CONFIG.get(ServerConfig.class).sneakToOpenBag) {
            ItemStack itemInHand = player.getItemInHand(interactionHand);
            if (!level.isClientSide) {
                player.openMenu(this.getMenuProvider(itemInHand));
                player.awardStat(Stats.ITEM_USED.get(this));
                this.lockMySlot(player, itemInHand);
            }
            player.playSound(SoundEvents.BUNDLE_DROP_CONTENTS, 0.8F, 0.8F + level.getRandom().nextFloat() * 0.4F);
            return InteractionResultHolder.sidedSuccess(itemInHand, level.isClientSide);
        } else {
            return super.use(level, player, interactionHand);
        }
    }

    private MenuProvider getMenuProvider(ItemStack stack) {
        return new SimpleMenuProvider((containerId, inventory, player) -> {
            ItemContainerProvider provider = ContainerItemHelper.INSTANCE.getItemContainerProvider(stack);
            Objects.requireNonNull(provider, "provider is null");
            SimpleContainer container = provider.getItemContainer(stack, player, true);
            return new BagItemMenu(this.type, containerId, inventory, container);
        }, stack.getHoverName());
    }

    private void lockMySlot(Player player, ItemStack itemStack) {
        if (!(player.containerMenu instanceof BagItemMenu menu)) return;
        NonNullList<ItemStack> items = menu.getItems();
        for (int i = 0; i < items.size(); i++) {
            if (items.get(i) == itemStack) {
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
}
