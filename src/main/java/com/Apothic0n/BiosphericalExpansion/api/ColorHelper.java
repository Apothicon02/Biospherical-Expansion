package com.Apothic0n.BiosphericalExpansion.api;

import com.google.common.collect.ImmutableList;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.spongepowered.asm.mixin.Unique;

public class ColorHelper {
    @Unique
    private static final PerlinSimplexNoise SATURATION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0));
    @Unique
    private static final PerlinSimplexNoise BRIGHTNESS_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(5432L)), ImmutableList.of(0));

    public static int tintFoliageOrGrass(BlockState blockState, int x, int y, int z, double temperature, double humidity, boolean isFoliage) {
        double saturate = -(Mth.clamp(SATURATION_NOISE.getValue(x * 0.05, z * 0.01, false) * 0.33, -0.33, 0.33)+0.5);
        double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.05, z * 0.01, false), -0.5, 0.5)+0.75;
        double darkForestFactor = getFactor(0.6, 4, -0.25, 0, temperature);
        String name = blockState.getBlock().asItem().getDefaultInstance().getHoverName().getString();
        if (isFoliage && name.contains("Dark")) {
            darkForestFactor = Math.max(darkForestFactor, getFactor(0.6, 4, 0, 0.33, temperature));
        }
        double snowFactor = getFactor(0.8, 2, -1, -0.8, temperature);
        float red = (float) ((0.66 - Mth.clamp(temperature, -0.25, 1)*0.02) - darkForestFactor + snowFactor);
        float green = (float) (1 - darkForestFactor + snowFactor);
        float blue = (float) (0.6 - darkForestFactor + snowFactor);
        float gray = (float) ((red+green+blue)/(3+brighten));
        red = (float) Mth.clamp(red + (gray - red) * saturate, 0.1, 1);
        green = (float) Mth.clamp(green + (gray - green) * saturate, 0.1, 1);
        blue = (float) Mth.clamp(blue + (gray - blue) * saturate, 0.1, 1);
        return FastColor.ABGR32.color(1, (int) (blue*255), (int) (green*255), (int) (red*255));
    }

    private static double getFactor(double shift, double size, double min, double max, double temperature) {
        if (temperature >= max) {
            shift = Math.min(shift, Math.max(0, shift - Mth.abs((float) ((max - temperature)*size))));
        } else if (temperature <= min) {
            shift = Math.min(shift, Math.max(0, shift - Mth.abs((float) ((min - temperature)*size))));
        }
        return shift;
    }
}
