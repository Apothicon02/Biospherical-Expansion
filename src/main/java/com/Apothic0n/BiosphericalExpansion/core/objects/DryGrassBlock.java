package com.Apothic0n.BiosphericalExpansion.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;

public class DryGrassBlock extends Block {
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public DryGrassBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HALF, Half.TOP));
    }

    @Override
    public void neighborChanged(BlockState blockState, Level level, BlockPos blockPos, Block block, BlockPos blockPos2, boolean bool) {
        if (!level.isClientSide()) {
            BlockState belowState = level.getBlockState(blockPos.below());
            if (!belowState.is(BlockTags.DIRT) && !belowState.is(BioxBlocks.DRY_GRASS.get())) {
                level.setBlock(blockPos, Blocks.AIR.defaultBlockState(), UPDATE_ALL);
            } else if (level.getBlockState(blockPos.above()).is(BioxBlocks.DRY_GRASS.get())) {
                level.setBlock(blockPos, this.defaultBlockState().setValue(HALF, Half.BOTTOM), UPDATE_ALL);
            }
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(HALF);
    }
}
