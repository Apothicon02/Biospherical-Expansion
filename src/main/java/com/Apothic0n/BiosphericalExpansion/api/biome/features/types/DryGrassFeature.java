package com.Apothic0n.BiosphericalExpansion.api.biome.features.types;

import com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.Half;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import static net.minecraft.world.level.block.Block.UPDATE_ALL;

public class DryGrassFeature extends Feature<NoneFeatureConfiguration> {
    public DryGrassFeature(Codec<NoneFeatureConfiguration> config) {
        super(config);
    }

    private static final PerlinSimplexNoise HEIGHT_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(5432L)), ImmutableList.of(-6, 1));

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        RandomSource random = pContext.random();
        BlockPos origin = pContext.origin();
        BlockState bottomState = BioxBlocks.DRY_GRASS.get().defaultBlockState().setValue(BlockStateProperties.HALF, Half.BOTTOM);
        BlockState topState = BioxBlocks.DRY_GRASS.get().defaultBlockState();
        boolean placedAnything = false;
        for (int x = origin.getX(); x <= origin.getX()+16; x++) {
            for (int z = origin.getZ(); z <= origin.getZ()+16; z++) {
                BlockPos blockPos = new BlockPos(x, worldgenlevel.getHeight(Heightmap.Types.WORLD_SURFACE_WG, x, z), z);
                BlockState belowState = worldgenlevel.getBlockState(blockPos.below());
                if (worldgenlevel.getBiome(blockPos).is(BiomeTags.IS_SAVANNA) && worldgenlevel.getBlockState(blockPos).is(Blocks.AIR) && belowState.is(BlockTags.DIRT)) {
                    if (belowState.is(Blocks.GRASS_BLOCK) || belowState.is(Blocks.MOSS_BLOCK)) {
                        double height = HEIGHT_NOISE.getValue(x, z, false);
                        if (height > 0.33) {
                            worldgenlevel.setBlock(blockPos, bottomState, UPDATE_ALL);
                            worldgenlevel.setBlock(blockPos.above(), bottomState, UPDATE_ALL);
                            worldgenlevel.setBlock(blockPos.above(2), topState, UPDATE_ALL);
                        } else {
                            worldgenlevel.setBlock(blockPos, bottomState, UPDATE_ALL);
                            worldgenlevel.setBlock(blockPos.above(), topState, UPDATE_ALL);
                        }
                        placedAnything = true;
                    } else if ((random.nextFloat()*4)+1 >= 2) {
                        worldgenlevel.setBlock(blockPos, topState, UPDATE_ALL);
                        placedAnything = true;
                    }
                }
            }
        }
        return placedAnything;
    }
}
