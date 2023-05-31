package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class FilteredBagSlot extends Slot {
   private final BagOfHoldingItem.Type type;

   public FilteredBagSlot(BagOfHoldingItem.Type type, Container container, int slot, int x, int y) {
      super(container, slot, x, y);
      this.type = type;
   }

   @Override
   public boolean mayPlace(ItemStack stack) {
      return mayPlaceInBag(this.type, stack);
   }

    public static boolean mayPlaceInBag(BagOfHoldingItem.Type type, ItemStack stack) {
        Item item = stack.getItem();
        if (!type.config().bagWhitelist.isEmpty()) {
            return type.config().bagWhitelist.contains(item);
        }
        if (!item.canFitInsideContainerItems()) {
            return false;
        }
        return !type.config().bagBlacklist.contains(item);
    }
}