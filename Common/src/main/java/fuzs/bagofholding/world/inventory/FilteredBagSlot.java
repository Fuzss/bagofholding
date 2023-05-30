package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilteredBagSlot extends Slot {
   private final BagOfHoldingItem.Type type;

   public FilteredBagSlot(BagOfHoldingItem.Type type, Container container, int slot, int x, int y) {
      super(container, slot, x, y);
      this.type = type;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return BagOfHoldingItem.mayPlaceInBag(this.type, stack);
   }
}