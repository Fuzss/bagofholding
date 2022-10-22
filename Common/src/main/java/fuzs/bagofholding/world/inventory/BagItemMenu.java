package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.api.world.inventory.SimpleContainerWithSlots;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.puzzleslib.init.builder.ModMenuSupplier;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;

public class BagItemMenu extends AbstractContainerMenu {
    private final Container container;
    private final int containerRows;
    private int hotbarStartIndex;

    public static ModMenuSupplier<BagItemMenu> create(BagOfHoldingItem.Type type) {
        return (int containerId, Inventory inventory) -> new BagItemMenu(type, containerId, inventory);
    }

    private BagItemMenu(BagOfHoldingItem.Type type, int containerId, Inventory inventory) {
        this(type, containerId, inventory, new SimpleContainerWithSlots(type.config().rows));
    }

    public BagItemMenu(BagOfHoldingItem.Type type, int containerId, Inventory inventory, Container container) {
        super(type.menuType(), containerId);
        final int containerRows = type.config().rows;
        this.containerRows = containerRows;
        checkContainerSize(container, containerRows * 9);
        this.container = container;
        container.startOpen(inventory.player);
        int i = (containerRows - 4) * 18;
        for (int j = 0; j < containerRows; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new FilteredBagSlot(type, container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new LockableInventorySlot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            Slot hotbarSlot = this.addSlot(new LockableInventorySlot(inventory, i1, 8 + i1 * 18, 161 + i));
            if (i1 == 0) {
                this.hotbarStartIndex = hotbarSlot.index;
            }
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

    public int getHotbarStartIndex() {
        return this.hotbarStartIndex;
    }

    @FunctionalInterface
    public interface Factory {
        BagItemMenu create(int containerId, Inventory inventory, Container container);
    }
}