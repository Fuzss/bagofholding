package fuzs.bagofholding.world.item.crafting;

import com.google.gson.JsonObject;
import fuzs.bagofholding.registry.ModRegistry;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;

/**
 * do not override {@link net.minecraft.world.item.crafting.Recipe#isSpecial} setting it to true, otherwise we don't show up in the recipe book
 */
public class BagUpgradeRecipe extends ShapedRecipe {
    public BagUpgradeRecipe(ResourceLocation p_44153_, String p_44154_, int p_44155_, int p_44156_, NonNullList<Ingredient> p_44157_, ItemStack p_44158_) {
        super(p_44153_, p_44154_, p_44155_, p_44156_, p_44157_, p_44158_);
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
        public ShapedRecipe fromJson(ResourceLocation p_44236_, JsonObject p_44237_) {
            ShapedRecipe recipe = super.fromJson(p_44236_, p_44237_);
            if (recipe == null) return null;
            return new BagUpgradeRecipe(recipe.getId(), recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
        }

        @Override
        public ShapedRecipe fromNetwork(ResourceLocation p_44239_, FriendlyByteBuf p_44240_) {
            ShapedRecipe recipe = super.fromNetwork(p_44239_, p_44240_);
            if (recipe == null) return null;
            return new BagUpgradeRecipe(recipe.getId(), recipe.getGroup(), recipe.getWidth(), recipe.getHeight(), recipe.getIngredients(), recipe.getResultItem());
        }
    }
}
