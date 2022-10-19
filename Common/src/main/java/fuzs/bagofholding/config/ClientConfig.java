package fuzs.bagofholding.config;

import fuzs.bagofholding.api.config.ContainerItemTooltipConfig;
import fuzs.puzzleslib.config.ConfigCore;
import fuzs.puzzleslib.config.annotation.Config;

public class ClientConfig implements ConfigCore, ContainerItemTooltipConfig {
    @Config(description = "Color bag inventories on tooltips according to the bag's color.")
    public boolean colorfulTooltips = true;
    @Config(description = "Seeing bag inventory contents requires shift to be held.")
    public boolean contentsRequireShift = true;
    @Config(name = "render_slot_overlay", description = "Render a white overlay over the slot the next item will be taken out when right-clicking the shulker box item.")
    public boolean slotOverlay = true;
    @Config(description = "Show an indicator on bags when the stack carried by the cursor can be added to them in your inventory.")
    public boolean containerItemIndicator = true;

    @Override
    public boolean colorfulTooltips() {
        return this.colorfulTooltips;
    }

    @Override
    public boolean contentsRequireShift() {
        return this.contentsRequireShift;
    }

    @Override
    public boolean slotOverlay() {
        return this.slotOverlay;
    }
}
