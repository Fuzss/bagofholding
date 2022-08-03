package fuzs.bagofholding.core;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Predicate;

public class ForgeAbstractions implements CommonAbstractions {

    @Override
    public EnchantmentCategory createEnchantmentCategory(String enumConstantName, Predicate<Item> predicate) {
        return EnchantmentCategory.create(enumConstantName, predicate);
    }
}
