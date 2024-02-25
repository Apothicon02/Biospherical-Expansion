package com.Apothic0n.BiosphericalExpansion.api.biome.features.types;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

public class NoodleRiverFeature extends Feature<NoneFeatureConfiguration> {
    public NoodleRiverFeature(Codec<NoneFeatureConfiguration> pContext) {
        super(pContext);
    }

    public static final PerlinSimplexNoise NOODLE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(-8, 1, 1, -1));

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        ChunkPos chunkOrigin = new ChunkPos(pContext.origin());
        BlockPos origin =  new BlockPos(chunkOrigin.getMiddleBlockX(), pContext.origin().getY(), chunkOrigin.getMiddleBlockZ());
        RandomSource random = pContext.random();

        for(int x = origin.getX() - 16; x < origin.getX() + 16; ++x) {
            for(int z = origin.getZ() - 16; z < origin.getZ() + 16; ++z) {
                double noise = NOODLE_NOISE.getValue(x, z, true);
                if (noise > 0 && noise < 0.01) {
                    replaceFromPos(worldgenlevel, new BlockPos((int) (x + ((Math.random()*5)-2)), 62, (int) (z + ((Math.random()*5)-2))), 8, 8, 8);
                }
            }
        }

        return true;
    }
    public void replaceFromPos(WorldGenLevel worldgenlevel, BlockPos blockpos, int i, int j, int k) {
        int l = Math.max(i, Math.max(j, k));

        for(BlockPos blockpos1 : BlockPos.withinManhattan(blockpos, i, j, k)) {
            if (blockpos1.distManhattan(blockpos) > l) {
                break;
            }
            if (worldgenlevel.getBlockState(blockpos1).is(BlockTags.OVERWORLD_CARVER_REPLACEABLES)) {
                if (blockpos1.getY() > blockpos.getY()) {
                    worldgenlevel.setBlock(blockpos1, Blocks.CAVE_AIR.defaultBlockState(), 2);
                } else {
                    worldgenlevel.setBlock(blockpos1, Blocks.WATER.defaultBlockState(), 2);
                }
                if (worldgenlevel.getBlockState(blockpos1.above()).getBlock() instanceof FallingBlock) {
                    worldgenlevel.setBlock(blockpos1.above(), Blocks.AIR.defaultBlockState(), 2);
                }
            }
        }
    }
}
