package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class SetupTags {
    
    public static final TagKey<Item> BRUSHES = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "brushes"));
    public static final TagKey<Item> ELYTRAS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "elytras"));
    public static final TagKey<Item> FILLED_MAPS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "filled_maps"));
    public static final TagKey<Item> FLIT_AND_STEELS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "flint_and_steels"));
    public static final TagKey<Item> GOAT_HORNS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "goat_horns"));
    public static final TagKey<Item> HORSE_ARMORS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "horse_armors"));
    public static final TagKey<Item> POTIONS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "potions"));
    public static final TagKey<Item> SADDLES = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "saddles"));
    public static final TagKey<Item> SHEARS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "shears"));
    public static final TagKey<Item> SHIELDS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "shields"));
    public static final TagKey<Item> SOUPS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "soups"));
    public static final TagKey<Item> SPYGLASSES = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "spyglasses"));
    public static final TagKey<Item> TOTEMS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "totems"));
    public static final TagKey<Item> WOLF_ARMORS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "wolf_armors"));
    public static final TagKey<Item> WRITABLE_BOOKS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "writable_books"));
    
    public static final TagKey<Item> STORE_ARMORS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "store_armors"));
    public static final TagKey<Item> STORE_OTHERS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "store_others"));
    public static final TagKey<Item> STORE_TOOLS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "store_tools"));
    public static final TagKey<Item> STORE_WEAPONS = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "store_weapons"));
    
    public static final TagKey<Item> UPGRADES = ItemTags
            .create(ResourceLocation.fromNamespaceAndPath(NeoStorage.MODID, "upgrades"));
    
    private SetupTags() {
    }
    
}
