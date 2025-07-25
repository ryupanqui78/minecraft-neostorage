package com.ryu.minecraft.mod.neoforge.neostorage.enums;

public enum StorageType {
    ONE_ITEM(1), TWO_ITEMS(2), THREE_ITEMS(3), FOUR_ITEMS(4);
    
    private final int value;
    
    private StorageType(int pValue) {
        this.value = pValue;
    }
    
    public int getValue() {
        return this.value;
    }
}
