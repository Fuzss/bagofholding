package fuzs.bagofholding.client.gui.screens.inventory;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import fuzs.bagofholding.client.core.ModClientServices;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.inventory.LockableInventorySlot;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;

public class BagItemScreen extends AbstractContainerScreen<BagItemMenu> {
   private static final ResourceLocation CONTAINER_BACKGROUND = new ResourceLocation("textures/gui/container/generic_54.png");
   private final int containerRows;

   public BagItemScreen(BagItemMenu p_98409_, Inventory p_98410_, Component p_98411_) {
      super(p_98409_, p_98410_, p_98411_);
      this.passEvents = false;
      this.containerRows = p_98409_.getRowCount();
      this.imageHeight = 114 + this.containerRows * 18;
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
      RenderSystem.setShader(GameRenderer::getPositionTexShader);
      RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
      RenderSystem.setShaderTexture(0, CONTAINER_BACKGROUND);
      int i = (this.width - this.imageWidth) / 2;
      int j = (this.height - this.imageHeight) / 2;
      this.blit(p_98413_, i, j, 0, 0, this.imageWidth, 17);
      for (int k = 0; k < (int) Math.ceil(this.containerRows / 6.0); k++) {
         this.blit(p_98413_, i, j + 17 + 18 * 6 * k, 0, 17, this.imageWidth, Math.min(this.containerRows - 6 * k, 6) * 18);
      }
      this.blit(p_98413_, i, j + this.containerRows * 18 + 17, 0, 126, this.imageWidth, 96);
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