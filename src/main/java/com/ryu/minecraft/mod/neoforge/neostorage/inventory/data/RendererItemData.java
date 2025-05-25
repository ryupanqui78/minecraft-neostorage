package com.ryu.minecraft.mod.neoforge.neostorage.inventory.data;

import net.minecraft.world.level.Level;

public class RendererItemData {
    private final int lightInValue;
    private final int overlayInValue;
    private final float maxScale;
    private final Level level;
    
    public RendererItemData(int pLightInValue, int pOverlayInValue, float pMaxScale, Level pLevel) {
        this.lightInValue = pLightInValue;
        this.overlayInValue = pOverlayInValue;
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
    
    public int getOverlayInValue() {
        return this.overlayInValue;
    }
    
}
