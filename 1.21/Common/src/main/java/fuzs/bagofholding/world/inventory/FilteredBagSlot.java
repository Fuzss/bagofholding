package fuzs.bagofholding.world.inventory;

import fuzs.iteminteractions.api.v1.provider.ItemContentsBehavior;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class FilteredBagSlot extends Slot {
   private final ItemContentsBehavior behavior;

   public FilteredBagSlot(ItemContentsBehavior behavior, Container container, int slot, int x, int y) {
      super(container, slot, x, y);
      this.behavior = behavior;
   }

   @Override
   public boolean mayPlace(ItemStack itemStack) {
      return this.behavior.isItemAllowedInContainer(ItemStack.EMPTY, itemStack);
   }
}