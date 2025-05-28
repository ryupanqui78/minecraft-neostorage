package com.ryu.minecraft.mod.neoforge.neostorage.inventory.data;

import net.minecraft.tags.TagKey;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;

public class StorageMenuData {
    private final Container container;
    private final ContainerData dataMultiple;
    private final TagKey<Item> filterTag;
    
    public StorageMenuData(Container pContainer, ContainerData pDataMultiple, TagKey<Item> pFilterTag) {
        this.container = pContainer;
        this.dataMultiple = pDataMultiple;
        this.filterTag = pFilterTag;
    }
    
    public Container getContainer() {
        return this.container;
    }
    
    public ContainerData getData() {
        return this.dataMultiple;
    }
    
    public TagKey<Item> getFilterTag() {
        return this.filterTag;
    }
    
    public int getLevelSlots() {
        return this.dataMultiple.get(0);
    }
    
}
