package fuzs.bagofholding.data;

import com.google.gson.JsonObject;
import fuzs.bagofholding.init.ModRegistry;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.Nullable;

import java.util.function.Consumer;

public class BagUpgradeRecipeBuilder extends ShapedRecipeBuilder {

    public BagUpgradeRecipeBuilder(RecipeCategory recipeCategory, ItemLike itemLike, int count) {
        super(recipeCategory, itemLike, count);
    }

    @Override
    public void save(Consumer<FinishedRecipe> finishedRecipeConsumer, ResourceLocation recipeId) {
        super.save(finishedRecipe -> finishedRecipeConsumer.accept(new Result(finishedRecipe)), recipeId);
    }

    private record Result(FinishedRecipe finishedRecipe) implements FinishedRecipe {

        @Override
        public void serializeRecipeData(JsonObject pJson) {
            this.finishedRecipe.serializeRecipeData(pJson);
        }

        @Override
        public ResourceLocation getId() {
            return this.finishedRecipe.getId();
        }

        @Override
        public RecipeSerializer<?> getType() {
            return ModRegistry.BAG_UPGRADE_RECIPE_SERIALIZER.get();
        }

        @Nullable
        @Override
        public JsonObject serializeAdvancement() {
            return this.finishedRecipe.serializeAdvancement();
        }

        @Nullable
        @Override
        public ResourceLocation getAdvancementId() {
            return this.finishedRecipe.getAdvancementId();
        }
    }
}
