package com.Apothic0n.BiosphericalExpansion.mixin;

import com.Apothic0n.BiosphericalExpansion.api.BioxDensityFunctions;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import org.spongepowered.asm.mixin.*;

@Mixin(value = Biome.class, priority = 69420)
public abstract class BiomeMixin {
    @Shadow
    @Final
    private BiomeSpecialEffects specialEffects;

    @Shadow protected abstract int getGrassColorFromTexture();

    @Unique
    private int biox$getGrassColorFromTexture() {
        if (BioxDensityFunctions.temperature != null) {
            return GrassColor.get(1D, 1D);
        } else {
            return getGrassColorFromTexture();
        }
    }

    /**
     * @author Apothicon
     * @reason Infinite color blending not dependant on biomes.
     */
    @Overwrite
    public int getGrassColor(double posX, double posZ) {
        int i = this.specialEffects.getGrassColorOverride().orElseGet(this::biox$getGrassColorFromTexture);
        return this.specialEffects.getGrassColorModifier().modifyColor(posX, posZ, i);
    }
}
