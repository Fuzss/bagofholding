package fuzs.bagofholding.neoforge.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.neoforge.api.data.v2.AbstractBuiltInDataProvider;
import fuzs.puzzleslib.neoforge.api.data.v2.core.NeoForgeDataProviderContext;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantmentProvider extends AbstractBuiltInDataProvider.Enchantments {

    public ModEnchantmentProvider(NeoForgeDataProviderContext context) {
        super(context);
    }

    @Override
    protected void addBootstrap(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> items = context.lookup(Registries.ITEM);
        register(context,
                ModRegistry.PRESERVATION_ENCHANTMENT,
                Enchantment.enchantment(Enchantment.definition(items.getOrThrow(ModRegistry.BAGS_ITEM_TAG),
                        5,
                        3,
                        Enchantment.dynamicCost(5, 8),
                        Enchantment.dynamicCost(55, 8),
                        2,
                        EquipmentSlotGroup.ANY
                ))
        );
    }
}
