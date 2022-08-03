package fuzs.bagofholding.core;

import fuzs.bagofholding.world.item.enchantment.PreservationEnchantment;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.EnchantmentCategory;

import java.util.function.Predicate;

public interface CommonAbstractions {

    EnchantmentCategory createEnchantmentCategory(String enumConstantName, Predicate<Item> predicate);
}
