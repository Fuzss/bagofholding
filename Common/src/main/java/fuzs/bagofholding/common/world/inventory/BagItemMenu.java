package fuzs.bagofholding.common.world.inventory;

import fuzs.bagofholding.common.init.ModRegistry;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ContainerStorage;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ItemStorageHolder;
import fuzs.puzzleslib.common.api.container.v1.ContainerMenuHelper;
import net.minecraft.core.Holder;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.inventory.ContainerInput;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BagItemMenu extends ChestMenu {
    private final ItemStorageHolder holder;

    public BagItemMenu(int containerId, Inventory inventory, Holder<Item> item) {
        this(containerId, inventory, ItemStorageHolder.ofItem(new ItemStack(item)));
    }

    private BagItemMenu(int containerId, Inventory inventory, ItemStorageHolder holder) {
        this(containerId,
                inventory,
                new SimpleContainer(
                        holder.storage() instanceof ContainerStorage storage ? storage.getInventorySize() : 27),
                holder);
    }

    public BagItemMenu(int containerId, Inventory inventory, Container container, ItemStorageHolder holder) {
        this.holder = holder;
        super(ModRegistry.BAG_MENU_TYPE.value(),
                containerId,
                inventory,
                container,
                holder.storage() instanceof ContainerStorage storage ? storage.getInventoryHeight() : 3);
        ContainerMenuHelper.setSelectedSlotLocked(this);
    }

    @Override
    public void addChestGrid(Container container, int left, int top) {
        for (int y = 0; y < this.getRowCount(); ++y) {
            for (int x = 0; x < 9; ++x) {
                this.addSlot(new ItemStorageSlot(this.holder,
                        container,
                        x + y * 9,
                        left + x * SLOT_SIZE,
                        top + y * SLOT_SIZE));
            }
        }
    }

    @Override
    public void clicked(int slotIndex, int buttonNum, ContainerInput containerInput, Player player) {
        if (!this.stillValid(player)) {
            return;
        }

        if (containerInput != ContainerInput.SWAP || buttonNum != player.getInventory().getSelectedSlot()) {
            super.clicked(slotIndex, buttonNum, containerInput, player);
        }
    }
}
