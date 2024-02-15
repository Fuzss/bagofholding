package fuzs.bagofholding.client.handler;

import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;

public class SlotOverlayHandler {

    public static void onDrawForeground(AbstractContainerScreen<?> screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // use this event since it runs before dragged item stack is rendered, so we can render behind
        if (screen instanceof BagItemScreen bagItemScreen) {
            findLockedSlot(bagItemScreen).ifPresent(slot -> AbstractContainerScreen.renderSlotHighlight(guiGraphics, slot.x, slot.y, 0));
        }
    }

    private static Optional<Slot> findLockedSlot(BagItemScreen screen) {
        for (int i = screen.getMenu().slots.size() - 1; i >= 0; i--) {
            Slot slot = screen.getMenu().slots.get(i);
            if (!screen.isHoveredSlot(slot) && slot instanceof LockableInventorySlot lockableSlot && lockableSlot.locked()) {
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }
}
