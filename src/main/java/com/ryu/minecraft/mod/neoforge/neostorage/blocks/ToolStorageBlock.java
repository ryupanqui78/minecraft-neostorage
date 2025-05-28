package com.ryu.minecraft.mod.neoforge.neostorage.blocks;

import com.mojang.serialization.MapCodec;
import com.ryu.minecraft.mod.neoforge.neostorage.blocks.entities.ToolStorageBlockEntity;
import com.ryu.minecraft.mod.neoforge.neostorage.enums.StorageType;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;

public class ToolStorageBlock extends BaseEntityBlock {
    
    public static final String BLOCK_NAME = "toolstorage";
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    public static final IntegerProperty LEVEL = IntegerProperty.create("visual_slot", 1, 4);
    public static final BooleanProperty OPEN = BlockStateProperties.OPEN;
    public static final MapCodec<ToolStorageBlock> CODEC = BlockBehaviour
            .simpleCodec(properties -> new ToolStorageBlock(StorageType.ONE_ITEM, properties));
    
    public ToolStorageBlock(StorageType pStorageType, Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ToolStorageBlock.FACING, Direction.NORTH)
                .setValue(ToolStorageBlock.OPEN, Boolean.valueOf(false))
                .setValue(ToolStorageBlock.LEVEL, pStorageType.getValue()));
    }
    
    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return ToolStorageBlock.CODEC;
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(ToolStorageBlock.FACING, ToolStorageBlock.OPEN, ToolStorageBlock.LEVEL);
    }
    
    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }
    
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(ToolStorageBlock.FACING,
                context.getHorizontalDirection().getOpposite());
    }
    
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new ToolStorageBlockEntity(pPos, pState);
    }
    
    @Override
    public void onRemove(BlockState pState, Level pLevel, BlockPos pPos, BlockState pNewState, boolean pMovedByPiston) {
        if (!pState.is(pNewState.getBlock())) {
            final BlockState airState = Blocks.AIR.defaultBlockState();
            if (pLevel.getBlockEntity(pPos) instanceof final ToolStorageBlockEntity be) {
                if (pLevel instanceof ServerLevel) {
                    Containers.dropContents(pLevel, pPos, be);
                }
                pLevel.setBlock(pPos, airState, 1);
                pLevel.updateNeighbourForOutputSignal(pPos, this);
            } else {
                pLevel.setBlock(pPos, airState, 1);
            }
        }
    }
    
    @Override
    protected InteractionResult useWithoutItem(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, BlockHitResult pHitResult) {
        if (pLevel.isClientSide) {
            return InteractionResult.SUCCESS;
        } else {
            this.validateAndOpenMenu(pLevel.getBlockEntity(pPos), pPlayer);
            return InteractionResult.CONSUME;
        }
    }
    
    private void validateAndOpenMenu(BlockEntity pBlockEntity, Player pPlayer) {
        if (pBlockEntity instanceof final ToolStorageBlockEntity toolStorageBlockEntity) {
            pPlayer.openMenu(toolStorageBlockEntity);
        }
    }
}
