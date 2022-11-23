package fuzs.bagofholding.world.inventory;

import fuzs.bagofholding.api.world.inventory.ContainerItemProvider;
import fuzs.bagofholding.api.world.item.container.ContainerItemHelper;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.tooltip.TooltipComponent;
import net.minecraft.world.item.ItemStack;

import java.util.Optional;

public class BagOfHoldingProvider implements ContainerItemProvider {
    private final BagOfHoldingItem.Type type;

    public BagOfHoldingProvider(BagOfHoldingItem.Type type) {
        this.type = type;
    }

    @Override
    public SimpleContainer getItemContainer(Player player, ItemStack stack, boolean allowSaving) {
        return ContainerItemHelper.loadItemContainer(stack, null, this.type.config().rows, allowSaving);
    }

    @Override
    public boolean canAcceptItem(ItemStack stack) {
        return BagOfHoldingItem.mayPlaceInBag(this.type, stack);
    }

    @Override
    public boolean isAllowed() {
        return true;
    }

    @Override
    public Optional<TooltipComponent> getTooltipImage(ItemStack stack) {
        return ContainerItemHelper.getTooltipImage(stack, null, this.type.config().rows, this.type.backgroundColor);
    }
}
