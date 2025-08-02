package com.ryu.minecraft.mod.neoforge.neostorage.blocks;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ArmorStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.enums.StorageType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class ArmorStorageBlock extends AbstractStorageBlock {
    
    public static final String BLOCK_NAME = "armorstorage";
    public static final MapCodec<ArmorStorageBlock> CODEC = BlockBehaviour
            .simpleCodec(properties -> new ArmorStorageBlock(StorageType.ONE_ITEM, properties));
    
    public ArmorStorageBlock(StorageType pStorageType, Properties pProperties) {
        super(pStorageType, pProperties);
    }
    
    @Override
    protected MapCodec<ArmorStorageBlock> codec() {
        return ArmorStorageBlock.CODEC;
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ArmorStorageBlockEntity(pPos, pState);
    }
    
    @Override
    protected void validateAndOpenMenu(BlockEntity pBlockEntity, Player pPlayer) {
        if (pBlockEntity instanceof final ArmorStorageBlockEntity armorStorageBlockEntity) {
            pPlayer.openMenu(armorStorageBlockEntity);
        }
    }
    
}
