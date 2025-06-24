package fuzs.bagofholding.client.gui.screens.inventory;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.puzzleslib.api.client.key.v1.KeyMappingHelper;
import fuzs.puzzleslib.api.core.v1.utility.ResourceLocationHelper;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class BagItemScreen extends AbstractContainerScreen<BagItemMenu> {
    private static final ResourceLocation CONTAINER_BACKGROUND = ResourceLocationHelper.withDefaultNamespace(
            "textures/gui/container/generic_54.png");

    public BagItemScreen(BagItemMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageHeight = 114 + menu.getInventoryHeight() * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderSlotHighlightBack(GuiGraphics guiGraphics) {
        super.renderSlotHighlightBack(guiGraphics);
        this.renderLockableSlotHighlight(guiGraphics, SLOT_HIGHLIGHT_BACK_SPRITE);
    }

    @Override
    protected void renderSlotHighlightFront(GuiGraphics guiGraphics) {
        super.renderSlotHighlightFront(guiGraphics);
        this.renderLockableSlotHighlight(guiGraphics, SLOT_HIGHLIGHT_FRONT_SPRITE);
    }

    private void renderLockableSlotHighlight(GuiGraphics guiGraphics, ResourceLocation resourceLocation) {
        for (Slot slot : this.menu.slots) {
            if (slot != this.hoveredSlot && slot.isHighlightable()) {
                if (slot instanceof LockableInventorySlot lockableSlot && lockableSlot.locked()) {
                    guiGraphics.blitSprite(RenderPipelines.GUI_TEXTURED,
                            resourceLocation,
                            slot.x - 4,
                            slot.y - 4,
                            24,
                            24);
                }
            }
        }
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int backgroundColor = this.getBackgroundColor();
        int leftPos = (this.width - this.imageWidth) / 2;
        int topPos = (this.height - this.imageHeight) / 2;
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                CONTAINER_BACKGROUND,
                leftPos,
                topPos,
                0,
                0,
                this.imageWidth,
                17,
                256,
                256,
                backgroundColor);
        int inventoryHeight = this.menu.getInventoryHeight();
        for (int k = 0; k < (int) Math.ceil(inventoryHeight / 6.0); k++) {
            guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                    CONTAINER_BACKGROUND,
                    leftPos,
                    topPos + 17 + 18 * 6 * k,
                    0,
                    17,
                    this.imageWidth,
                    Math.min(inventoryHeight - 6 * k, 6) * 18,
                    256,
                    256,
                    backgroundColor);
        }
        guiGraphics.blit(RenderPipelines.GUI_TEXTURED,
                CONTAINER_BACKGROUND,
                leftPos,
                topPos + inventoryHeight * 18 + 17,
                0,
                126,
                this.imageWidth,
                96,
                256,
                256,
                backgroundColor);
    }

    private int getBackgroundColor() {
        if (BagOfHolding.CONFIG.get(ClientConfig.class).colorfulMenuBackgrounds) {
            return this.menu.getBackgroundColor();
        } else {
            return -1;
        }
    }

    @Override
    protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
        guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, -1, false);
        guiGraphics.drawString(this.font,
                this.playerInventoryTitle,
                this.inventoryLabelX,
                this.inventoryLabelY,
                -1,
                false);
    }

    @Override
    protected boolean checkHotbarKeyPressed(int keyCode, int scanCode) {
        // prevent number keys from extracting items from a locked slot
        // vanilla only checks the hovered slot for being accessible, but the hotbar item is directly taken from the inventory, not from a slot,
        // therefore ignoring all restrictions put on the corresponding slot in the menu
        // also the hotbar slot has a varying index as the player inventory is always added last, so we store the first hotbar slot during menu construction
        if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null) {
            for (int i = 0; i < 9; ++i) {
                if (KeyMappingHelper.isKeyActiveAndMatches(this.minecraft.options.keyHotbarSlots[i],
                        keyCode,
                        scanCode)) {
                    if (this.menu.getSlot(this.menu.getHotbarStartIndex() + i) instanceof LockableInventorySlot slot
                            && slot.locked()) {
                        return true;
                    }
                }
            }
        }

        return super.checkHotbarKeyPressed(keyCode, scanCode);
    }

    public boolean isHoveredSlot(Slot slot) {
        return this.hoveredSlot == slot;
    }
}