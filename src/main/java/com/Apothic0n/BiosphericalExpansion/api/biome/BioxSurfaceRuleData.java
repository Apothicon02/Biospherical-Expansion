package com.Apothic0n.BiosphericalExpansion.api.biome;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.synth.NormalNoise;

public class BioxSurfaceRuleData {
    private static final SurfaceRules.RuleSource COARSE_DIRT = makeStateRule(Blocks.COARSE_DIRT);
    private static final SurfaceRules.RuleSource GRASS_BLOCK = makeStateRule(Blocks.GRASS_BLOCK);

    public static final ResourceKey<NormalNoise.NoiseParameters> SURFACE_VARIATION = ResourceKey.create(Registries.NOISE, new ResourceLocation("eco","surface_variation"));

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource dirtyGrass = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(61), 0),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE_VARIATION, 0.069D, 0.18D), COARSE_DIRT)
                            )
                        ),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, GRASS_BLOCK));

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FOREST), dirtyGrass));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
