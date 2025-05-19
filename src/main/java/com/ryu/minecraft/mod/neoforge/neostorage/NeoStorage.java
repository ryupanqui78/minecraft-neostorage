package com.ryu.minecraft.mod.neoforge.neostorage;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupBlocks;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupCreativeModTab;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupItems;
import com.ryu.minecraft.mod.neoforge.neostorage.setup.SetupMenus;

import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;

// The value here should match an entry in the META-INF/neoforge.mods.toml file
@Mod(NeoStorage.MODID)
public class NeoStorage {
    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @EventBusSubscriber(modid = NeoStorage.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
            NeoStorage.LOGGER.info("HELLO FROM CLIENT SETUP");
            NeoStorage.LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }
    
    public static final String MODID = "neostorage";
    
    public static final Logger LOGGER = LogUtils.getLogger();
    
    public NeoStorage(IEventBus modEventBus, ModContainer modContainer) {
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);
        
        // Register the Deferred Register to the mod event bus so blocks get registered
        SetupBlocks.BLOCKS.register(modEventBus);
        SetupItems.ITEMS.register(modEventBus);
        SetupBlockEntity.BLOCK_ENTITIES.register(modEventBus);
        SetupCreativeModTab.CREATIVE_MODE_TABS.register(modEventBus);
        SetupMenus.MENUS.register(modEventBus);
        
        // Register ourselves for server and other game events we are interested in.
        // Note that this is necessary if and only if we want *this* class (NeoStorage) to respond directly to events.
        // Do not add this line if there are no @SubscribeEvent-annotated functions in this class, like onServerStarting() below.
        NeoForge.EVENT_BUS.register(this);
        
        // Register our mod's ModConfigSpec so that FML can create and load the config file for us
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }
    
    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
        NeoStorage.LOGGER.info("HELLO FROM COMMON SETUP");
        
        if (Config.logDirtBlock) {
            NeoStorage.LOGGER.info("DIRT BLOCK >> {}", BuiltInRegistries.BLOCK.getKey(Blocks.DIRT));
        }
        
        NeoStorage.LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
        
        Config.items.forEach((item) -> NeoStorage.LOGGER.info("ITEM >> {}", item.toString()));
    }
    
    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
        NeoStorage.LOGGER.info("HELLO from server starting");
    }
}
