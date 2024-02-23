package com.Apothic0n.BiosphericalExpansion.api.biome.features.types;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;

public class PathFeature extends Feature<NoneFeatureConfiguration> {
    public PathFeature(Codec<NoneFeatureConfiguration> pContext) {
        super(pContext);
    }

    private static final PerlinSimplexNoise PATH_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(-10, 1, 1, 1));

    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> pContext) {
        WorldGenLevel worldgenlevel = pContext.level();
        ChunkPos chunkOrigin = new ChunkPos(pContext.origin());
        BlockPos origin =  new BlockPos(chunkOrigin.getMiddleBlockX(), pContext.origin().getY(), chunkOrigin.getMiddleBlockZ());
        RandomSource random = pContext.random();

        for(int x = origin.getX() - 16; x < origin.getX() + 16; ++x) {
            for(int z = origin.getZ() - 16; z < origin.getZ() + 16; ++z) {
                double noise = PATH_NOISE.getValue(x, z, true);
                if (noise > 0 && noise < 0.01) {
                    BlockPos blockpos = worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, new BlockPos(x, 0, z));
                    if (blockpos.getY() > 64 && blockpos.getY() < 142) {
                        replaceFromPos(worldgenlevel, blockpos, 4, 2, 4);
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
            if (isSteep(worldgenlevel, blockpos1) == false) {
                BlockState originalBlockState = worldgenlevel.getBlockState(blockpos1);
                BlockState blockstate = getPathBlock(originalBlockState);
                if (blockstate.isSolid()) {
                    this.setBlock(worldgenlevel, blockpos1, blockstate);
                    BlockPos offsetPos = blockpos1.south(blockpos1.getX() - blockpos.getX()).west(blockpos1.getZ() - blockpos.getZ());
                    Block adjacentBlock = worldgenlevel.getBlockState(offsetPos).getBlock();
                    if (adjacentBlock == originalBlockState.getBlock()) {
                        BlockState edgeBlockState = getEdgePathBlock(blockstate);
                        if (edgeBlockState.isSolid()) {
                            this.setBlock(worldgenlevel, offsetPos, edgeBlockState);
                        }
                    }
                }
            }
        }
    }

    public boolean isSteep(WorldGenLevel worldgenlevel, BlockPos blockpos) {
        int baseElevation = blockpos.getY();
        if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.north()).getY() - baseElevation) <= 2) {
            if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.east()).getY() - baseElevation) <= 2) {
                if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.south()).getY() - baseElevation) <= 2) {
                    if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.west()).getY() - baseElevation) <= 2) {
                        if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.north(2)).getY() - baseElevation) <= 2) {
                            if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.east(2)).getY() - baseElevation) <= 2) {
                                if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.south(2)).getY() - baseElevation) <= 2) {
                                    if (Math.abs(worldgenlevel.getHeightmapPos(Heightmap.Types.WORLD_SURFACE_WG, blockpos.west(2)).getY() - baseElevation) <= 2) {
                                        return false;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return true;
    }

    public BlockState getPathBlock(BlockState blockState) {
        BlockState blockState1 = Blocks.AIR.defaultBlockState();
        if (blockState.is(BlockTags.DIRT) || blockState.is(Blocks.MOSS_BLOCK) || blockState.is(BlockTags.SAND) || blockState.is(BlockTags.TERRACOTTA)) {
            blockState1 = Blocks.PACKED_MUD.defaultBlockState();
        } else if (blockState.is(Blocks.SNOW_BLOCK) || blockState.is(Blocks.POWDER_SNOW)) {
            blockState1 = Blocks.COBBLED_DEEPSLATE.defaultBlockState();
        }
        return blockState1;
    }

    public BlockState getEdgePathBlock(BlockState blockState) {
        BlockState blockState1 = Blocks.AIR.defaultBlockState();
        if (blockState.is(Blocks.PACKED_MUD)) {
            blockState1 = Blocks.COARSE_DIRT.defaultBlockState();
        } else if (blockState.is(Blocks.COBBLED_DEEPSLATE)) {
            blockState1 = Blocks.DEEPSLATE.defaultBlockState();
        }
        return blockState1;
    }
}
