package fuzs.bagofholding.capability;

import com.google.common.collect.Lists;
import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.capability.v3.data.CapabilityComponent;
import fuzs.puzzleslib.api.container.v1.ContainerSerializationHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.GameRules;

import java.util.List;

public class BagPerseveranceCapability extends CapabilityComponent<Player> {
    private static final String TAG_AMOUNT = BagOfHolding.id("amount").toString();
    private static final String TAG_ITEMS = BagOfHolding.id("items").toString();

    private final List<ItemStack> items = Lists.newArrayList();

    public void saveOnDeath() {
        if (this.getHolder().level().getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) return;
        Inventory inventory = this.getHolder().getInventory();
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemStack = inventory.getItem(i);
            if (!itemStack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(ModRegistry.PRESERVATION_ENCHANTMENT.value(), itemStack) > 0) {
                inventory.removeItemNoUpdate(i);
                if (!this.getHolder().getAbilities().instabuild && this.getHolder().getRandom().nextDouble() < BagOfHolding.CONFIG.get(
                        ServerConfig.class).preservationLevelLossChance) {
                    decreaseEnchantmentLevel(ModRegistry.PRESERVATION_ENCHANTMENT.value(), itemStack);
                }
                this.items.add(itemStack);
            }
        }
        if (!this.items.isEmpty()) {
            this.setChanged();
        }
    }

    private static void decreaseEnchantmentLevel(Enchantment enchantment, ItemStack stack) {
        ResourceLocation resourcelocation = EnchantmentHelper.getEnchantmentId(enchantment);
        ListTag listtag = stack.getEnchantmentTags();
        int enchantmentIndex = -1;
        for (int i = 0; i < listtag.size(); ++i) {
            CompoundTag compoundtag = listtag.getCompound(i);
            ResourceLocation resourcelocation1 = EnchantmentHelper.getEnchantmentId(compoundtag);
            if (resourcelocation1 != null && resourcelocation1.equals(resourcelocation)) {
                enchantmentIndex = i;
                break;
            }
        }
        if (enchantmentIndex != -1) {
            CompoundTag compoundtag = listtag.getCompound(enchantmentIndex);
            int level = EnchantmentHelper.getEnchantmentLevel(compoundtag);
            if (level > 1) {
                EnchantmentHelper.setEnchantmentLevel(compoundtag, level - 1);
            } else {
                listtag.remove(enchantmentIndex);
            }
        }
    }

    public void restoreAfterRespawn(Player newPlayer) {
        if (!this.items.isEmpty()) {
            this.giveItemsToPlayer(this.items, newPlayer);
            this.items.clear();
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
    public void write(CompoundTag tag) {
        tag.putByte(TAG_AMOUNT, (byte) this.items.size());
        ContainerSerializationHelper.saveAllItems(TAG_ITEMS, tag, this.items.size(), this.items::get, false);
    }

    @Override
    public void read(CompoundTag tag) {
        byte amount = tag.getByte(TAG_AMOUNT);
        if (amount != 0) {
            ContainerSerializationHelper.loadAllItems(TAG_ITEMS, tag, amount, (element, index) -> {
                this.items.add(element);
            });
        }
    }
}
