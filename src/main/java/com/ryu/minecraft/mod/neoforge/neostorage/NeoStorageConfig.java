package com.ryu.minecraft.mod.neoforge.neostorage;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = NeoStorage.MODID, bus = EventBusSubscriber.Bus.MOD)
public class NeoStorageConfig {
    
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    
    static final ModConfigSpec SPEC = NeoStorageConfig.BUILDER.build();
    
    @SubscribeEvent
    static void onLoad(final ModConfigEvent event) {
        NeoStorage.LOGGER.debug("Loading configuration ...");
    }
    
    private NeoStorageConfig() {
    }
    
}
