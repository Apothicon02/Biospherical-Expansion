package com.Apothic0n.BiosphericalExpansion.core.objects;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.phys.shapes.VoxelShape;

public class DryGrassBlock extends GrowingPlantHeadBlock {
    public static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 15.0D, 12.0D);
    public static final EnumProperty<Half> HALF = BlockStateProperties.HALF;

    public DryGrassBlock(BlockBehaviour.Properties p_154864_) {
        super(p_154864_, Direction.UP, SHAPE, false, 0.1D);
        this.registerDefaultState(this.getStateDefinition().any().setValue(HALF, Half.TOP));
    }

    protected int getBlocksToGrowWhenBonemealed(RandomSource p_222649_) {
        return NetherVines.getBlocksToGrowWhenBonemealed(p_222649_);
    }

    @Override
    public BlockState updateShape(BlockState blockState, Direction direction, BlockState blockState2, LevelAccessor levelAccessor, BlockPos blockPos, BlockPos blockPos2) {
        BlockState belowState = levelAccessor.getBlockState(blockPos.below());
        if (!belowState.is(BlockTags.DIRT) && !belowState.is(BioxBlocks.DRY_GRASS.get())) {
            return Blocks.AIR.defaultBlockState();
        } else if (levelAccessor.getBlockState(blockPos.above()).is(BioxBlocks.DRY_GRASS.get())) {
            return this.defaultBlockState().setValue(HALF, Half.BOTTOM);
        }
        return this.defaultBlockState().setValue(HALF, Half.TOP);
    }

    protected Block getBodyBlock() {
        return BioxBlocks.DRY_GRASS.get();
    }

    protected boolean canGrowInto(BlockState p_154869_) {
        return NetherVines.isValidGrowthState(p_154869_);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> stateBuilder) {
        stateBuilder.add(HALF, AGE);
    }
}
