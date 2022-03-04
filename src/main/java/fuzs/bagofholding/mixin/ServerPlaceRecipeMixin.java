package fuzs.bagofholding.mixin;

import fuzs.bagofholding.world.item.RecipesIgnoreTag;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlaceRecipe.class)
public abstract class ServerPlaceRecipeMixin {
    @Shadow
    protected Inventory inventory;

    @ModifyVariable(method = "moveItemToGrid", at = @At("STORE"), ordinal = 0)
    protected int moveItemToGrid$storeIndex(int itemIndex, Slot p_135439_, ItemStack p_135440_) {
        if (p_135440_.getItem() instanceof RecipesIgnoreTag) {
            return this.findSlotMatchingItem(this.inventory, p_135440_);
        }
        return itemIndex;
    }

    private int findSlotMatchingItem(Inventory inventory, ItemStack p_36031_) {
        for(int i = 0; i < inventory.items.size(); ++i) {
            // vanilla uses many more checks here for tag/damage/enchantment/name
            if (!inventory.items.get(i).isEmpty() && p_36031_.is(inventory.items.get(i).getItem())) {
                return i;
            }
        }

        return -1;
    }
}
