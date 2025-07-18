package fuzs.bagofholding.mixin;

import fuzs.bagofholding.init.ModRegistry;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Inventory.class)
abstract class InventoryMixin {

    @Inject(method = "isUsableForCrafting", at = @At("HEAD"), cancellable = true)
    private static void isUsableForCrafting(ItemStack itemStack, CallbackInfoReturnable<Boolean> callback) {
        if (itemStack.is(ModRegistry.RECIPES_IGNORE_COMPONENTS_ITEM_TAG)) callback.setReturnValue(true);
    }
}
