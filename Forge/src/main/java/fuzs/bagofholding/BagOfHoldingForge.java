package fuzs.bagofholding;

import fuzs.bagofholding.data.ModLanguageProvider;
import fuzs.bagofholding.data.ModRecipeProvider;
import fuzs.bagofholding.init.ForgeModRegistry;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.core.CoreServices;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod(BagOfHolding.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BagOfHoldingForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        CoreServices.FACTORIES.modConstructor(BagOfHolding.MOD_ID).accept(new BagOfHolding());
        ForgeModRegistry.touch();
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final PlayerEvent.Clone evt) -> {
            if (!evt.isWasDeath()) return;
            if (evt.getOriginal() instanceof ServerPlayer player) {
                player.reviveCaps();
                ModRegistry.BAG_PERSEVERANCE_CAPABILITY.maybeGet(player).ifPresent(capability -> {
                    capability.restoreAfterRespawn(evt.getEntity());
                });
                player.invalidateCaps();
            }
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, new ModLanguageProvider(generator, BagOfHolding.MOD_ID));
    }
}
