package fuzs.bagofholding.neoforge.world.item;

import fuzs.bagofholding.world.inventory.BagItemMenu;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.core.Holder;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.ItemStack;

public class NeoForgeBagOfHoldingItem extends BagOfHoldingItem {

    public NeoForgeBagOfHoldingItem(Properties properties, Holder<MenuType<BagItemMenu>> menuType) {
        super(properties, menuType);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        // changes to the tag otherwise trigger the re-equip animation
        return slotChanged;
    }
}
