package fuzs.bagofholding.capability;

import com.google.common.collect.Lists;
import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
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

public class BagPerseveranceCapabilityImpl implements BagPerseveranceCapability {
    private final List<ItemStack> bags = Lists.newArrayList();

    @Override
    public void saveOnDeath(Player player) {
        if (player.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) return;
        Inventory inventory = player.getInventory();
        for (int i = 0; i < inventory.getContainerSize(); ++i) {
            ItemStack itemstack = inventory.getItem(i);
            if (!itemstack.isEmpty() && EnchantmentHelper.getItemEnchantmentLevel(ModRegistry.PRESERVATION_ENCHANTMENT.get(), itemstack) > 0) {
                inventory.removeItemNoUpdate(i);
                if (!player.getAbilities().instabuild && player.getRandom().nextDouble() < BagOfHolding.CONFIG.get(ServerConfig.class).preservationLevelLossChance) {
                    this.decreaseEnchantmentLevel(ModRegistry.PRESERVATION_ENCHANTMENT.get(), itemstack);
                }
                this.bags.add(itemstack);
            }
        }
    }

    private void decreaseEnchantmentLevel(Enchantment enchantment, ItemStack stack) {
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

    @Override
    public void restoreAfterRespawn(Player player) {
        this.giveItemsToPlayer(this.bags, player);
        this.bags.clear();
    }

    private void giveItemsToPlayer(List<ItemStack> items, Player player) {
        // copied from give command
        for (ItemStack itemstack : items) {
            boolean flag = player.getInventory().add(itemstack);
            if (flag && itemstack.isEmpty()) {
                itemstack.setCount(1);
                ItemEntity itementity1 = player.drop(itemstack, false);
                if (itementity1 != null) {
                    itementity1.makeFakeItem();
                }
                player.level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, ((player.getRandom().nextFloat() - player.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F);
                player.containerMenu.broadcastChanges();
            } else {
                ItemEntity itementity = player.drop(itemstack, false);
                if (itementity != null) {
                    itementity.setNoPickUpDelay();
                    itementity.setOwner(player.getUUID());
                }
            }
        }
    }

    @Override
    public void write(CompoundTag tag) {
        ListTag listtag = new ListTag();
        for (ItemStack itemstack : this.bags) {
            if (!itemstack.isEmpty()) {
                listtag.add(itemstack.save(new CompoundTag()));
            }
        }
        tag.put("Items", listtag);
    }

    @Override
    public void read(CompoundTag tag) {
        if (tag.contains("Items")) {
            ListTag items = tag.getList("Items", 10);
            for (int i = 0; i < items.size(); ++i) {
                ItemStack itemstack = ItemStack.of(items.getCompound(i));
                if (!itemstack.isEmpty()) {
                    this.bags.add(itemstack);
                }
            }
        }
    }
}
