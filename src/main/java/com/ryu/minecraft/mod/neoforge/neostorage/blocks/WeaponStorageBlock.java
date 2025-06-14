package com.ryu.minecraft.mod.neoforge.neostorage.blocks;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.WeaponStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.enums.StorageType;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WeaponStorageBlock extends AbstractStorageBlock {
    
    public static final String BLOCK_NAME = "weaponstorage";
    public static final MapCodec<WeaponStorageBlock> CODEC = BlockBehaviour
            .simpleCodec(properties -> new WeaponStorageBlock(StorageType.ONE_ITEM, properties));
    
    public WeaponStorageBlock(StorageType pStorageType, Properties pProperties) {
        super(pStorageType, pProperties);
    }
    
    @Override
    protected MapCodec<WeaponStorageBlock> codec() {
        return WeaponStorageBlock.CODEC;
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WeaponStorageBlockEntity(pPos, pState);
    }
    
    @Override
    protected void validateAndOpenMenu(BlockEntity pBlockEntity, Player pPlayer) {
        if (pBlockEntity instanceof final WeaponStorageBlockEntity weaponStorageBlockEntity) {
            pPlayer.openMenu(weaponStorageBlockEntity);
        }
    }
    
}
