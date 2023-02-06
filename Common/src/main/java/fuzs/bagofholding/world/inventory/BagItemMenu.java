package fuzs.bagofholding.world.inventory;

import com.google.common.collect.Sets;
import fuzs.bagofholding.api.world.inventory.SimpleContainerWithSlots;
import fuzs.bagofholding.world.item.BagOfHoldingItem;
import fuzs.puzzleslib.init.builder.ModMenuSupplier;
import net.minecraft.CrashReport;
import net.minecraft.CrashReportCategory;
import net.minecraft.CrashReportDetail;
import net.minecraft.ReportedException;
import net.minecraft.core.Registry;
import net.minecraft.util.Mth;
import net.minecraft.world.Container;
import net.minecraft.world.entity.SlotAccess;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ClickAction;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Set;

public class BagItemMenu extends AbstractContainerMenu {
    private final Container container;
    private final BagOfHoldingItem.Type bagType;
    private int hotbarStartIndex;
    private int quickcraftType = -1;
    private int quickcraftStatus;
    private final Set<Slot> quickcraftSlots = Sets.<Slot>newHashSet();

    public static ModMenuSupplier<BagItemMenu> create(BagOfHoldingItem.Type type) {
        return (int containerId, Inventory inventory) -> new BagItemMenu(type, containerId, inventory);
    }

    private BagItemMenu(BagOfHoldingItem.Type bagType, int containerId, Inventory inventory) {
        this(bagType, containerId, inventory, new SimpleContainerWithSlots(bagType.config().rows));
    }

