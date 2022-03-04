package fuzs.bagofholding.data;

import fuzs.bagofholding.registry.ModRegistry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;

import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider {
    public ModRecipeProvider(DataGenerator p_125973_) {
        super(p_125973_);
    }

    @Override
    protected void buildCraftingRecipes(Consumer<FinishedRecipe> p_176532_) {
        // just manually change type to lootpouches:crafting_special_bag_of_holding_upgrade, so we don't have to copy serializer
        ShapedRecipeBuilder.shaped(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get())
                .define('C', Blocks.CHEST)
                .define('S', Items.STRING)
                .define('I', Items.LEATHER)
                .define('W', ItemTags.WOOL)
                .pattern("SIS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy("has_chest", has(Blocks.CHEST))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get())
                .define('C', ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get())
                .define('S', Items.STRING)
                .define('I', Items.IRON_INGOT)
                .define('W', ItemTags.WOOL)
                .pattern("SIS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy("has_leather_bag_of_holding", has(ModRegistry.LEATHER_BAG_OF_HOLDING_ITEM.get()))
                .save(p_176532_);
        ShapedRecipeBuilder.shaped(ModRegistry.GOLDEN_BAG_OF_HOLDING_ITEM.get())
                .define('C', ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get())
                .define('S', Items.STRING)
                .define('I', Items.GOLD_INGOT)
                .define('W', ItemTags.WOOL)
                .pattern("SIS")
                .pattern("ICI")
                .pattern("WIW")
                .unlockedBy("has_iron_bag_of_holding", has(ModRegistry.IRON_BAG_OF_HOLDING_ITEM.get()))
                .save(p_176532_);
    }
}
