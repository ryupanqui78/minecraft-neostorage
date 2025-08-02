package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.AbstractStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.ArmorStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.ToolStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.WeaponStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ArmorStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ToolStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.WeaponStorageBlockEntity;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlockEntity {
    
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister
            .create(BuiltInRegistries.BLOCK_ENTITY_TYPE, NeoStorage.MODID);
    
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ArmorStorageBlockEntity>> ARMOR_STORAGE = SetupBlockEntity.BLOCK_ENTITIES
            .register(ArmorStorageBlock.BLOCK_NAME,
                    () -> new BlockEntityType<>(ArmorStorageBlockEntity::new, SetupBlocks.ARMOR_STORAGE.stream()
                            .map(DeferredBlock<AbstractStorageBlock>::get).toList().toArray(new Block[0])));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<ToolStorageBlockEntity>> TOOL_STORAGE = SetupBlockEntity.BLOCK_ENTITIES
            .register(ToolStorageBlock.BLOCK_NAME,
                    () -> new BlockEntityType<>(ToolStorageBlockEntity::new, SetupBlocks.TOOL_STORAGE.stream()
                            .map(DeferredBlock<AbstractStorageBlock>::get).toList().toArray(new Block[0])));
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<WeaponStorageBlockEntity>> WEAPON_STORAGE = SetupBlockEntity.BLOCK_ENTITIES
            .register(WeaponStorageBlock.BLOCK_NAME,
                    () -> new BlockEntityType<>(WeaponStorageBlockEntity::new, SetupBlocks.WEAPON_STORAGE.stream()
                            .map(DeferredBlock<AbstractStorageBlock>::get).toList().toArray(new Block[0])));
    
    private SetupBlockEntity() {
    }
    
}
