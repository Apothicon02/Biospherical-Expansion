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
    private static final SurfaceRules.RuleSource MOSS_BLOCK = makeStateRule(Blocks.MOSS_BLOCK);
    public static final ResourceKey<NormalNoise.NoiseParameters> SURFACE_VARIATION = ResourceKey.create(Registries.NOISE, new ResourceLocation("eco","surface_variation"));
    public static final ResourceKey<NormalNoise.NoiseParameters> LESS_SURFACE_VARIATION = ResourceKey.create(Registries.NOISE, new ResourceLocation("biox","less_surface_variation"));

    public static SurfaceRules.RuleSource makeRules() {
        SurfaceRules.RuleSource coarseDirt = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(61), 0),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(SURFACE_VARIATION, 0.069D, 0.18D), COARSE_DIRT)
                        )));

        SurfaceRules.RuleSource lessCoarseDirt = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(61), 0),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(LESS_SURFACE_VARIATION, 0.0420D, 0.18D), COARSE_DIRT)
                        )));
        SurfaceRules.RuleSource lessMoss = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR,
                        SurfaceRules.ifTrue(SurfaceRules.yStartCheck(VerticalAnchor.absolute(61), 0),
                                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(LESS_SURFACE_VARIATION, 0.0370D, 0.23D), MOSS_BLOCK)
                        )));

        return SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.FOREST), coarseDirt),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.TAIGA), SurfaceRules.sequence(lessCoarseDirt, lessMoss)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.OLD_GROWTH_PINE_TAIGA), SurfaceRules.sequence(lessCoarseDirt, lessMoss)),
                SurfaceRules.ifTrue(SurfaceRules.isBiome(Biomes.OLD_GROWTH_SPRUCE_TAIGA), SurfaceRules.sequence(lessCoarseDirt, lessMoss)));
    }

    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }
}
