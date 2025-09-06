package com.ryu.minecraft.mod.neoforge.neostorage;

import net.neoforged.neoforge.common.ModConfigSpec;

public class NeoStorageConfig {
    
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();
    
    static final ModConfigSpec SPEC = NeoStorageConfig.BUILDER.build();
    
    private NeoStorageConfig() {
    }
    
}
