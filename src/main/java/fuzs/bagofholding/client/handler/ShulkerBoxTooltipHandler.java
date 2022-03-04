package fuzs.bagofholding.client.handler;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.commons.compress.utils.Lists;

import java.util.List;

public class ShulkerBoxTooltipHandler {
    @SubscribeEvent
    public void onItemTooltip(final ItemTooltipEvent evt) {
        ItemStack stack = evt.getItemStack();
        if (!(Block.byItem(stack.getItem()) instanceof ShulkerBoxBlock)) return;
        List<Component> shulkerBoxLines = Lists.newArrayList();
        stack.getItem().appendHoverText(stack, evt.getPlayer() != null ? evt.getPlayer().level : null, shulkerBoxLines, evt.getFlags());
        if (!shulkerBoxLines.isEmpty() && !shulkerBoxLines.contains(new TextComponent("???????"))) {
            List<Component> tooltip = evt.getToolTip();
            int startIndex = tooltip.indexOf(shulkerBoxLines.get(0));
            int endIndex = tooltip.indexOf(shulkerBoxLines.get(shulkerBoxLines.size() - 1));
            for (int i = 0; i < endIndex - startIndex + 1; i++) {
                tooltip.remove(startIndex);
            }
        }
    }
}
