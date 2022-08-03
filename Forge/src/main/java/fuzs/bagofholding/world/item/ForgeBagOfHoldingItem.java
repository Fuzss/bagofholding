package fuzs.bagofholding.world.item;

import fuzs.bagofholding.world.inventory.BagItemMenu;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.function.IntSupplier;

public class ForgeBagOfHoldingItem extends BagOfHoldingItem {

    public ForgeBagOfHoldingItem(Properties p_41383_, IntSupplier containerRows, DyeColor backgroundColor, BagItemMenu.Factory menuFactory) {
        super(p_41383_, containerRows, backgroundColor, menuFactory);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        // changes to the tag otherwise trigger the re-equip animation
        return slotChanged;
    }
}
