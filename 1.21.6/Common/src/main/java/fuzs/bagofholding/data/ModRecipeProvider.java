package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractRecipeProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import fuzs.puzzleslib.api.data.v2.recipes.TransmuteShapedRecipeBuilder;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.block.Blocks;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(this.items(), RecipeCategory.TOOLS, ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value())
                .define('C', Blocks.CHEST)
                .define('S', Items.STRING)
                .define('I', Items.LEATHER)
                .define('W', ItemTags.WOOL)
                .pattern("SIS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy(getHasName(Blocks.CHEST), this.has(Blocks.CHEST))
                .save(recipeOutput);
        RecipeSerializer<?> recipeSerializer = TransmuteShapedRecipeBuilder.getRecipeSerializer(this.modId);
        TransmuteShapedRecipeBuilder.shaped(recipeSerializer,
                        this.items(),
                        RecipeCategory.TOOLS,
                        ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value())
                .define('C', ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value())
                .define('S', Items.STRING)
                .define('I', Items.IRON_INGOT)
                .define('G', Items.DIAMOND)
                .define('W', ItemTags.WOOL)
                .input(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value())
                .pattern("SGS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy(getHasName(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value()),
                        this.has(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.value()))
                .save(recipeOutput);
        TransmuteShapedRecipeBuilder.shaped(recipeSerializer,
                        this.items(),
                        RecipeCategory.TOOLS,
                        ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.value())
                .define('C', ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value())
                .define('S', Items.STRING)
                .define('I', Items.GOLD_INGOT)
                .define('G', Items.AMETHYST_SHARD)
                .define('W', ItemTags.WOOL)
                .input(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value())
                .pattern("SGS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy(getHasName(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value()),
                        this.has(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.value()))
                .save(recipeOutput);
    }
}
