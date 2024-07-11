package fuzs.bagofholding.world.item.container;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagType;
import fuzs.iteminteractions.api.v1.DyeBackedColor;
import fuzs.iteminteractions.api.v1.provider.impl.ContainerProvider;
import net.minecraft.core.HolderSet;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BagProvider extends ContainerProvider {
    public static final MapCodec<BagProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(BagType.CODEC.fieldOf("bag_type").forGetter(provider -> provider.bagType),
                        backgroundColorCodec(),
                        disallowedItemsCodec(),
                        filterContainerItemsCodec(),
                        interactionPermissionsCodec(),
                        equipmentSlotsCodec()
                )
                .apply(instance,
                        (BagType bagType, Optional<DyeBackedColor> dyeColor, HolderSet<Item> disallowedItems, Boolean filterContainerItems, InteractionPermissions interactionPermissions, EquipmentSlotGroup equipmentSlots) -> {
                            return new BagProvider(bagType, dyeColor.orElse(null)).disallowedItems(disallowedItems)
                                    .filterContainerItems(filterContainerItems)
                                    .interactionPermissions(interactionPermissions)
                                    .equipmentSlots(equipmentSlots);
                        }
                );
    });

    private final BagType bagType;

    public BagProvider(BagType bagType, @Nullable DyeBackedColor dyeColor) {
        super(-1, -1, dyeColor);
        this.bagType = bagType;
    }

    @Override
    public BagProvider disallowedItems(HolderSet<Item> disallowedItems) {
        return (BagProvider) super.disallowedItems(disallowedItems);
    }

    @Override
    public BagProvider filterContainerItems(boolean filterContainerItems) {
        return (BagProvider) super.filterContainerItems(filterContainerItems);
    }

    @Override
    public BagProvider interactionPermissions(InteractionPermissions interactionPermissions) {
        return (BagProvider) super.interactionPermissions(interactionPermissions);
    }

    @Override
    public BagProvider equipmentSlots(EquipmentSlotGroup equipmentSlots) {
        return (BagProvider) super.equipmentSlots(equipmentSlots);
    }

    @Override
    public float[] getBackgroundColor() {
        return super.getBackgroundColor();
    }

    @Override
    public int getInventoryWidth() {
        return 9;
    }

    @Override
    public int getInventoryHeight() {
        return switch (this.bagType) {
            case LEATHER -> BagOfHolding.CONFIG.get(ServerConfig.class).leatherBagRows;
            case IRON -> BagOfHolding.CONFIG.get(ServerConfig.class).ironBagRows;
            case GOLDEN -> BagOfHolding.CONFIG.get(ServerConfig.class).goldenBagRows;
        };
    }

    @Override
    public Type getType() {
        return ModRegistry.BAG_ITEM_CONTENTS_PROVIDER_TYPE.value();
    }
}
