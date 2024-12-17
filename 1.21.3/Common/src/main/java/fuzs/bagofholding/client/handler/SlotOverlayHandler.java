package fuzs.bagofholding.client.handler;

import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.Slot;

import java.util.Optional;

public class SlotOverlayHandler {
    private static final ResourceLocation SLOT_HIGHLIGHT_BACK_SPRITE = ResourceLocation.withDefaultNamespace(
            "container/slot_highlight_back");
    private static final ResourceLocation SLOT_HIGHLIGHT_FRONT_SPRITE = ResourceLocation.withDefaultNamespace(
            "container/slot_highlight_front");

    public static void onDrawForeground(AbstractContainerScreen<?> screen, GuiGraphics guiGraphics, int mouseX, int mouseY) {
        // use this event since it runs before dragged item stack is rendered, so we can render behind
        if (screen instanceof BagItemScreen bagItemScreen) {
            findLockedSlot(bagItemScreen).ifPresent((Slot slot) -> {
                guiGraphics.blitSprite(RenderType::guiTextured,
                        SLOT_HIGHLIGHT_BACK_SPRITE,
                        slot.x - 4,
                        slot.y - 4,
                        24,
                        24);
                guiGraphics.blitSprite(RenderType::guiTexturedOverlay,
                        SLOT_HIGHLIGHT_FRONT_SPRITE,
                        slot.x - 4,
                        slot.y - 4,
                        24,
                        24);
            });
        }
    }

    private static Optional<Slot> findLockedSlot(BagItemScreen screen) {
        for (int i = screen.getMenu().slots.size() - 1; i >= 0; i--) {
            Slot slot = screen.getMenu().slots.get(i);
            if (!screen.isHoveredSlot(slot) && slot instanceof LockableInventorySlot lockableSlot &&
                    lockableSlot.locked()) {
                return Optional.of(slot);
            }
        }
        return Optional.empty();
    }
}
