package com.Apothic0n.BiosphericalExpansion.mixin;

import net.minecraft.core.Holder;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.*;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = NoiseBasedChunkGenerator.class, priority = 1)
public class NoiseBasedChunkGeneratorMixin {
    @Shadow @Final private Holder<NoiseGeneratorSettings> settings;

    /**
     * @author Apothicon
     * @reason Fixes a vital issue where the only 'creature' mobs that generate during world-gen are goats.
     */
    @Overwrite
    public void spawnOriginalMobs(WorldGenRegion p_64379_) {
        if (!this.settings.value().disableMobGeneration()) {
            ChunkPos chunkpos = p_64379_.getCenter();
            int minHeight = p_64379_.getMinBuildHeight();
            if (minHeight > 0) {
                minHeight = minHeight * -1;
            }
            Holder<Biome> holder = p_64379_.getBiome(chunkpos.getWorldPosition().atY((int) (Math.random()*(p_64379_.getMaxBuildHeight() - (1 + minHeight)))));
            WorldgenRandom worldgenrandom = new WorldgenRandom(new LegacyRandomSource(RandomSupport.generateUniqueSeed()));
            worldgenrandom.setDecorationSeed(p_64379_.getSeed(), chunkpos.getMinBlockX(), chunkpos.getMinBlockZ());
            NaturalSpawner.spawnMobsForChunkGeneration(p_64379_, holder, chunkpos, worldgenrandom);
        }
    }
}
