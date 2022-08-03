package fuzs.bagofholding.client.handler;

import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;

public class SlotOverlayHandler {

    public void onDrawForeground(AbstractContainerScreen<?> screen, PoseStack poseStack, int mouseX, int mouseY) {
        // use this event since it runs before dragged item stack is rendered, so we can render behind
        if (screen instanceof BagItemScreen bagItemScreen) {
            for (int i = 0; i < bagItemScreen.getMenu().slots.size(); ++i) {
                Slot slot = bagItemScreen.getMenu().slots.get(i);
                if (!bagItemScreen.isHoveredSlot(slot) && slot instanceof LockableInventorySlot inventorySlot && inventorySlot.locked()) {
                    AbstractContainerScreen.renderSlotHighlight(poseStack, slot.x, slot.y, bagItemScreen.getBlitOffset());
                }
            }
        }
    }
}
