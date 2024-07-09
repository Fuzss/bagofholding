package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.world.item.BagType;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilteredBagSlot extends Slot {
   private final BagType type;

   public FilteredBagSlot(BagType type, Container container, int slot, int x, int y) {
      super(container, slot, x, y);
      this.type = type;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return this.type.mayPlaceInBag(stack);
   }
}