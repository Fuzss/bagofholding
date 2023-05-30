package fuzs.bagofholding;

import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.data.ModItemContainerProvider;
import fuzs.bagofholding.data.ModLanguageProvider;
import fuzs.bagofholding.data.ModModelProvider;
import fuzs.bagofholding.data.ModRecipeProvider;
import fuzs.bagofholding.init.ForgeModRegistry;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.capability.v2.ForgeCapabilityHelper;
import fuzs.puzzleslib.api.core.v1.ModConstructor;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

import java.util.concurrent.CompletableFuture;

@Mod(BagOfHolding.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BagOfHoldingForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ForgeModRegistry.touch();
        ModConstructor.construct(BagOfHolding.MOD_ID, BagOfHolding::new);
        registerCapabilities();
        registerHandlers();
    }

    private static void registerCapabilities() {
        ForgeCapabilityHelper.setCapabilityToken(ModRegistry.BAG_PERSEVERANCE_CAPABILITY, new CapabilityToken<BagPerseveranceCapability>() {});
    }

    private static void registerHandlers() {
        // TODO move to common puzzles implementation in 1.19.4
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
        final DataGenerator dataGenerator = evt.getGenerator();
        final PackOutput packOutput = dataGenerator.getPackOutput();
        final CompletableFuture<HolderLookup.Provider> lookupProvider = evt.getLookupProvider();
        final ExistingFileHelper fileHelper = evt.getExistingFileHelper();
        dataGenerator.addProvider(true, new ModItemContainerProvider(packOutput));
        dataGenerator.addProvider(true, new ModLanguageProvider(packOutput, BagOfHolding.MOD_ID));
        dataGenerator.addProvider(true, new ModModelProvider(packOutput, BagOfHolding.MOD_ID, fileHelper));
        dataGenerator.addProvider(true, new ModRecipeProvider(packOutput));
    }
}
