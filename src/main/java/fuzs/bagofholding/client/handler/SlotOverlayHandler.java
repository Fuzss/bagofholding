package fuzs.bagofholding.client.handler;

import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class SlotOverlayHandler {
    @SubscribeEvent
    public void onDrawForeground(final ContainerScreenEvent.DrawForeground evt) {
        // use this event since it runs before dragged item stack is rendered, so we can render behind
        if (evt.getContainerScreen() instanceof BagItemScreen screen) {
            for (int i = 0; i < screen.getMenu().slots.size(); ++i) {
                Slot slot = screen.getMenu().slots.get(i);
                if (!screen.isHoveredSlot(slot) && slot instanceof LockableInventorySlot inventorySlot && inventorySlot.locked()) {
                    AbstractContainerScreen.renderSlotHighlight(evt.getPoseStack(), slot.x, slot.y, screen.getBlitOffset(), screen.getSlotColor(i));
                }
            }
        }
    }
}
