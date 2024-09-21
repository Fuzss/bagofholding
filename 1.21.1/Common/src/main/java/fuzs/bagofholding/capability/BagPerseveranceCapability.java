package fuzs.bagofholding.capability;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.capability.v3.data.CapabilityComponent;
import fuzs.puzzleslib.api.container.v1.ContainerSerializationHelper;
import fuzs.puzzleslib.api.init.v3.registry.LookupHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.ItemEnchantments;
import net.minecraft.world.level.GameRules;

import java.util.List;

public class BagPerseveranceCapability extends CapabilityComponent<Player> {
    private static final String TAG_AMOUNT = BagOfHolding.id("amount").toString();
    private static final String TAG_ITEMS = BagOfHolding.id("items").toString();

    public static final Codec<BagPerseveranceCapability> CODEC = ItemStack.CODEC.listOf()
            .xmap(BagPerseveranceCapability::new, capability -> capability.items);

    private List<ItemStack> items;

    private BagPerseveranceCapability(List<ItemStack> items) {
        this.items = items;
    }

    public BagPerseveranceCapability() {
        this(ImmutableList.of());
    }

    public void saveOnDeath() {
        if (!this.getHolder().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            Inventory inventory = this.getHolder().getInventory();
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            for (int i = 0; i < inventory.getContainerSize(); ++i) {
                ItemStack itemStack = inventory.getItem(i);
                Holder<Enchantment> enchantment = LookupHelper.lookup(this.getHolder(),
                        Registries.ENCHANTMENT,
                        ModRegistry.PRESERVATION_ENCHANTMENT
                );
                if (!itemStack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(enchantment, itemStack) > 0) {
                    inventory.removeItemNoUpdate(i);
                    if (!this.getHolder().getAbilities().instabuild && this.getHolder().getRandom().nextDouble() <
                            BagOfHolding.CONFIG.get(ServerConfig.class).preservationLevelLossChance) {
                        EnchantmentHelper.updateEnchantments(itemStack, (ItemEnchantments.Mutable enchantments) -> {
                            enchantments.set(enchantment, enchantments.getLevel(enchantment) - 1);
                        });
                    }
                    builder.add(itemStack);
                }
            }
            this.items = builder.build();
            if (!this.items.isEmpty()) {
                this.setChanged();
            }
        }
    }

    public void restoreAfterRespawn(Player newPlayer) {
        if (!this.items.isEmpty()) {
            this.giveItemsToPlayer(this.items, newPlayer);
            this.items = ImmutableList.of();
            this.setChanged();
        }
    }

    private void giveItemsToPlayer(List<ItemStack> items, Player player) {
        // copied from give command
        for (ItemStack itemstack : items) {
            boolean flag = player.getInventory().add(itemstack);
            if (flag && itemstack.isEmpty()) {
                itemstack.setCount(1);
                ItemEntity itemEntity = player.drop(itemstack, false);
                if (itemEntity != null) {
                    itemEntity.makeFakeItem();
                }
                player.level()
                        .playSound(null,
                                player.getX(),
                                player.getY(),
                                player.getZ(),
                                SoundEvents.ITEM_PICKUP,
                                SoundSource.PLAYERS,
                                0.2F,
                                ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                        );
                player.containerMenu.broadcastChanges();
            } else {
                ItemEntity itemEntity = player.drop(itemstack, false);
                if (itemEntity != null) {
                    itemEntity.setNoPickUpDelay();
                    itemEntity.setTarget(player.getUUID());
                }
            }
        }
    }

    @Override
    public void write(CompoundTag tag, HolderLookup.Provider registries) {
        tag.putByte(TAG_AMOUNT, (byte) this.items.size());
        ContainerSerializationHelper.saveAllItems(TAG_ITEMS,
                tag,
                this.items.size(),
                this.items::get,
                false,
                registries
        );
    }

    @Override
    public void read(CompoundTag tag, HolderLookup.Provider registries) {
        byte amount = tag.getByte(TAG_AMOUNT);
        if (amount != 0) {
            ImmutableList.Builder<ItemStack> builder = ImmutableList.builder();
            ContainerSerializationHelper.loadAllItems(TAG_ITEMS, tag, amount, (element, index) -> {
                builder.add(element);
            }, registries);
            this.items = builder.build();
        }
    }
}
