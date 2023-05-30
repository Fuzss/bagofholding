package fuzs.bagofholding.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import fuzs.iteminteractionscore.api.container.v1.ContainerItemHelper;
import fuzs.puzzleslib.api.client.core.v1.ClientAbstractions;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class BagItemScreen extends AbstractContainerScreen<BagItemMenu> {
   private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");

   public BagItemScreen(BagItemMenu menu, Inventory inventory, Component title) {
      super(menu, inventory, title);
      this.passEvents = false;
      this.imageHeight = 114 + menu.getRowCount() * 18;
      this.inventoryLabelY = this.imageHeight - 94;
   }

   @Override
   public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
      this.renderBackground(poseStack);
      super.render(poseStack, mouseX, mouseY, partialTick);
      this.renderTooltip(poseStack, mouseX, mouseY);
   }

   @Override
   protected void renderBg(PoseStack poseStack, float partialTick, int mouseX, int mouseY) {
      float[] backgroundColor = ContainerItemHelper.INSTANCE.getBackgroundColor(this.menu.getBackgroundColor());
      RenderSystem.setShaderColor(backgroundColor[0], backgroundColor[1], backgroundColor[2], 1.0F);
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      this.blit(poseStack, i, j, 0, 0, this.imageWidth, 17);
      int containerRows = this.menu.getRowCount();
      for (int k = 0; k < (int) Math.ceil(containerRows / 6.0); k++) {
         this.blit(poseStack, i, j + 17 + 18 * 6 * k, 0, 17, this.imageWidth, Math.min(containerRows - 6 * k, 6) * 18);
      }
      this.blit(poseStack, i, j + containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
      int textColor = this.menu.getTextColor() == null ? 4210752 : this.menu.getTextColor().getTextColor();
      this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, textColor);
      this.font.draw(poseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, textColor);
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