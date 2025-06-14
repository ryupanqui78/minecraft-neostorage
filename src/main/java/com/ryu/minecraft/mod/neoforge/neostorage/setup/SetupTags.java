package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SetupTags {
    
    public static final TagKey<Item> BRUSHES = ItemTags.create(new ResourceLocation(NeoStorage.MODID, "brushes"));
    public static final TagKey<Item> ELYTRAS = ItemTags.create(new ResourceLocation(NeoStorage.MODID, "elytras"));
    public static final TagKey<Item> SHEARS = ItemTags.create(new ResourceLocation(NeoStorage.MODID, "shears"));
    public static final TagKey<Item> SHIELDS = ItemTags.create(new ResourceLocation(NeoStorage.MODID, "shields"));
    
    public static final TagKey<Item> STORE_TOOLS = ItemTags
            .create(new ResourceLocation(NeoStorage.MODID, "store_tools"));
    public static final TagKey<Item> STORE_WEAPONS = ItemTags
            .create(new ResourceLocation(NeoStorage.MODID, "store_weapons"));
    
    private SetupTags() {
    }
    
}
