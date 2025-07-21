package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.container.BagProvider;
import fuzs.iteminteractions.api.v1.DyeBackedColor;
import fuzs.iteminteractions.api.v1.ItemContentsHelper;
import fuzs.iteminteractions.api.v1.provider.ItemContentsBehavior;
import net.minecraft.core.Holder;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BagItemMenu extends AbstractContainerMenu {
    private final Container container;
    private final ItemContentsBehavior behavior;
    private final int hotbarStartIndex;

    public BagItemMenu(int containerId, Inventory inventory, Holder<Item> item) {
        this(containerId, inventory, ItemContentsHelper.getItemContentsBehavior(new ItemStack(item)));
    }

    private BagItemMenu(int containerId, Inventory inventory, ItemContentsBehavior behavior) {
        this(containerId,
                inventory,
                new SimpleContainer(((BagProvider) behavior.provider()).getInventoryHeight() * 9),
                behavior);
    }

    public BagItemMenu(int containerId, Inventory inventory, Container container, ItemContentsBehavior behavior) {
        super(ModRegistry.BAG_MENU_TYPE.value(), containerId);
        this.behavior = behavior;
        checkContainerSize(container, this.getInventoryHeight() * 9);
        this.container = container;
        container.startOpen(inventory.player);
        int i = (this.getInventoryHeight() - 4) * 18;
        for (int j = 0; j < this.getInventoryHeight(); ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new FilteredBagSlot(behavior, container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new LockableInventorySlot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }
        int hotbarStartIndex = 0;
        for (int i1 = 0; i1 < 9; ++i1) {
            Slot hotbarSlot = this.addSlot(new LockableInventorySlot(inventory, i1, 8 + i1 * 18, 161 + i));
            if (i1 == 0) {
                hotbarStartIndex = hotbarSlot.index;
            }
        }
        this.hotbarStartIndex = hotbarStartIndex;
    }

    @Override
    public boolean stillValid(Player player) {
        return this.container.stillValid(player);
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemStack = itemstack1.copy();
            if (index < this.container.getContainerSize()) {
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

        return itemStack;
    }

    @Override
    public void removed(Player player) {
        super.removed(player);
        this.container.stopOpen(player);
    }

    public int getInventoryHeight() {
        return ((BagProvider) this.behavior.provider()).getInventoryHeight();
    }

    public @Nullable DyeBackedColor getBackgroundColor() {
        return ((BagProvider) this.behavior.provider()).getBackgroundColor();
    }

    public int getHotbarStartIndex() {
        return this.hotbarStartIndex;
    }
}