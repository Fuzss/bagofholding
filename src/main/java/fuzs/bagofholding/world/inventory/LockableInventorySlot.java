package fuzs.bagofholding.world.inventory;

import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class LockableInventorySlot extends Slot {
   private boolean locked;

   public LockableInventorySlot(Container p_40202_, int p_40203_, int p_40204_, int p_40205_) {
      super(p_40202_, p_40203_, p_40204_, p_40205_);
   }

   @Override
   public boolean mayPlace(ItemStack p_40231_) {
      return !this.locked;
   }

   @Override
   public boolean mayPickup(Player p_40228_) {
      return !this.locked;
   }

   public void lock() {
      this.locked = true;
   }

   public boolean locked() {
      return this.locked;
   }
}