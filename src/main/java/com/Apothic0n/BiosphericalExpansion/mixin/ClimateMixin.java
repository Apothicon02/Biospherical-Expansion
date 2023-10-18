package com.Apothic0n.BiosphericalExpansion.mixin;

import net.minecraft.core.QuartPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.biome.Climate;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = Climate.Sampler.class, priority = 69420)
public class ClimateMixin {

    @Final
    @Shadow
    private DensityFunction temperature;
    @Final
    @Shadow
    private DensityFunction humidity;
    @Final
    @Shadow
    private DensityFunction continentalness;
    @Final
    @Shadow
    private DensityFunction erosion;
    @Final
    @Shadow
    private DensityFunction depth;
    @Final
    @Shadow
    private DensityFunction weirdness;

    /**
     * @author Apothicon
     * @reason Adjusts the way temperature / humidity works.
     */
    @Overwrite
    public Climate.TargetPoint sample(int p_186975_, int p_186976_, int p_186977_) {
        int i = QuartPos.toBlock(p_186975_);
        int j = QuartPos.toBlock(p_186976_);
        int k = QuartPos.toBlock(p_186977_);
        DensityFunction.SinglePointContext densityfunction$singlepointcontext = new DensityFunction.SinglePointContext(i, j, k);
        return Climate.target((float)(this.temperature.compute(densityfunction$singlepointcontext) + (Mth.clamp(k, -2048, 2048) * 0.00048)), (float)(this.humidity.compute(densityfunction$singlepointcontext) + (Mth.clamp(i, -2048, 2048) * 0.00048)), (float)this.continentalness.compute(densityfunction$singlepointcontext), (float)this.erosion.compute(densityfunction$singlepointcontext), (float)this.depth.compute(densityfunction$singlepointcontext), (float)this.weirdness.compute(densityfunction$singlepointcontext));
    }
}