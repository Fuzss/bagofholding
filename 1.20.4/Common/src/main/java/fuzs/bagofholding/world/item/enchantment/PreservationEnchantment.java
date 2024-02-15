package fuzs.bagofholding.world.item.enchantment;

import fuzs.bagofholding.init.ModRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class PreservationEnchantment extends Enchantment {

   public PreservationEnchantment(Rarity rarity, EquipmentSlot... equipmentSlots) {
      super(rarity, ModRegistry.BAG_OF_HOLDING_ENCHANTMENT_CATEGORY, equipmentSlots);
   }

   @Override
   public int getMinCost(int level) {
      return 5 + (level - 1) * 8;
   }

   @Override
   public int getMaxCost(int level) {
      return super.getMinCost(level) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}