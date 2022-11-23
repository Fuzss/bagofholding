package fuzs.bagofholding.client.gui.screens.inventory;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.api.world.item.container.ContainerItemHelper;
import fuzs.bagofholding.client.core.ModClientServices;
import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
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
   public void render(PoseStack p_98418_, int p_98419_, int p_98420_, float p_98421_) {
      this.renderBackground(p_98418_);
      super.render(p_98418_, p_98419_, p_98420_, p_98421_);
      this.renderTooltip(p_98418_, p_98419_, p_98420_);
   }

   @Override
   protected void renderBg(PoseStack p_98413_, float p_98414_, int p_98415_, int p_98416_) {
      if (BagOfHolding.CONFIG.get(ClientConfig.class).colorfulTooltips) {
         float[] color = ContainerItemHelper.getBackgroundColor(this.menu.getBackgroundColor());
         RenderSystem.setShaderColor(color[0], color[1], color[2], 1.0F);
      } else {
         RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      }
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      this.blit(p_98413_, i, j, 0, 0, this.imageWidth, 17);
      int containerRows = this.menu.getRowCount();
      for (int k = 0; k < (int) Math.ceil(containerRows / 6.0); k++) {
         this.blit(p_98413_, i, j + 17 + 18 * 6 * k, 0, 17, this.imageWidth, Math.min(containerRows - 6 * k, 6) * 18);
      }
      this.blit(p_98413_, i, j + containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
   }

   @Override
   protected void renderLabels(PoseStack poseStack, int mouseX, int mouseY) {
      if (BagOfHolding.CONFIG.get(ClientConfig.class).colorfulTooltips) {
         int textColor = this.menu.getTextColor() == null ? 4210752 : this.menu.getTextColor().getTextColor();
         this.font.draw(poseStack, this.title, (float)this.titleLabelX, (float)this.titleLabelY, textColor);
         this.font.draw(poseStack, this.playerInventoryTitle, (float)this.inventoryLabelX, (float)this.inventoryLabelY, textColor);
      } else {
         super.renderLabels(poseStack, mouseX, mouseY);
      }
   }

   @Override
   protected boolean checkHotbarKeyPressed(int p_97806_, int p_97807_) {
      // prevent number keys from extracting items from a locked slot
      // vanilla only checks the hovered slot for being accessible, but the hotbar item is directly taken from the inventory, not from a slot,
      // therefore ignoring all restrictions put on the corresponding slot in the menu
      // also the hotbar slot has a varying index as the player inventory is always added last, so we store the first hotbar slot during menu construction
      if (this.menu.getCarried().isEmpty() && this.hoveredSlot != null) {
         for (int i = 0; i < 9; ++i) {
            if (ModClientServices.CLIENT_ABSTRACTIONS.isKeyActiveAndMatches(this.minecraft.options.keyHotbarSlots[i], p_97806_, p_97807_)) {
               if (this.menu.getSlot(this.menu.getHotbarStartIndex() + i) instanceof LockableInventorySlot slot && slot.locked()) {
                  return true;
               }
            }
         }
      }

      return super.checkHotbarKeyPressed(p_97806_, p_97807_);
   }

   public boolean isHoveredSlot(Slot slot) {
      return this.hoveredSlot == slot;
   }
}