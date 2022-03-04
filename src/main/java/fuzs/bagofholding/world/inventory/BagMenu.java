package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.registry.ModRegistry;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BagMenu extends AbstractContainerMenu {
   private final Container container;
   private final int containerRows;

   public static BagMenu oneRow(int containerId, Inventory inventory) {
      return new BagMenu(ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.get(), containerId, inventory, 1);
   }

   public static BagMenu threeRows(int containerId, Inventory inventory) {
      return new BagMenu(ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.get(), containerId, inventory, 3);
   }

   public static BagMenu sixRows(int containerId, Inventory inventory) {
      return new BagMenu(ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.get(), containerId, inventory, 6);
   }

   public static BagMenu oneRow(int containerId, Inventory inventory, Container container) {
      return new BagMenu(ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.get(), containerId, inventory, container, 1);
   }

   public static BagMenu threeRows(int containerId, Inventory inventory, Container container) {
      return new BagMenu(ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.get(), containerId, inventory, container, 3);
   }

   public static BagMenu sixRows(int containerId, Inventory inventory, Container container) {
      return new BagMenu(ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.get(), containerId, inventory, container, 6);
   }

   private BagMenu(MenuType<?> menuType, int containerId, Inventory inventory, int containerRows) {
      this(menuType, containerId, inventory, new BagContainer(containerRows), containerRows);
   }

   public BagMenu(MenuType<?> menuType, int containerId, Inventory inventory, Container container, int containerRows) {
      super(menuType, containerId);
      this.containerRows = containerRows;
      checkContainerSize(container, containerRows * 9);
      this.container = container;
      container.startOpen(inventory.player);
      int i = (containerRows - 4) * 18;
      for(int j = 0; j < containerRows; ++j) {
         for(int k = 0; k < 9; ++k) {
            this.addSlot(new BagSlot(container, k + j * 9, 8 + k * 18, 18 + j * 18));
         }
      }
      for(int l = 0; l < 3; ++l) {
         for(int j1 = 0; j1 < 9; ++j1) {
            this.addSlot(new BagInventorySlot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
         }
      }
      for(int i1 = 0; i1 < 9; ++i1) {
         this.addSlot(new BagInventorySlot(inventory, i1, 8 + i1 * 18, 161 + i));
      }

   }

   @Override
   public boolean stillValid(Player p_40195_) {
      return this.container.stillValid(p_40195_);
   }

   @Override
   public ItemStack quickMoveStack(Player p_40199_, int p_40200_) {
      ItemStack itemstack = ItemStack.EMPTY;
      Slot slot = this.slots.get(p_40200_);
      if (slot != null && slot.hasItem()) {
         ItemStack itemstack1 = slot.getItem();
         itemstack = itemstack1.copy();
         if (p_40200_ < this.container.getContainerSize()) {
            if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) {
               return ItemStack.EMPTY;
            }
         } else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) {
            return ItemStack.EMPTY;
         }

         if (itemstack1.isEmpty()) {
            slot.set(ItemStack.EMPTY);
         } else {
            slot.setChanged();
         }
      }

      return itemstack;
   }

   @Override
   public void removed(Player p_40197_) {
      super.removed(p_40197_);
      this.container.stopOpen(p_40197_);
   }

   public int getRowCount() {
      return this.containerRows;
   }

   @FunctionalInterface
   public interface BagOfHoldingMenuFactory {
      BagMenu create(int containerId, Inventory inventory, Container container);
   }
}