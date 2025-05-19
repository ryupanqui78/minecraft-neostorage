package com.ryu.minecraft.mod.neoforge.neostorage.helpers;

import java.util.function.Consumer;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;

public class StorageHelper {
    
    private static final int COLUMNS_PER_ROW = 9;
    private static final int SIZE_SLOT = 18;
    
    public static void addDefaultInventorySlots(Inventory pInventory, int pSlotPosX, int pSlotHotbarPosY, int pSlotInventoryPosY, Consumer<Slot> pMenu) {
        int indexInventory = 0;
        
        for (int k = 0; k < StorageHelper.COLUMNS_PER_ROW; k++) {
            final int posX = pSlotPosX + (k * StorageHelper.SIZE_SLOT);
            pMenu.accept(new Slot(pInventory, indexInventory, posX, pSlotHotbarPosY));
            indexInventory++;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < StorageHelper.COLUMNS_PER_ROW; j++) {
                final int posX = pSlotPosX + (j * StorageHelper.SIZE_SLOT);
                pMenu.accept(
                        new Slot(pInventory, indexInventory, posX, pSlotInventoryPosY + (i * StorageHelper.SIZE_SLOT)));
                indexInventory++;
            }
        }
    }
    
    private StorageHelper() {
    }
    
}