    public BagItemMenu(BagOfHoldingItem.Type bagType, int containerId, Inventory inventory, Container container) {
        super(bagType.menuType(), containerId);
        this.bagType = bagType;
        final int containerRows = bagType.config().rows;
        checkContainerSize(container, containerRows * 9);
        this.container = container;
        container.startOpen(inventory.player);
        int i = (containerRows - 4) * 18;
        for (int j = 0; j < containerRows; ++j) {
            for (int k = 0; k < 9; ++k) {
                this.addSlot(new FilteredBagSlot(bagType, container, k + j * 9, 8 + k * 18, 18 + j * 18));
            }
        }
        for (int l = 0; l < 3; ++l) {
            for (int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new LockableInventorySlot(inventory, j1 + l * 9 + 9, 8 + j1 * 18, 103 + l * 18 + i));
            }
        }
        for (int i1 = 0; i1 < 9; ++i1) {
            Slot hotbarSlot = this.addSlot(new LockableInventorySlot(inventory, i1, 8 + i1 * 18, 161 + i));
            if (i1 == 0) {
                this.hotbarStartIndex = hotbarSlot.index;
            }
        }
    }

    @Override
    public boolean stillValid(Player p_40195_) {
        return this.container.stillValid(p_40195_);
    }

    @Override
    public ItemStack quickMoveStack(Player p_40199_, int p_40200_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_40200_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            if (p_40200_ < this.container.getContainerSize()) {
                if (!this.moveItemStackTo(itemstack1, this.container.getContainerSize(), this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 0, this.container.getContainerSize(), false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }

        return itemstack;
    }

    @Override
    public void removed(Player p_40197_) {
        super.removed(p_40197_);
        this.container.stopOpen(p_40197_);
    }

    public int getRowCount() {
        return this.bagType.config().rows;
    }

    @Nullable
    public DyeColor getBackgroundColor() {
        return this.bagType.backgroundColor;
    }

    @Nullable
    public DyeColor getTextColor() {
        return this.bagType.textColor;
    }

    public int getHotbarStartIndex() {
        return this.hotbarStartIndex;
    }

    public void clicked(int mouseX, int mouseY, ClickType clickType, Player player) {
        try {
            this.doClick(mouseX, mouseY, clickType, player);
        } catch (Exception var8) {
            CrashReport crashReport = CrashReport.forThrowable(var8, "Container click");
            CrashReportCategory crashReportCategory = crashReport.addCategory("Click info");
            crashReportCategory.setDetail(
                    "Menu Type", (CrashReportDetail<String>)(() -> this.getType() != null ? Registry.MENU.getKey(this.getType()).toString() : "<no type>")
            );
            crashReportCategory.setDetail("Menu Class", (CrashReportDetail<String>)(() -> this.getClass().getCanonicalName()));
            crashReportCategory.setDetail("Slot Count", this.slots.size());
            crashReportCategory.setDetail("Slot", mouseX);
            crashReportCategory.setDetail("Button", mouseY);
            crashReportCategory.setDetail("Type", clickType);
            throw new ReportedException(crashReport);
        }
    }

    private void doClick(int mouseX, int mouseY, ClickType clickType, Player player) {
        Inventory inventory = player.getInventory();
        if (clickType == ClickType.QUICK_CRAFT) {
            int i = this.quickcraftStatus;
            this.quickcraftStatus = getQuickcraftHeader(mouseY);
            if ((i != 1 || this.quickcraftStatus != 2) && i != this.quickcraftStatus) {
                this.resetQuickCraft();
            } else if (this.getCarried().isEmpty()) {
                this.resetQuickCraft();
            } else if (this.quickcraftStatus == 0) {
                this.quickcraftType = getQuickcraftType(mouseY);
                if (isValidQuickcraftType(this.quickcraftType, player)) {
                    this.quickcraftStatus = 1;
                    this.quickcraftSlots.clear();
                } else {
                    this.resetQuickCraft();
                }
            } else if (this.quickcraftStatus == 1) {
                Slot slot = this.slots.get(mouseX);
                ItemStack itemStack = this.getCarried();
                if (canItemQuickReplace(slot, itemStack, true)
                        && slot.mayPlace(itemStack)
                        && (this.quickcraftType == 2 || itemStack.getCount() > this.quickcraftSlots.size())
                        && this.canDragTo(slot)) {
                    this.quickcraftSlots.add(slot);
                }
            } else if (this.quickcraftStatus == 2) {
                if (!this.quickcraftSlots.isEmpty()) {
                    if (this.quickcraftSlots.size() == 1) {
                        int j = ((Slot)this.quickcraftSlots.iterator().next()).index;
                        this.resetQuickCraft();
                        this.doClick(j, this.quickcraftType, ClickType.PICKUP, player);
                        return;
                    }

                    ItemStack itemStack2 = this.getCarried().copy();
                    int k = this.getCarried().getCount();

                    for(Slot slot2 : this.quickcraftSlots) {
                        ItemStack itemStack3 = this.getCarried();
                        if (slot2 != null
                                && canItemQuickReplace(slot2, itemStack3, true)
                                && slot2.mayPlace(itemStack3)
                                && (this.quickcraftType == 2 || itemStack3.getCount() >= this.quickcraftSlots.size())
                                && this.canDragTo(slot2)) {
                            ItemStack itemStack4 = itemStack2.copy();
                            int l = slot2.hasItem() ? slot2.getItem().getCount() : 0;
                            getQuickCraftSlotCount(this.quickcraftSlots, this.quickcraftType, itemStack4, l, slot2);
                            int m = slot2.getMaxStackSize(itemStack4);
                            if (itemStack4.getCount() > m) {
                                itemStack4.setCount(m);
                            }

                            k -= itemStack4.getCount() - l;
                            slot2.set(itemStack4);
                        }
                    }

                    itemStack2.setCount(k);
                    this.setCarried(itemStack2);
                }

                this.resetQuickCraft();
            } else {
                this.resetQuickCraft();
            }
        } else if (this.quickcraftStatus != 0) {
            this.resetQuickCraft();
        } else if ((clickType == ClickType.PICKUP || clickType == ClickType.QUICK_MOVE) && (mouseY == 0 || mouseY == 1)) {
            ClickAction clickAction = mouseY == 0 ? ClickAction.PRIMARY : ClickAction.SECONDARY;
            if (mouseX == -999) {
                if (!this.getCarried().isEmpty()) {
                    if (clickAction == ClickAction.PRIMARY) {
                        player.drop(this.getCarried(), true);
                        this.setCarried(ItemStack.EMPTY);
                    } else {
                        player.drop(this.getCarried().split(1), true);
                    }
                }
            } else if (clickType == ClickType.QUICK_MOVE) {
                if (mouseX < 0) {
                    return;
                }

                Slot slot = this.slots.get(mouseX);
                if (!slot.mayPickup(player)) {
                    return;
                }

                ItemStack itemStack = this.quickMoveStack(player, mouseX);

                while(!itemStack.isEmpty() && ItemStack.isSame(slot.getItem(), itemStack)) {
                    itemStack = this.quickMoveStack(player, mouseX);
                }
            } else {
                if (mouseX < 0) {
                    return;
                }

                Slot slot = this.slots.get(mouseX);
                ItemStack itemStack = slot.getItem();
                ItemStack itemStack5 = this.getCarried();
                player.updateTutorialInventoryAction(itemStack5, slot.getItem(), clickAction);
                if (!itemStack5.overrideStackedOnOther(slot, clickAction, player)
                        && !itemStack.overrideOtherStackedOnMe(itemStack5, slot, clickAction, player, this.createCarriedSlotAccess())) {
                    if (itemStack.isEmpty()) {
                        if (!itemStack5.isEmpty()) {
                            int n = clickAction == ClickAction.PRIMARY ? itemStack5.getCount() : 1;
                            this.setCarried(slot.safeInsert(itemStack5, n));
                        }
                    } else if (slot.mayPickup(player)) {
                        if (itemStack5.isEmpty()) {
                            int n = clickAction == ClickAction.PRIMARY ? itemStack.getCount() : (itemStack.getCount() + 1) / 2;
                            Optional<ItemStack> optional = slot.tryRemove(n, Integer.MAX_VALUE, player);
                            optional.ifPresent(itemStack22 -> {
                                this.setCarried(itemStack22);
                                slot.onTake(player, itemStack22);
                            });
                        } else if (slot.mayPlace(itemStack5)) {
                            if (ItemStack.isSameItemSameTags(itemStack, itemStack5)) {
                                int n = clickAction == ClickAction.PRIMARY ? itemStack5.getCount() : 1;
                                this.setCarried(slot.safeInsert(itemStack5, n));
                            } else if (itemStack5.getCount() <= slot.getMaxStackSize(itemStack5)) {
                                this.setCarried(itemStack);
                                slot.set(itemStack5);
                            }
                        } else if (ItemStack.isSameItemSameTags(itemStack, itemStack5)) {
                            Optional<ItemStack> optional2 = slot.tryRemove(itemStack.getCount(), slot.getMaxStackSize(itemStack5) - itemStack5.getCount(), player);
                            optional2.ifPresent(itemStack2x -> {
                                itemStack5.grow(itemStack2x.getCount());
                                slot.onTake(player, itemStack2x);
                            });
                        }
                    }
                }

                slot.setChanged();
            }
        } else if (clickType == ClickType.SWAP) {
            Slot slot3 = this.slots.get(mouseX);
            ItemStack itemStack2 = inventory.getItem(mouseY);
            ItemStack itemStack = slot3.getItem();
            if (!itemStack2.isEmpty() || !itemStack.isEmpty()) {
                if (itemStack2.isEmpty()) {
                    if (slot3.mayPickup(player)) {
                        inventory.setItem(mouseY, itemStack);
//                        slot3.onSwapCraft(itemStack.getCount());
                        slot3.set(ItemStack.EMPTY);
                        slot3.onTake(player, itemStack);
                    }
                } else if (itemStack.isEmpty()) {
                    if (slot3.mayPlace(itemStack2)) {
                        int o = slot3.getMaxStackSize(itemStack2);
                        if (itemStack2.getCount() > o) {
                            slot3.set(itemStack2.split(o));
                        } else {
                            inventory.setItem(mouseY, ItemStack.EMPTY);
                            slot3.set(itemStack2);
                        }
                    }
                } else if (slot3.mayPickup(player) && slot3.mayPlace(itemStack2)) {
                    int o = slot3.getMaxStackSize(itemStack2);
                    if (itemStack2.getCount() > o) {
                        slot3.set(itemStack2.split(o));
                        slot3.onTake(player, itemStack);
                        if (!inventory.add(itemStack)) {
                            player.drop(itemStack, true);
                        }
                    } else {
                        inventory.setItem(mouseY, itemStack);
                        slot3.set(itemStack2);
                        slot3.onTake(player, itemStack);
                    }
                }
            }
        } else if (clickType == ClickType.CLONE && player.getAbilities().instabuild && this.getCarried().isEmpty() && mouseX >= 0) {
            Slot slot3 = this.slots.get(mouseX);
            if (slot3.hasItem()) {
                ItemStack itemStack2 = slot3.getItem().copy();
                itemStack2.setCount(slot3.getMaxStackSize(itemStack2));
                this.setCarried(itemStack2);
            }
        } else if (clickType == ClickType.THROW && this.getCarried().isEmpty() && mouseX >= 0) {
            Slot slot3 = this.slots.get(mouseX);
            int j = mouseY == 0 ? 1 : slot3.getItem().getCount();
            ItemStack itemStack = slot3.safeTake(j, Integer.MAX_VALUE, player);
            player.drop(itemStack, true);
        } else if (clickType == ClickType.PICKUP_ALL && mouseX >= 0) {
            Slot slot3 = this.slots.get(mouseX);
            ItemStack itemStack2 = this.getCarried();
            if (!itemStack2.isEmpty() && (!slot3.hasItem() || !slot3.mayPickup(player))) {
                int k = mouseY == 0 ? 0 : this.slots.size() - 1;
                int o = mouseY == 0 ? 1 : -1;

                for(int n = 0; n < 2; ++n) {
                    for(int p = k; p >= 0 && p < this.slots.size() && itemStack2.getCount() < this.slots.get(p).getMaxStackSize(itemStack2); p += o) {
                        Slot slot4 = this.slots.get(p);
                        if (slot4.hasItem() && canItemQuickReplace(slot4, itemStack2, true) && slot4.mayPickup(player) && this.canTakeItemForPickAll(itemStack2, slot4)) {
                            ItemStack itemStack6 = slot4.getItem();
                            if (n != 0 || itemStack6.getCount() != slot4.getMaxStackSize(itemStack6)) {
                                ItemStack itemStack7 = slot4.safeTake(itemStack6.getCount(), slot4.getMaxStackSize(itemStack2) - itemStack2.getCount(), player);
                                itemStack2.grow(itemStack7.getCount());
                            }
                        }
                    }
                }
            }
        }

    }

    public static void getQuickCraftSlotCount(Set<Slot> dragSlots, int dragMode, ItemStack stack, int slotStackSize, Slot slot) {
        switch(dragMode) {
            case 0:
                stack.setCount(Mth.floor((float)stack.getCount() / (float)dragSlots.size()));
                break;
            case 1:
                stack.setCount(1);
                break;
            case 2:
                stack.setCount(slot.getMaxStackSize(stack));
        }

        stack.grow(slotStackSize);
    }

    protected void resetQuickCraft() {
        this.quickcraftStatus = 0;
        this.quickcraftSlots.clear();
    }

    private SlotAccess createCarriedSlotAccess() {
        return new SlotAccess() {
            @Override
            public ItemStack get() {
                return getCarried();
            }

            @Override
            public boolean set(ItemStack carried) {
                setCarried(carried);
                return true;
            }
        };
    }

    protected boolean moveItemStackTo(ItemStack stack, int startIndex, int endIndex, boolean reverseDirection) {
        boolean bl = false;
        int i = startIndex;
        if (reverseDirection) {
            i = endIndex - 1;
        }

        if (true) {
            while(!stack.isEmpty()) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                Slot slot = this.slots.get(i);
                ItemStack itemStack = slot.getItem();
                if (!itemStack.isEmpty() && ItemStack.isSameItemSameTags(stack, itemStack)) {
                    int j = itemStack.getCount() + stack.getCount();
                    if (j <= slot.getMaxStackSize(stack)) {
                        stack.setCount(0);
                        itemStack.setCount(j);
                        slot.setChanged();
                        bl = true;
                    } else if (itemStack.getCount() < slot.getMaxStackSize(stack)) {
                        stack.shrink(slot.getMaxStackSize(stack) - itemStack.getCount());
                        itemStack.setCount(slot.getMaxStackSize(stack));
                        slot.setChanged();
                        bl = true;
                    }
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        if (!stack.isEmpty()) {
            if (reverseDirection) {
                i = endIndex - 1;
            } else {
                i = startIndex;
            }

            while(true) {
                if (reverseDirection) {
                    if (i < startIndex) {
                        break;
                    }
                } else if (i >= endIndex) {
                    break;
                }

                Slot slot = this.slots.get(i);
                ItemStack itemStack = slot.getItem();
                if (itemStack.isEmpty() && slot.mayPlace(stack)) {
                    if (stack.getCount() > slot.getMaxStackSize(stack)) {
                        slot.set(stack.split(slot.getMaxStackSize(stack)));
                    } else {
                        slot.set(stack.split(stack.getCount()));
                    }

                    slot.setChanged();
                    bl = true;
                    break;
                }

                if (reverseDirection) {
                    --i;
                } else {
                    ++i;
                }
            }
        }

        return bl;
    }

    public static boolean canItemQuickReplace(@Nullable Slot slot, ItemStack stack, boolean stackSizeMatters) {
        boolean bl = slot == null || !slot.hasItem();
        if (!bl && ItemStack.isSameItemSameTags(stack, slot.getItem())) {
            return slot.getItem().getCount() + (stackSizeMatters ? 0 : stack.getCount()) <= slot.getMaxStackSize(stack);
        } else {
            return bl;
        }
    }
}