package fuzs.bagofholding.world.item;

import fuzs.bagofholding.world.inventory.BagItemMenu;
import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.function.IntSupplier;

public class FabricBagOfHoldingItem extends BagOfHoldingItem implements FabricItem {

    public FabricBagOfHoldingItem(Properties p_41383_, IntSupplier containerRows, DyeColor backgroundColor, BagItemMenu.Factory menuFactory) {
        super(p_41383_, containerRows, backgroundColor, menuFactory);
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        // changes to the tag otherwise trigger the re-equip animation
        return !ItemStack.isSame(oldStack, newStack);
    }
}
