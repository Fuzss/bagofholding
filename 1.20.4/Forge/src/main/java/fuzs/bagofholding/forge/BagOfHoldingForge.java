package fuzs.bagofholding.forge;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.forge.init.ForgeModRegistry;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(BagOfHolding.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BagOfHoldingForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ForgeModRegistry.touch();
        ModConstructor.construct(BagOfHolding.MOD_ID, BagOfHolding::new);
    }
}
