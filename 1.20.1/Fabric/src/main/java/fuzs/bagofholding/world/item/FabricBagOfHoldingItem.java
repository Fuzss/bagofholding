package fuzs.bagofholding.world.item;

import net.fabricmc.fabric.api.item.v1.FabricItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class FabricBagOfHoldingItem extends BagOfHoldingItem implements FabricItem {

    public FabricBagOfHoldingItem(Properties properties, Type type) {
        super(properties, type);
    }

    @Override
    public boolean allowNbtUpdateAnimation(Player player, InteractionHand hand, ItemStack oldStack, ItemStack newStack) {
        // changes to the tag otherwise trigger the re-equip animation
        return !ItemStack.isSameItem(oldStack, newStack);
    }
}
