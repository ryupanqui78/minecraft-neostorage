package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.item.UpgradeLevelItem;
import com.ryu.minecraft.mod.neoforge.neostorage.item.UpgradeTier;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupItems {
    
    private static final String NEOTOOL_NAME = "neotool";
    
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(NeoStorage.MODID);
    
    public static final DeferredItem<Item> NEOTOOL = SetupItems.ITEMS.registerSimpleItem(SetupItems.NEOTOOL_NAME);
    public static final DeferredItem<UpgradeLevelItem> UPGRADE_COPPER = SetupItems.ITEMS
            .register(UpgradeLevelItem.COPPER_ITEM, registryName -> new UpgradeLevelItem(UpgradeTier.COPPER,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, registryName))));
    public static final DeferredItem<UpgradeLevelItem> UPGRADE_IRON = SetupItems.ITEMS
            .register(UpgradeLevelItem.IRON_ITEM, registryName -> new UpgradeLevelItem(UpgradeTier.IRON,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, registryName))));
    public static final DeferredItem<UpgradeLevelItem> UPGRADE_EMERALD = SetupItems.ITEMS
            .register(UpgradeLevelItem.EMERALD_ITEM, registryName -> new UpgradeLevelItem(UpgradeTier.EMERALD,
                    new Item.Properties().setId(ResourceKey.create(Registries.ITEM, registryName))));
    
    private SetupItems() {
    }
    
}
