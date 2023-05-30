package fuzs.bagofholding.world.item.crafting;

import com.google.gson.JsonObject;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

/**
 * do not override {@link net.minecraft.world.item.crafting.Recipe#isSpecial} setting it to true, otherwise we don't show up in the recipe book
 */
public class BagUpgradeRecipe extends ShapedRecipe {

    public BagUpgradeRecipe(ResourceLocation resourceLocation, String string, CraftingBookCategory craftingBookCategory, int width, int height, NonNullList<Ingredient> nonNullList, ItemStack itemStack) {
        super(resourceLocation, string, craftingBookCategory, width, height, nonNullList, itemStack);
    }

    @Override
    public ItemStack assemble(CraftingContainer container) {
        ItemStack itemstack = ItemStack.EMPTY;
        for (int i = 0; i < container.getContainerSize(); ++i) {
            ItemStack itemstack1 = container.getItem(i);
            if (itemstack1.getItem() instanceof BagOfHoldingItem) {
                itemstack = itemstack1;
            }
        }
        ItemStack resultItem = super.assemble(container);
        if (!itemstack.isEmpty() && itemstack.hasTag() && resultItem.getItem() instanceof BagOfHoldingItem) {
            resultItem.setTag(itemstack.getTag().copy());
        }
        return resultItem;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRegistry.BAG_UPGRADE_RECIPE_SERIALIZER.get();
    }

    public static class Serializer extends ShapedRecipe.Serializer {
        @Override
        public ShapedRecipe fromJson(ResourceLocation recipeId, JsonObject jsonObject) {
            ShapedRecipe recipe = super.fromJson(recipeId, jsonObject);
            return new BagUpgradeRecipe(recipe.getId(), recipe.getGroup(), recipe.category(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
        }

        @Override
        public ShapedRecipe fromNetwork(ResourceLocation recipeId, FriendlyByteBuf friendlyByteBuf) {
            ShapedRecipe recipe = super.fromNetwork(recipeId, friendlyByteBuf);
            return new BagUpgradeRecipe(recipe.getId(), recipe.getGroup(), recipe.category(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
        }
    }
}
