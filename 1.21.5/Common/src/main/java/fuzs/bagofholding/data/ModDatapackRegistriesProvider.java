package fuzs.bagofholding.data;

import fuzs.bagofholding.init.ModRegistry;
import fuzs.puzzleslib.api.data.v2.AbstractDatapackRegistriesProvider;
import fuzs.puzzleslib.api.data.v2.core.DataProviderContext;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModDatapackRegistriesProvider extends AbstractDatapackRegistriesProvider {

    public ModDatapackRegistriesProvider(DataProviderContext context) {
        super(context);
    }

    @Override
    public void addBootstrap(RegistryBoostrapConsumer consumer) {
        consumer.add(Registries.ENCHANTMENT, ModDatapackRegistriesProvider::bootstrapEnchantments);
    }

    static void bootstrapEnchantments(BootstrapContext<Enchantment> context) {
        HolderGetter<Item> itemLookup = context.lookup(Registries.ITEM);
        registerEnchantment(context,
                ModRegistry.PRESERVATION_ENCHANTMENT,
                Enchantment.enchantment(Enchantment.definition(itemLookup.getOrThrow(ModRegistry.BAGS_ITEM_TAG),
                        5,
                        3,
                        Enchantment.dynamicCost(5, 8),
                        Enchantment.dynamicCost(55, 8),
                        2,
                        EquipmentSlotGroup.ANY)));
    }
}
