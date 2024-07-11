package fuzs.bagofholding.fabric.world.item;

import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.core.Holder;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class FabricBagOfHoldingItem extends BagOfHoldingItem implements FabricItem {

    public FabricBagOfHoldingItem(Properties properties, Holder<MenuType<BagItemMenu>> menuType) {
        super(properties, menuType);
    }

    @Override
    public boolean allowComponentsUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        // changes to the tag otherwise trigger the re-equip animation
        return !ItemStack.isSameItem(oldStack, newStack);
    }
}
