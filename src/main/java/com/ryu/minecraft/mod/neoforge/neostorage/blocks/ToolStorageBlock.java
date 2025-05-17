package com.ryu.minecraft.mod.neoforge.neostorage.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;

public class ToolStorageBlock extends Block {
    
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;
    
    public static final String BLOCK_NAME = "toolstorage";
    
    public ToolStorageBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any().setValue(ToolStorageBlock.FACING, Direction.NORTH));
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
        builder.add(ToolStorageBlock.FACING);
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
    
}
