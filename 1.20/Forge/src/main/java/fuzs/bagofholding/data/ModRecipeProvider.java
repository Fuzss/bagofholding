package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.data.v1.AbstractRecipeProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class ModRecipeProvider extends AbstractRecipeProvider {

    public ModRecipeProvider(PackOutput packOutput) {
        super(packOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> exporter) {
        new BagUpgradeRecipeBuilder(RecipeCategory.TOOLS, ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get(), 1)
                .define('C', Blocks.CHEST)
                .define('S', Items.STRING)
                .define('I', Items.LEATHER)
                .define('W', ItemTags.WOOL)
                .pattern("SIS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy("has_chest", has(Blocks.CHEST))
                .save(exporter);
        new BagUpgradeRecipeBuilder(RecipeCategory.TOOLS, ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get(), 1)
                .define('C', ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get())
                .define('S', Items.STRING)
                .define('I', Items.IRON_INGOT)
                .define('G', Items.DIAMOND)
                .define('W', ItemTags.WOOL)
                .pattern("SGS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy("has_leather_bag_of_holding", has(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get()))
                .save(exporter);
        new BagUpgradeRecipeBuilder(RecipeCategory.TOOLS, ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get(), 1)
                .define('C', ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get())
                .define('S', Items.STRING)
                .define('I', Items.GOLD_INGOT)
                .define('G', Items.AMETHYST_SHARD)
                .define('W', ItemTags.WOOL)
                .pattern("SGS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy("has_iron_bag_of_holding", has(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get()))
                .save(exporter);
    }
}
