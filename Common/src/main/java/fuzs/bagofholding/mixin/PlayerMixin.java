package fuzs.bagofholding.mixin;

import fuzs.bagofholding.init.ModRegistry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Player.class)
abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = "dropEquipment", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/LivingEntity;dropEquipment()V", shift = At.Shift.AFTER))
    protected void bagofholding$dropEquipment(CallbackInfo callback) {
        // on Forge using LivingDropsEvent would work, requires a different implementation though
        // just do it like this since the event is hard to replicate on Fabric anyway due to the whole capturing of drops Forge does
        ModRegistry.BAG_PERSEVERANCE_CAPABILITY.maybeGet(this).ifPresent(capability -> capability.saveOnDeath((Player) (Object) this));
    }
}
