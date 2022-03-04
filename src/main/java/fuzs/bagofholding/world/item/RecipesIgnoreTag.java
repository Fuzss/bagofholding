package fuzs.bagofholding.world.item;

/**
 * add this interface to an item that should be moved by the recipe book auto-fill feature even when it has tag/damage/enchantment/name data
 * (vanilla requires perfect equivalence, otherwise the item is simply not moved when all other ingredients are)
 */
public interface RecipesIgnoreTag {
}
