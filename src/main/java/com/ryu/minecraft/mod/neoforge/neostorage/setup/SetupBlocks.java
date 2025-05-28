package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.ToolStorageBlock;
import com.ryu.minecraft.mod.neoforge.neostorage.enums.StorageType;
import com.ryu.minecraft.mod.neoforge.neostorage.exceptions.StorageBlockException;

import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlocks {
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoStorage.MODID);
    
    public static final List<DeferredBlock<ToolStorageBlock>> TOOL_STORAGE = List
            .copyOf(SetupBlocks.registerStorageLevels(ToolStorageBlock.BLOCK_NAME, 3f, ToolStorageBlock.class));
    
    private static ToolStorageBlock createBlockInstance(Class<? extends ToolStorageBlock> pStorageBlock, StorageType type, Properties properties) {
        try {
            return pStorageBlock.getConstructor(StorageType.class, Properties.class).newInstance(type, properties);
        } catch (final ReflectiveOperationException e) {
            throw new StorageBlockException(e);
        }
    }
    
    private static List<DeferredBlock<ToolStorageBlock>> registerStorageLevels(String pName, float pStrength, Class<? extends ToolStorageBlock> pStorageBlock) {
        final List<DeferredBlock<ToolStorageBlock>> registerBlocks = new ArrayList<>();
        final Properties properties = BlockBehaviour.Properties.of().strength(pStrength).requiresCorrectToolForDrops();
        Stream.of(StorageType.values()).forEach(type -> {
            final String nameBlock = pName + type.getValue();
            final DeferredBlock<ToolStorageBlock> registerBlock = SetupBlocks.BLOCKS.registerBlock(nameBlock,
                    p -> SetupBlocks.createBlockInstance(pStorageBlock, type, p), properties);
            SetupItems.ITEMS.registerSimpleBlockItem(registerBlock);
            registerBlocks.add(registerBlock);
        });
        
        return registerBlocks;
    }
    
    private SetupBlocks() {
    }
    
}
