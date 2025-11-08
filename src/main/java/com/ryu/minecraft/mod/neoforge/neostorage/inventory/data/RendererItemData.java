package com.ryu.minecraft.mod.neoforge.neostorage.inventory.data;

import net.minecraft.world.level.Level;

public class RendererItemData {
    private final int lightInValue;
    private final boolean showTextCount;
    private final float maxScale;
    private final Level level;
    
    public RendererItemData(int pLightInValue, boolean pShowTextCount, float pMaxScale, Level pLevel) {
        this.lightInValue = pLightInValue;
        this.showTextCount = pShowTextCount;
        this.maxScale = pMaxScale;
        this.level = pLevel;
    }
    
    public Level getLevel() {
        return this.level;
    }
    
    public int getLightInValue() {
        return this.lightInValue;
    }
    
    public float getMaxScale() {
        return this.maxScale;
    }
    
    public boolean showTextCount() {
        return this.showTextCount;
    }
    
}
