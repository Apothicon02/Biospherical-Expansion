package com.Apothic0n.BiosphericalExpansion.mixin;

import com.Apothic0n.BiosphericalExpansion.api.BioxDensityFunctions;
import com.Apothic0n.BiosphericalExpansion.api.ColorHelper;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.levelgen.DensityFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BiomeColors.class)
public abstract class BiomeColorsMixin {

    /**
     * @author Apothicon
     * @reason Custom foliage tinting
     */
    @Inject(method = "getAverageFoliageColor", at = @At("HEAD"), cancellable = true)
    private static void getAverageFoliageColor(BlockAndTintGetter blockAndTintGetter, BlockPos blockPos, CallbackInfoReturnable<Integer> ci) {
        if (BioxDensityFunctions.temperature != null) {
            int x = blockPos.getX();
            int y = blockPos.getY();
            int z = blockPos.getZ();
            ci.setReturnValue(ColorHelper.tintFoliageOrGrass(blockAndTintGetter.getBlockState(blockPos), x, y, z, BioxDensityFunctions.temperature.compute(new DensityFunction.SinglePointContext(x, y, z)),
                    BioxDensityFunctions.humidity.compute(new DensityFunction.SinglePointContext(x, y, z)), true));
        }
    }
}
