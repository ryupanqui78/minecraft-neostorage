package com.ryu.minecraft.mod.neoforge.neostorage.blocks;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ToolStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.enums.StorageType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ToolStorageBlock extends AbstractStorageBlock {
    
    public static final String BLOCK_NAME = "toolstorage";
    public static final MapCodec<ToolStorageBlock> CODEC = BlockBehaviour
            .simpleCodec(properties -> new ToolStorageBlock(StorageType.ONE_ITEM, properties));
    
    public ToolStorageBlock(StorageType pStorageType, Properties properties) {
        super(pStorageType, properties);
    }
    
    @Override
    protected MapCodec<ToolStorageBlock> codec() {
        return ToolStorageBlock.CODEC;
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ToolStorageBlockEntity(pPos, pState);
    }
    
    @Override
    protected void validateAndOpenMenu(BlockEntity pBlockEntity, Player pPlayer) {
        if (pBlockEntity instanceof final ToolStorageBlockEntity toolStorageBlockEntity) {
            pPlayer.openMenu(toolStorageBlockEntity);
        }
    }
}
