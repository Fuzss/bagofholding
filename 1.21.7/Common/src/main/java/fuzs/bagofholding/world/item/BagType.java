package fuzs.bagofholding.world.item;

import com.mojang.serialization.Codec;
import fuzs.iteminteractions.api.v1.DyeBackedColor;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.DyeColor;

import java.util.Locale;

public enum BagType implements StringRepresentable {
    LEATHER(DyeColor.BROWN),
    IRON(DyeColor.LIGHT_GRAY),
    GOLDEN(DyeColor.YELLOW);

    public static final Codec<BagType> CODEC = StringRepresentable.fromEnum(BagType::values);

    public final DyeBackedColor fallbackColor;

    BagType(DyeColor dyeColor) {
        this.fallbackColor = DyeBackedColor.fromDyeColor(dyeColor);
    }

    @Override
    public String getSerializedName() {
        return this.name().toLowerCase(Locale.ROOT);
    }
}
