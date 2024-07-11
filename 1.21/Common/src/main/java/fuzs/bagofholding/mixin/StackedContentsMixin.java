package fuzs.bagofholding.mixin;

import fuzs.bagofholding.init.ModRegistry;
import net.minecraft.world.entity.player.StackedContents;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StackedContents.class)
abstract class StackedContentsMixin {

    @Inject(method = "accountSimpleStack", at = @At("HEAD"), cancellable = true)
    public void accountSimpleStack(ItemStack itemStack, CallbackInfo callback) {
        if (itemStack.is(ModRegistry.RECIPES_IGNORE_COMPONENTS_ITEM_TAG)) {
            this.accountStack(itemStack);
            callback.cancel();
        }
    }

    @Shadow
    public abstract void accountStack(ItemStack itemStack);
}
