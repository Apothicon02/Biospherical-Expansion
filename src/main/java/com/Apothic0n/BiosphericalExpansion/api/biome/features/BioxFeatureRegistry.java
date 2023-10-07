package com.Apothic0n.BiosphericalExpansion.api.biome.features;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.types.Basic3x2x3CubeFeature;
import com.Apothic0n.EcosphericalExpansion.api.biome.features.types.Stemmed2x2x2CubeFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class BioxFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BiosphericalExpansion.MODID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> BASIC_3X2X3_CUBE_FEATURE = FEATURES.register("basic_3x2x3_cube", () ->
            new Basic3x2x3CubeFeature(SimpleBlockConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
