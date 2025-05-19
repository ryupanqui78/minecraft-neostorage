package com.ryu.minecraft.mod.neoforge.neostorage.inventory.data;

import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ItemStored {
    private final ItemStack itemStack;
    private long count;
    private final TagKey<Item> tagItem;
    
    public ItemStored(ItemStack pItemStack, long pCount, TagKey<Item> pTagItem) {
        this.itemStack = pItemStack;
        this.count = pCount;
        this.tagItem = pTagItem;
    }
    
    public void addCount(long value) {
        this.count += value;
    }
    
    public long getCount() {
        return this.count;
    }
    
    public ItemStack getItemStack() {
        return this.itemStack;
    }
    
    public TagKey<Item> getTagItem() {
        return this.tagItem;
    }
}
