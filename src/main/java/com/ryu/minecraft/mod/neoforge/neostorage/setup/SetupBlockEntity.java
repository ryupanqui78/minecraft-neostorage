package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.ToolStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ToolStorageBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlockEntity {
    
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NeoStorage.MODID);
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ToolStorageBlockEntity>> TOOL_STORAGE = SetupBlockEntity.BLOCK_ENTITIES
            .register(ToolStorageBlock.BLOCK_NAME, () -> BlockEntityType.Builder
                    .of(ToolStorageBlockEntity::new, SetupBlocks.TOOL_STORAGE.get()).build(null));
    
    private SetupBlockEntity() {
    }
    
}
