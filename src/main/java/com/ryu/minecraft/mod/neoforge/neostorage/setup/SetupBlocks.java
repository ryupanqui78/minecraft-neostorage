package com.ryu.minecraft.mod.neoforge.neostorage.setup;

import com.ryu.minecraft.mod.neoforge.neostorage.NeoStorage;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.ToolStorageBlock;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public class SetupBlocks {
    
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(NeoStorage.MODID);
    
    public static final DeferredBlock<ToolStorageBlock> TOOL_STORAGE = SetupBlocks.BLOCKS.registerBlock(
            ToolStorageBlock.BLOCK_NAME, ToolStorageBlock::new,
            BlockBehaviour.Properties.of().strength(3f).requiresCorrectToolForDrops());
    
    public static final DeferredItem<BlockItem> TOOL_STORAGE_ITEM = SetupItems.ITEMS
            .registerSimpleBlockItem(TOOL_STORAGE);
    
}
