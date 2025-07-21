package fuzs.bagofholding.world.item.container;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.item.BagType;
import fuzs.iteminteractions.api.v1.DyeBackedColor;
import fuzs.iteminteractions.api.v1.provider.impl.ContainerProvider;
import net.minecraft.world.entity.EquipmentSlotGroup;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public class BagProvider extends ContainerProvider {
    public static final MapCodec<BagProvider> CODEC = RecordCodecBuilder.mapCodec(instance -> {
        return instance.group(BagType.CODEC.fieldOf("bag_type").forGetter(provider -> provider.bagType),
                        backgroundColorCodec(),
                        itemContentsCodec(),
                        interactionPermissionsCodec(),
                        equipmentSlotsCodec())
                .apply(instance,
                        (BagType bagType, Optional<DyeBackedColor> dyeColor, ItemContents itemContents, InteractionPermissions interactionPermissions, EquipmentSlotGroup equipmentSlots) -> {
                            return new BagProvider(bagType, dyeColor.orElse(null)).itemContents(itemContents)
                                    .interactionPermissions(interactionPermissions)
                                    .equipmentSlots(equipmentSlots);
                        });
    });

    private final BagType bagType;

    public BagProvider(BagType bagType, @Nullable DyeBackedColor dyeColor) {
        super(-1, -1, dyeColor);
        this.bagType = bagType;
    }

    @Override
    protected BagProvider itemContents(ItemContents itemContents) {
        return (BagProvider) super.itemContents(itemContents);
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

    public @Nullable DyeBackedColor getBackgroundColor() {
        return this.dyeColor;
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
