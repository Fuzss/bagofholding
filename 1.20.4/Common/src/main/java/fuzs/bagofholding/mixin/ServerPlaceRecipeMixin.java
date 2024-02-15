package fuzs.bagofholding.mixin;

import fuzs.bagofholding.world.item.RecipesIgnoreTag;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlaceRecipe.class)
abstract class ServerPlaceRecipeMixin {
    @Shadow
    protected Inventory inventory;

    @ModifyVariable(method = "moveItemToGrid", at = @At("STORE"), ordinal = 0)
    protected int moveItemToGrid(int index, Slot slot, ItemStack stack) {
        if (stack.getItem() instanceof RecipesIgnoreTag) {
            return bagofholding$findSlotMatchingItem(this.inventory, stack);
        } else {
            return index;
        }
    }

    @Unique
    private static int bagofholding$findSlotMatchingItem(Inventory inventory, ItemStack stack) {
        for(int i = 0; i < inventory.items.size(); ++i) {
            // vanilla uses many more checks here for tag/damage/enchantment/name
            if (!inventory.items.get(i).isEmpty() && stack.is(inventory.items.get(i).getItem())) {
                return i;
            }
        }
        return -1;
    }
}
