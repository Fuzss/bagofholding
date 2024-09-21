package fuzs.bagofholding.mixin;

import fuzs.bagofholding.init.ModRegistry;
import net.minecraft.recipebook.ServerPlaceRecipe;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ServerPlaceRecipe.class)
abstract class ServerPlaceRecipeMixin<I extends RecipeInput, R extends Recipe<I>> {
    @Shadow
    protected Inventory inventory;

    @ModifyVariable(method = "moveItemToGrid", at = @At("STORE"), ordinal = 1)
    public int moveItemToGrid(int index, Slot slot, ItemStack itemStack, int maxAmount) {
        // handle items that should be moved by the recipe book autofill feature even when it has any components data,
        // as vanilla deliberately ignores those
        if (itemStack.is(ModRegistry.RECIPES_IGNORE_COMPONENTS_ITEM_TAG)) {
            return this.bagofholding$findSlotMatchingUnusedItem(this.inventory, itemStack);
        } else {
            return index;
        }
    }

    @Unique
    private int bagofholding$findSlotMatchingUnusedItem(Inventory inventory, ItemStack itemStack) {
        for (int i = 0; i < inventory.items.size(); ++i) {
            if (!inventory.items.get(i).isEmpty() && itemStack.is(inventory.items.get(i).getItem())) {
                return i;
            }
        }

        return -1;
    }
}
