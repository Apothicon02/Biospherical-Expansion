package com.Apothic0n.BiosphericalExpansion.mixin;

import com.Apothic0n.BiosphericalExpansion.api.BioxDensityFunctions;
import com.Apothic0n.BiosphericalExpansion.api.ColorHelper;
import com.google.common.collect.ImmutableList;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.DensityFunction;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(targets = "net.minecraft.world.level.biome.BiomeSpecialEffects$GrassColorModifier$1")
public class BiomeSpecialEffectsGrassMixin {

    /**
     * @author Apothicon
     * @reason Adds grass discoloration.
     */
    @Inject(at = @At("RETURN"), method = "modifyColor", cancellable = true)
    private void modifyColor(double x, double z, int unusedColor, CallbackInfoReturnable<Integer> cir) {
        cir.setReturnValue(ColorHelper.tintFoliageOrGrass(Blocks.GRASS_BLOCK.defaultBlockState(), (int) x, 0, (int) z, BioxDensityFunctions.temperature.compute(new DensityFunction.SinglePointContext((int) x, 0, (int) z)),
                BioxDensityFunctions.humidity.compute(new DensityFunction.SinglePointContext((int) x, 0, (int) z)), false));
    }
}