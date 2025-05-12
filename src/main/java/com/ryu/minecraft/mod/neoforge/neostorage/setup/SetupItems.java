package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupItems {
    
    private static final String NEOTOOL_NAME = "neotool";
    
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoStorage.MODID);
    
    public static final DeferredItem<Item> NEOTOOL = SetupItems.ITEMS.registerSimpleItem(SetupItems.NEOTOOL_NAME);
    
    private SetupItems() {
    }
    
}
