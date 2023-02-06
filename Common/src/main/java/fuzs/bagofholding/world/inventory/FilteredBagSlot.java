package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilteredBagSlot extends Slot {
   private final BagOfHoldingItem.Type type;

   public FilteredBagSlot(BagOfHoldingItem.Type type, Container p_40202_, int p_40203_, int p_40204_, int p_40205_) {
      super(p_40202_, p_40203_, p_40204_, p_40205_);
      this.type = type;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return BagOfHoldingItem.mayPlaceInBag(this.type, stack);
   }

   @Override
   public int getMaxStackSize(ItemStack stack) {
      return this.getMaxStackSize();
   }
}