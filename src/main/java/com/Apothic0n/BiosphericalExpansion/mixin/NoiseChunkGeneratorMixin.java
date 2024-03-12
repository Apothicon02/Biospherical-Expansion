package com.Apothic0n.BiosphericalExpansion.mixin;

import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Aquifer;
import net.minecraft.world.level.levelgen.NoiseBasedChunkGenerator;
import net.minecraft.world.level.levelgen.NoiseGeneratorSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(NoiseBasedChunkGenerator.class)
public class NoiseChunkGeneratorMixin {

    /**
     * @author Apothic0n
     * @reason Prevents lava aquifers from generating above Y-116 on the amplified preset and slightly raises the lava aquifer altitude otherwise.
     */
    @Overwrite
    private static Aquifer.FluidPicker createFluidPicker(NoiseGeneratorSettings p_249264_) {
        int y = -51;
        if (p_249264_.noiseSettings().minY() < -64) {
            y = -116;
        }
        Aquifer.FluidStatus aquifer$fluidstatus = new Aquifer.FluidStatus(y, Blocks.LAVA.defaultBlockState());
        int i = p_249264_.seaLevel();
        Aquifer.FluidStatus aquifer$fluidstatus1 = new Aquifer.FluidStatus(i, p_249264_.defaultFluid());
        int finalY = y;
        return (p_224274_, p_224275_, p_224276_) -> {
            return p_224275_ < Math.min(finalY, i) ? aquifer$fluidstatus : aquifer$fluidstatus1;
        };
    }
}
