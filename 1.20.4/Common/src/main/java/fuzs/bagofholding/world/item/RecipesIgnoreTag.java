package fuzs.bagofholding.world.item;

/**
 * Add this interface to an item that should be moved by the recipe book autofill feature even when it has tag/damage/enchantment/name data
 * (vanilla requires reference equivalence, otherwise the item is simply not moved when all other ingredients are).
 */
public interface RecipesIgnoreTag {
    // NO-OP
}
