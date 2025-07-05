package com.ryu.minecraft.mod.neoforge.neostorage;

import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = NeoStorage.MODID)
public class Config {
    
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    
    protected static final ModConfigSpec SPEC = Config.BUILDER.build();
    
    @SubscribeEvent
    protected static void onLoad(final ModConfigEvent event) {
        // Nothing for now
    }
    
    private Config() {
    }
}
