package fuzs.bagofholding.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.client.gui.screens.inventory.BagItemScreen;
import fuzs.bagofholding.client.gui.screens.inventory.tooltip.ClientContainerItemTooltip;
import fuzs.bagofholding.client.handler.SlotOverlayHandler;
import fuzs.bagofholding.registry.ModRegistry;
import fuzs.bagofholding.world.inventory.tooltip.ContainerItemTooltip;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = BagOfHolding.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BagOfHoldingClient {
    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        registerHandlers();
    }

    private static void registerHandlers() {
        SlotOverlayHandler slotOverlayHandler = new SlotOverlayHandler();
        MinecraftForge.EVENT_BUS.addListener(slotOverlayHandler::onDrawForeground);
    }

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent evt) {
        MinecraftForgeClient.registerTooltipComponentFactory(ContainerItemTooltip.class, ClientContainerItemTooltip::new);
        MenuScreens.register(ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
        MenuScreens.register(ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
        MenuScreens.register(ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.get(), BagItemScreen::new);
    }
}
