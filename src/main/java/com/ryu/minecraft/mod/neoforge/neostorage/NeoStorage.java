package com.ryu.minecraft.mod.neoforge.neostorage;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupCreativeModTab;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupItems;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupMenus;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;

@Mod(NeoStorage.MODID)
public class NeoStorage {
    
    public static final String MODID = "neostorage";
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public NeoStorage(IEventBus modEventBus, ModContainer modContainer) {
        // Register the Deferred Register to the mod event bus so blocks get registered
        SetupBlocks.BLOCKS.register(modEventBus);
        SetupItems.ITEMS.register(modEventBus);
        SetupBlockEntity.BLOCK_ENTITIES.register(modEventBus);
        SetupCreativeModTab.CREATIVE_MODE_TABS.register(modEventBus);
        SetupMenus.MENUS.register(modEventBus);
        
        modContainer.registerConfig(ModConfig.Type.COMMON, NeoStorageConfig.SPEC);
    }
    
}
