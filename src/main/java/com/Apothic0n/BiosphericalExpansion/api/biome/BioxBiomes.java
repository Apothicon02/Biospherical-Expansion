package com.Apothic0n.BiosphericalExpansion.api.biome;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.biome.Biome;

public class BioxBiomes {

    public static final ResourceKey<Biome> AUTUMNAL_DARK_FOREST = register("autumnal_dark_forest");
    public static final ResourceKey<Biome> ERODED_MESA = register("eroded_mesa");
    public static final ResourceKey<Biome> FLOWER_PLAINS = register("flower_plains");
    public static final ResourceKey<Biome> MESA = register("mesa");
    public static final ResourceKey<Biome> MINERAL_CAVES = register("mineral_caves");
    public static final ResourceKey<Biome> MOLTEN_CAVES = register("molten_caves");
    public static final ResourceKey<Biome> SANDSTONY_SHORE = register("sandstony_shore");
    public static final ResourceKey<Biome> WOODED_MESA = register("wooded_mesa");

    private static ResourceKey<Biome> register(String name)
    {
        return ResourceKey.create(Registries.BIOME, new ResourceLocation(BiosphericalExpansion.MODID, name));
    }
}