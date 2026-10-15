package fuzs.bagofholding.common.world.inventory;

import fuzs.bagofholding.common.init.ModRegistry;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ContainerStorage;
import fuzs.iteminteractions.common.api.v2.world.item.storage.ItemStorageHolder;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BagItemMenu extends ChestMenu {
    private final ItemStorageHolder holder;

    public BagItemMenu(int containerId, Inventory inventory, Data data) {
        this(containerId, inventory, ItemStorageHolder.ofItem(new ItemStack(data.item())), data.hand());
    }

    private BagItemMenu(int containerId, Inventory inventory, ItemStorageHolder holder, InteractionHand hand) {
        this(containerId,
                inventory,
                new SimpleContainer(
                        holder.storage() instanceof ContainerStorage storage ? storage.getInventorySize() : 0),
                holder,
                hand);
    }

    public BagItemMenu(int containerId, Inventory inventory, Container container, ItemStorageHolder holder, InteractionHand hand) {
        this.holder = holder;
        super(ModRegistry.BAG_MENU_TYPE.value(),
                containerId,
                inventory,
                container,
                holder.storage() instanceof ContainerStorage storage ? storage.getInventoryHeight() : 0);
        setSlotLocked(this, hand.asEquipmentSlot());
    }

    /**
     * TODO replace with method from Puzzles Lib
     */
    @Deprecated
    public static void setSlotLocked(AbstractContainerMenu containerMenu, EquipmentSlot slot) {
        for (int slotNum = 0; slotNum < containerMenu.slots.size(); slotNum++) {
            Slot originalSlot = containerMenu.slots.get(slotNum);
            if (originalSlot.container instanceof Inventory inventory && originalSlot.getContainerSlot() == getSlotNum(
                    inventory,
                    slot)) {
                NonInteractiveResultSlot updatedSlot = new NonInteractiveResultSlot(originalSlot.container,
                        originalSlot.getContainerSlot(),
                        originalSlot.x,
                        originalSlot.y) {
                    @Override
                    public boolean isFake() {
                        return false;
                    }
                };
                updatedSlot.index = originalSlot.index;
                containerMenu.slots.set(slotNum, updatedSlot);
                break;
            }
        }
    }

    /**
     * TODO replace with method from Puzzles Lib
     */
    @Deprecated
    public static int getSlotNum(Inventory inventory, EquipmentSlot slot) {
        for (Int2ObjectMap.Entry<EquipmentSlot> entry : Inventory.EQUIPMENT_SLOT_MAPPING.int2ObjectEntrySet()) {
            if (entry.getValue() == slot) {
                return entry.getIntKey();
            }
        }

        return inventory.getSelectedSlot();
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
    public boolean stillValid(Player player) {
        return super.stillValid(player) && this.getRowCount() > 0;
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

    public record Data(Holder<Item> item, InteractionHand hand) {
        public static final StreamCodec<RegistryFriendlyByteBuf, Data> STREAM_CODEC = StreamCodec.composite(Item.STREAM_CODEC,
                Data::item,
                InteractionHand.STREAM_CODEC,
                Data::hand,
                Data::new);
    }
}
