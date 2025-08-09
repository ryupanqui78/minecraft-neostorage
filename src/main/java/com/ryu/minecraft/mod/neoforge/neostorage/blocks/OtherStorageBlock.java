package com.ryu.minecraft.mod.neoforge.neostorage.blocks;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.OtherStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.enums.StorageType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class OtherStorageBlock extends AbstractStorageBlock {
    
    public static final String BLOCK_NAME = "otherstorage";
    public static final MapCodec<OtherStorageBlock> CODEC = BlockBehaviour
            .simpleCodec(properties -> new OtherStorageBlock(StorageType.ONE_ITEM, properties));
    
    public OtherStorageBlock(StorageType pStorageType, Properties pProperties) {
        super(pStorageType, pProperties);
    }
    
    @Override
    protected MapCodec<OtherStorageBlock> codec() {
        return OtherStorageBlock.CODEC;
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new OtherStorageBlockEntity(pPos, pState);
    }
    
    @Override
    protected void validateAndOpenMenu(BlockEntity pBlockEntity, Player pPlayer) {
        if (pBlockEntity instanceof final OtherStorageBlockEntity storageBlockEntity) {
            pPlayer.openMenu(storageBlockEntity);
        }
    }
    
}
