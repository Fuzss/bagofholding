package fuzs.bagofholding.client;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.api.client.handler.MouseScrollHandler;
import fuzs.bagofholding.client.handler.SlotOverlayHandler;
import fuzs.bagofholding.config.ClientConfig;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.puzzleslib.client.core.ClientFactories;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ContainerScreenEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLConstructModEvent;

@Mod.EventBusSubscriber(modid = BagOfHolding.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BagOfHoldingForgeClient {

    @SubscribeEvent
    public static void onConstructMod(final FMLConstructModEvent evt) {
        ClientFactories.INSTANCE.clientModConstructor(BagOfHolding.MOD_ID).accept(new BagOfHoldingClient());
        registerHandlers();
    }

    private static void registerHandlers() {
        MinecraftForge.EVENT_BUS.addListener((final ContainerScreenEvent.Render.Foreground evt) -> {
            SlotOverlayHandler.onDrawForeground(evt.getContainerScreen(), evt.getPoseStack(), evt.getMouseX(), evt.getMouseY());
        });
        MinecraftForge.EVENT_BUS.addListener((final ScreenEvent.MouseScrolled.Pre evt) -> {
            MouseScrollHandler.onMouseScroll(evt.getScreen(), evt.getMouseX(), evt.getMouseY(), evt.getScrollDelta(), evt.getScrollDelta(), BagOfHolding.CONFIG.get(ClientConfig.class), BagOfHolding.CONFIG.get(ServerConfig.class)).ifPresent(unit -> evt.setCanceled(true));
        });
        MinecraftForge.EVENT_BUS.addListener((final TickEvent.ClientTickEvent evt) -> {
            if (evt.phase == TickEvent.Phase.END) MouseScrollHandler.onClientTick$End(Minecraft.getInstance(), BagOfHolding.CONFIG.get(ClientConfig.class));
        });
    }
}
