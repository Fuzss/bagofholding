package fuzs.bagofholding.forge.world.item;

import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.bagofholding.world.item.BagType;
import net.minecraft.world.item.ItemStack;

public class ForgeBagOfHoldingItem extends BagOfHoldingItem {

    public ForgeBagOfHoldingItem(Properties properties, BagType type) {
        super(properties, type);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        // changes to the tag otherwise trigger the re-equip animation
        return slotChanged;
    }
}
