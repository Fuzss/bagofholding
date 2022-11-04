package fuzs.bagofholding;

import fuzs.bagofholding.api.SimpleInventoryContainersApi;
import fuzs.bagofholding.api.capability.ContainerSlotCapability;
import fuzs.bagofholding.capability.BagPerseveranceCapability;
import fuzs.bagofholding.data.ModLanguageProvider;
import fuzs.bagofholding.data.ModRecipeProvider;
import fuzs.bagofholding.init.ForgeModRegistry;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.capability.ForgeCapabilityController;
import fuzs.puzzleslib.core.CommonFactories;
import net.minecraft.data.DataGenerator;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod(BagOfHolding.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class BagOfHoldingForge {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ForgeModRegistry.touch();
        CommonFactories.INSTANCE.modConstructor(BagOfHolding.MOD_ID).accept(new SimpleInventoryContainersApi());
        CommonFactories.INSTANCE.modConstructor(BagOfHolding.MOD_ID).accept(new BagOfHolding());
        registerCapabilities();
        registerHandlers();
    }

    private static void registerCapabilities() {
        ForgeCapabilityController.setCapabilityToken(ModRegistry.BAG_PERSEVERANCE_CAPABILITY, new CapabilityToken<BagPerseveranceCapability>() {});
        ForgeCapabilityController.setCapabilityToken(fuzs.bagofholding.api.init.ModRegistry.CONTAINER_SLOT_CAPABILITY, new CapabilityToken<ContainerSlotCapability>() {});
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final PlayerEvent.Clone evt) -> {
            if (!evt.isWasDeath()) return;
            if (evt.getOriginal() instanceof ServerPlayer player) {
                player.reviveCaps();
                ModRegistry.BAG_PERSEVERANCE_CAPABILITY.maybeGet(player).ifPresent(capability -> {
                    capability.restoreAfterRespawn(evt.getPlayer());
                });
                player.invalidateCaps();
            }
        });
    }

    @SubscribeEvent
    public static void onGatherData(final GatherDataEvent evt) {
        DataGenerator generator = evt.getGenerator();
        final ExistingFileHelper existingFileHelper = evt.getExistingFileHelper();
        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModLanguageProvider(generator, BagOfHolding.MOD_ID));
    }
}
