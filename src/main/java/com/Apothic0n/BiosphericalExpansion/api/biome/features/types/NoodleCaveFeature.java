package com.Apothic0n.BiosphericalExpansion.api.biome.features.types;

import com.Apothic0n.BiosphericalExpansion.core.BioxMath;
import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

import static com.Apothic0n.BiosphericalExpansion.core.BioxMath.booleanToInt;

public class NoodleCaveFeature extends Feature<NoneFeatureConfiguration> {
    public NoodleCaveFeature(Codec<NoneFeatureConfiguration> pContext) {
        super(pContext);
    }

    private static final PerlinSimplexNoise NOODLE_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(-6, 1, -1, 1));

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        ChunkPos chunkOrigin = new ChunkPos(pContext.origin());
        BlockPos origin =  new BlockPos(chunkOrigin.getMiddleBlockX(), pContext.origin().getY(), chunkOrigin.getMiddleBlockZ());
        RandomSource random = pContext.random();

        for(int x = origin.getX() - 16; x < origin.getX() + 16; ++x) {
            for(int z = origin.getZ() - 16; z < origin.getZ() + 16; ++z) {
                double noise = NOODLE_NOISE.getValue(x, z, true);
                double avoidNoise = NoodleRiverFeature.NOODLE_NOISE.getValue(x, z, true);
                if (noise > 0 && noise < 0.01 && (avoidNoise < -0.2 || avoidNoise > 0.2)) {
                    BlockPos heightmapPos = worldgenlevel.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, new BlockPos(x, 0, z));
                    BlockPos blockpos = new BlockPos(heightmapPos.getX(), BioxMath.mid(heightmapPos.getY()-27, scanDownUntilPositive(worldgenlevel.getMinBuildHeight() + 15, heightmapPos)), heightmapPos.getZ());
                    if (blockpos.getY() > worldgenlevel.getMinBuildHeight()+15 && blockpos.getY() < worldgenlevel.getMaxBuildHeight()-15) {
                        if (worldgenlevel.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, new BlockPos(x-6, 0, z)).getY() > heightmapPos.getY()-4 &&
                                worldgenlevel.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, new BlockPos(x+6, 0, z)).getY() > heightmapPos.getY()-4 &&
                                worldgenlevel.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, new BlockPos(x, 0, z-6)).getY() > heightmapPos.getY()-4 &&
                                worldgenlevel.getHeightmapPos(Heightmap.Types.OCEAN_FLOOR_WG, new BlockPos(x, 0, z+6)).getY() > heightmapPos.getY()-4) {
                            if (NOODLE_NOISE.getValue(x, blockpos.getY(), true) > 0) {
                                replaceFromPos(worldgenlevel, blockpos, 6, 12, 7);
                            } else {
                                replaceFromPos(worldgenlevel, blockpos, 7, 9, 6);
                            }
                        }
                    }
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
            BlockState originalBlockState = worldgenlevel.getBlockState(blockpos1);
            int waterSources = booleanToInt(worldgenlevel.getBlockState(blockpos1.north()).is(Blocks.WATER))+booleanToInt(worldgenlevel.getBlockState(blockpos1.east()).is(Blocks.WATER))+
                    booleanToInt(worldgenlevel.getBlockState(blockpos1.south()).is(Blocks.WATER))+booleanToInt(worldgenlevel.getBlockState(blockpos1.west()).is(Blocks.WATER));
            if (originalBlockState.getBlock() instanceof FallingBlock) {
                worldgenlevel.setBlock(blockpos1, Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
            } else if (waterSources == 2) {
                worldgenlevel.setBlock(blockpos1, Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
                worldgenlevel.setBlock(blockpos1.north(), Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
                worldgenlevel.setBlock(blockpos1.east(), Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
                worldgenlevel.setBlock(blockpos1.south(), Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
                worldgenlevel.setBlock(blockpos1.west(), Blocks.DRIPSTONE_BLOCK.defaultBlockState(), 2);
            } else if (originalBlockState.is(BlockTags.OVERWORLD_CARVER_REPLACEABLES)) {
                worldgenlevel.setBlock(blockpos1, Blocks.CAVE_AIR.defaultBlockState(), 2);
            }
        }
    }

    public int scanDownUntilPositive(int minHeight, BlockPos blockPos) {
        for (int y = blockPos.getY(); y > minHeight; y--) {
            int noise = (int) NOODLE_NOISE.getValue(blockPos.getX(), blockPos.getY(), true);
            if (noise > 0) {
                return noise;
            }
        }
        return blockPos.getY();
    }
}
