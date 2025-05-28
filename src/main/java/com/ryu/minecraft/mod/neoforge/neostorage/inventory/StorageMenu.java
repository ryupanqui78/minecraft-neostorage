package com.ryu.minecraft.mod.neoforge.neostorage.inventory;

import java.util.List;

import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ToolStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.helpers.StorageHelper;
import com.ryu.minecraft.mod.neoforge.neostorage.inventory.data.StorageMenuData;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupMenus;

import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class StorageMenu extends AbstractContainerMenu {
    
    public class SlotContent extends Slot {
        
        public SlotContent(Container pContainer, int pSlot, int pX, int pY) {
            super(pContainer, pSlot, pX, pY);
        }
        
        @Override
        public boolean mayPlace(ItemStack pStack) {
            return StorageMenu.this.isValidItem(pStack);
        }
        
    }
    
    public static final String MENU_NAME = "storage";
    public static final int NUMBER_DATA_CONTAINER = 1;
    public static final int NUMBER_SLOTS_CONTAINER = StorageMenu.NUMBER_OF_ROWS * StorageMenu.COLUMNS_PER_ROW;
    
    private static final int NUMBER_OF_ROWS = 6;
    private static final int COLUMNS_PER_ROW = 9;
    private static final int CONTENT_SLOT_START_POS_X = 31;
    private static final int CONTENT_SLOT_START_POS_Y = 19;
    private static final int SIZE_SLOT = 18;
    
    private final StorageMenuData data;
    
    public StorageMenu(int pContainerId, Inventory pPlayerInventory) {
        this(pContainerId, pPlayerInventory,
                new StorageMenuData(new SimpleContainer(StorageMenu.NUMBER_SLOTS_CONTAINER),
                        new SimpleContainerData(StorageMenu.NUMBER_DATA_CONTAINER), ItemTags.EQUIPPABLE_ENCHANTABLE));
    }
    
    public StorageMenu(int pContainerId, Inventory pInventory, StorageMenuData menuData) {
        super(SetupMenus.STORAGE_CONTAINER.get(), pContainerId);
        AbstractContainerMenu.checkContainerSize(menuData.getContainer(), StorageMenu.NUMBER_SLOTS_CONTAINER);
        AbstractContainerMenu.checkContainerDataCount(menuData.getData(), StorageMenu.NUMBER_DATA_CONTAINER);
        
        this.data = menuData;
        menuData.getContainer().startOpen(pInventory.player);
        
        for (int j = 0; j < StorageMenu.NUMBER_OF_ROWS; ++j) {
            for (int k = 0; k < StorageMenu.COLUMNS_PER_ROW; ++k) {
                final int indexContainer = k + (j * StorageMenu.COLUMNS_PER_ROW);
                final int posX = StorageMenu.CONTENT_SLOT_START_POS_X + (k * StorageMenu.SIZE_SLOT);
                this.addSlot(new SlotContent(menuData.getContainer(), indexContainer, posX,
                        StorageMenu.CONTENT_SLOT_START_POS_Y + (j * StorageMenu.SIZE_SLOT)));
            }
        }
        
        StorageHelper.addDefaultInventorySlots(pInventory, StorageMenu.CONTENT_SLOT_START_POS_X, 201, 143,
                this::addSlot);
        
        this.addDataSlots(this.data.getData());
    }
    
    public Container getContainer() {
        return this.data.getContainer();
    }
    
    public int getLevelSlot() {
        return this.data.getLevelSlots();
    }
    
    protected boolean isValidItem(ItemStack pStack) {
        boolean isValid = false;
        if (this.data.getContainer().isEmpty()) {
            isValid = pStack.is(this.data.getFilterTag());
        } else if (this.data.getContainer() instanceof final ToolStorageBlockEntity be) {
            final List<TagKey<Item>> listTags = be.getCurrentTagKeys();
            isValid = listTags.stream().anyMatch(pStack::is);
            if (!isValid && (listTags.size() < be.getLevelSlots())) {
                isValid = pStack.is(this.data.getFilterTag());
            }
        }
        return isValid;
    }
    
    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        final Slot slot = this.slots.get(pIndex);
        if (!slot.hasItem()) {
            return ItemStack.EMPTY;
        }
        
        final ItemStack itemstack1 = slot.getItem();
        final ItemStack itemstack = itemstack1.copy();
        if ((pIndex < StorageMenu.NUMBER_SLOTS_CONTAINER)
                && !this.moveItemStackTo(itemstack1, StorageMenu.NUMBER_SLOTS_CONTAINER, this.slots.size(), true)) {
            return ItemStack.EMPTY;
        } else {
            if (!this.moveItemStackTo(itemstack1, 0, StorageMenu.NUMBER_SLOTS_CONTAINER, false)) {
                return ItemStack.EMPTY;
            }
        }
        
        if (itemstack1.isEmpty()) {
            slot.setByPlayer(ItemStack.EMPTY);
        } else {
            slot.setChanged();
        }
        
        return itemstack;
    }
    
    @Override
    public void removed(Player pPlayer) {
        super.removed(pPlayer);
        this.data.getContainer().stopOpen(pPlayer);
    }
    
    @Override
    public boolean stillValid(Player pPlayer) {
        return this.data.getContainer().stillValid(pPlayer);
    }
    
}
