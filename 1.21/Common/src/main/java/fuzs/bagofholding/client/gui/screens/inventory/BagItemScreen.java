package fuzs.bagofholding.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.iteminteractions.api.v1.ContainerItemHelper;
import fuzs.puzzleslib.api.client.core.v1.ClientAbstractions;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;

public class BagItemScreen extends AbstractContainerScreen<BagItemMenu> {
   private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");

   public BagItemScreen(BagItemMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.imageHeight = 114 + menu.getInventoryRows() * 18;
      this.inventoryLabelY = this.imageHeight - 94;
   }

   @Override
   public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
      super.render(guiGraphics, mouseX, mouseY, partialTick);
      this.renderTooltip(guiGraphics, mouseX, mouseY);
   }

   @Override
   protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
      DyeColor dyeColor = BagOfHolding.CONFIG.get(ClientConfig.class).colorfulMenuBackgrounds ? this.menu.getBackgroundColor() : null;
      float[] backgroundColor = ContainerItemHelper.INSTANCE.getBackgroundColor(dyeColor);
      RenderSystem.setShaderColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0F);
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      guiGraphics.blit(CONTAINER_BACKGROUND, i, j, 0, 0, this.imageWidth, 17);
      int containerRows = this.menu.getInventoryRows();
      for (int k = 0; k < (int) Math.ceil(containerRows / 6.0); k++) {
         guiGraphics.blit(CONTAINER_BACKGROUND, i, j + 17 + 18 * 6 * k, 0, 17, this.imageWidth, Math.min(containerRows - 6 * k, 6) * 18);
      }
      guiGraphics.blit(CONTAINER_BACKGROUND, i, j + containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   protected void renderLabels(GuiGraphics guiGraphics, int mouseX, int mouseY) {
      int textColor = this.menu.getTextColor() == null ? 0x404040 : this.menu.getTextColor().getTextColor();
      guiGraphics.drawString(this.font, this.title, this.titleLabelX, this.titleLabelY, textColor, false);
      guiGraphics.drawString(this.font, this.playerInventoryTitle, this.inventoryLabelX, this.inventoryLabelY, textColor, false);
   }

   @Override
   protected boolean checkHotbarKeyPressed(int keyCode, int scanCode) {
      // prevent number keys from extracting items from a locked slot
      // vanilla only checks the hovered slot for being accessible, but the hotbar item is directly taken from the inventory, not from a slot,
      // therefore ignoring all restrictions put on the corresponding slot in the menu
      // also the hotbar slot has a varying index as the player inventory is always added last, so we store the first hotbar slot during menu construction
      if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null) {
         for (int i = 0; i < 9; ++i) {
            if (ClientAbstractions.INSTANCE.isKeyActiveAndMatches(this.minecraft.options.keyHotbarSlots[i], keyCode, scanCode)) {
               if (this.menu.getSlot(this.menu.getHotbarStartIndex() + i) instanceof LockableInventorySlot slot && slot.locked()) {
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