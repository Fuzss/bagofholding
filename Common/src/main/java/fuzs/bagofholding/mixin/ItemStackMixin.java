package fuzs.bagofholding.mixin;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
abstract class ItemStackMixin {
    @Shadow
    private int count;

    @Inject(method = "<init>(Lnet/minecraft/nbt/CompoundTag;)V", at = @At("TAIL"))
    private void init(CompoundTag compoundTag, CallbackInfo callback) {
        this.count = compoundTag.getInt("Count");
    }

    @Inject(method = "save", at = @At("TAIL"))
    public void save(CompoundTag compoundTag, CallbackInfoReturnable<CompoundTag> callback) {
        compoundTag.putInt("Count", this.count);
    }
}
