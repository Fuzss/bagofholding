package fuzs.bagofholding.world.item.enchantment;

import fuzs.bagofholding.registry.ModRegistry;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.enchantment.Enchantment;

public class PreservationEnchantment extends Enchantment {
   public PreservationEnchantment(Rarity p_44648_, EquipmentSlot... p_44649_) {
      super(p_44648_, ModRegistry.BAG_OF_HOLDING_ENCHANTMENT_CATEGORY, p_44649_);
   }

   @Override
   public int getMinCost(int p_44652_) {
      return 5 + (p_44652_ - 1) * 8;
   }

   @Override
   public int getMaxCost(int p_44660_) {
      return super.getMinCost(p_44660_) + 50;
   }

   @Override
   public int getMaxLevel() {
      return 3;
   }
}