package com.Apothic0n.BiosphericalExpansion.api.biome.features.decorators;

import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelSimulatedReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.RegistryObject;

import static com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks.wallBlocks;

public class HangingLeavesDecorator extends TreeDecorator {
    public static final Codec<HangingLeavesDecorator> CODEC = Codec.floatRange(0.0f, 1.0f).fieldOf("probability").xmap(HangingLeavesDecorator::new, hangingLeavesDecorator -> Float.valueOf(hangingLeavesDecorator.probability)).codec();
    private final float probability;

    public HangingLeavesDecorator(float probability) {
        this.probability = probability;
    }

    @Override
    protected TreeDecoratorType<?> type() {
        return BioxTreeDecoratorType.HANGING_LEAVES.get();
    }

    @Override
    public void place(Context context) {
        LevelSimulatedReader level = context.level();
        RandomSource randomSource = context.random();
        ObjectArrayList<BlockPos> list = context.leaves();
        list.forEach(blockPos -> {
            if ((Math.random()*(100)+1)/100 <= (probability/4)) {
                boolean anySuccess = placeColumn(context, level, blockPos, Blocks.OAK_LEAVES);
                if (!anySuccess) {
                    anySuccess = placeColumn(context, level, blockPos, Blocks.DARK_OAK_LEAVES);
                    if (!anySuccess) {
                        anySuccess = placeColumn(context, level, blockPos, Blocks.BIRCH_LEAVES);
                        if (!anySuccess) {
                            anySuccess = placeColumn(context, level, blockPos, Blocks.SPRUCE_LEAVES);
                            if (!anySuccess) {
                                anySuccess = placeColumn(context, level, blockPos, Blocks.JUNGLE_LEAVES);
                                if (!anySuccess) {
                                    anySuccess = placeColumn(context, level, blockPos, Blocks.ACACIA_LEAVES);
                                    if (!anySuccess) {
                                        anySuccess = placeColumn(context, level, blockPos, Blocks.MANGROVE_LEAVES);
                                        if (!anySuccess) {
                                            anySuccess = placeColumn(context, level, blockPos, Blocks.CHERRY_LEAVES);
                                            if (!anySuccess) {
                                                anySuccess = placeColumn(context, level, blockPos, Blocks.AZALEA_LEAVES);
                                                if (!anySuccess) {
                                                    placeColumn(context, level, blockPos, Blocks.FLOWERING_AZALEA_LEAVES);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        });
    }

    private static boolean placeColumn(Context context, LevelSimulatedReader level, BlockPos blockPos, Block hangingBlock) {
        Block block = Blocks.AIR;
        for (int o = 0; o < wallBlocks.size(); o++) {
            RegistryObject<Block> blockRegistryObject = wallBlocks.get(o).get(hangingBlock);
            if (blockRegistryObject != null) {
                block = blockRegistryObject.get();
                o = 420;
            }
        }
        BlockState blockState = block.defaultBlockState();
        Boolean placedAny = false;
        int maxLength = 16;
        if (hangingBlock == Blocks.DARK_OAK_LEAVES || hangingBlock == Blocks.JUNGLE_LEAVES) {
            maxLength = 32;
        }
        if (hangingBlock == Blocks.AZALEA_LEAVES || hangingBlock == Blocks.FLOWERING_AZALEA_LEAVES || hangingBlock == Blocks.CHERRY_LEAVES) {
            maxLength = 4;
        }
        if (hangingBlock == Blocks.SPRUCE_LEAVES || hangingBlock == Blocks.BIRCH_LEAVES) {
            maxLength = 2;
        }
        if (hangingBlock == Blocks.ACACIA_LEAVES) {
            maxLength = 1;
        }
        for (int h = 0; h < Math.random()*(maxLength)+1; h++) {
            if (level.isStateAtPosition(blockPos.below(h).above(), BlockStatePredicate.forBlock(hangingBlock).or(BlockStatePredicate.forBlock(block))) && level.isStateAtPosition(blockPos.below(h), BlockStatePredicate.forBlock(Blocks.AIR))) {
                context.setBlock(blockPos.below(h), blockState);
                placedAny = true;
            }
        }
        return placedAny;
    }
}