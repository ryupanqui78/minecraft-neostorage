package com.ryu.minecraft.mod.neoforge.neostorage.item;

public enum UpgradeTier {
    COPPER(1), IRON(2), EMERALD(3);
    
    private final int level;
    
    UpgradeTier(int level) {
        this.level = level;
    }
    
    public int getLevel() {
        return this.level;
    }
}
