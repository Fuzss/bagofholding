package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilteredBagSlot extends Slot {
   public FilteredBagSlot(Container p_40202_, int p_40203_, int p_40204_, int p_40205_) {
      super(p_40202_, p_40203_, p_40204_, p_40205_);
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return BagOfHoldingItem.mayPlaceInBag(stack);
   }
}