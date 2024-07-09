package fuzs.bagofholding.world.item;

import fuzs.bagofholding.BagOfHolding;
import fuzs.bagofholding.config.ServerConfig;
import fuzs.bagofholding.init.ModRegistry;
import fuzs.bagofholding.world.inventory.BagItemMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public enum BagType implements StringRepresentable {
    LEATHER("leather", DyeColor.BROWN, DyeColor.WHITE),
    IRON("iron", DyeColor.LIGHT_GRAY, DyeColor.WHITE),
    GOLDEN("golden", DyeColor.YELLOW, DyeColor.WHITE);

    public static final StringRepresentable.EnumCodec<BagType> CODEC = StringRepresentable.fromEnum(BagType::values);

    private final String name;
    private final DyeColor backgroundColor;
    private final DyeColor textColor;
    private final TagKey<Item> allow;
    private final TagKey<Item> disallow;

    BagType(String name, DyeColor backgroundColor, DyeColor textColor) {
        this.name = name;
        this.backgroundColor = backgroundColor;
        this.textColor = textColor;
        this.allow = TagKey.create(Registries.ITEM, BagOfHolding.id("allowed_in_" + name + "_bag"));
        this.disallow = TagKey.create(Registries.ITEM, BagOfHolding.id("not_allowed_in_" + name + "_bag"));
    }

    public int getInventoryRows() {
        return switch (this) {
            case LEATHER -> BagOfHolding.CONFIG.get(ServerConfig.class).leatherBagRows;
            case IRON -> BagOfHolding.CONFIG.get(ServerConfig.class).ironBagRows;
            case GOLDEN -> BagOfHolding.CONFIG.get(ServerConfig.class).goldenBagRows;
        };
    }

    public boolean mayPlaceInBag(ItemStack itemStack) {
        if (BuiltInRegistries.ITEM.getTagOrEmpty(this.allow).iterator().hasNext()) {
            return itemStack.is(this.allow);
        } else if (!itemStack.getItem().canFitInsideContainerItems()) {
            return false;
        } else {
            return !itemStack.is(this.disallow);
        }
    }

    public MenuType<? extends BagItemMenu> menuType() {
        return switch (this) {
            case LEATHER -> ModRegistry.LEATHER_BAG_OF_HOLDING_MENU_TYPE.value();
            case IRON -> ModRegistry.IRON_BAG_OF_HOLDING_MENU_TYPE.value();
            case GOLDEN -> ModRegistry.GOLDEN_BAG_OF_HOLDING_MENU_TYPE.value();
        };
    }

    @Override
    public String getSerializedName() {
        return this.name;
    }

    public DyeColor getBackgroundColor() {
        return this.backgroundColor;
    }

    public DyeColor getTextColor() {
        return this.textColor;
    }

    public TagKey<Item> getAllowTag() {
        return this.allow;
    }

    public TagKey<Item> getDisallowTag() {
        return this.disallow;
    }
}
