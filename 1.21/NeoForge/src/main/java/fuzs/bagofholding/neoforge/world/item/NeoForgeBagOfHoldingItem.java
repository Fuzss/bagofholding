package fuzs.bagofholding.neoforge.world.item;

import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.BagType;
import net.minecraft.world.item.ItemStack;

public class NeoForgeBagOfHoldingItem extends BagOfHoldingItem {

    public NeoForgeBagOfHoldingItem(Properties properties, BagType type) {
        super(properties, type);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        // changes to the tag otherwise trigger the re-equip animation
        return slotChanged;
    }
}
