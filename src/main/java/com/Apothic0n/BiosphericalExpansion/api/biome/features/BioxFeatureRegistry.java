package com.Apothic0n.BiosphericalExpansion.api.biome.features;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.configurations.OreSpikeConfiguration;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.types.*;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public abstract class BioxFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BiosphericalExpansion.MODID);

    public static final RegistryObject<Feature<SimpleBlockConfiguration>> BASIC_3X2X3_CUBE_FEATURE = FEATURES.register("basic_3x2x3_cube", () ->
            new Basic3x2x3CubeFeature(SimpleBlockConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> UNLIMITED_ICE_SPIKE_FEATURE = FEATURES.register("unlimited_ice_spike", () ->
            new UnlimitedIceSpikeFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<OreSpikeConfiguration>> ORE_SPIKE_FEATURE = FEATURES.register("ore_spike", () ->
            new OreSpikeFeature(OreSpikeConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> PATH_FEATURE = FEATURES.register("path", () ->
            new PathFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> NOODLE_CAVE_FEATURE = FEATURES.register("noodle_cave", () ->
            new NoodleCaveFeature(NoneFeatureConfiguration.CODEC));
    public static final RegistryObject<Feature<NoneFeatureConfiguration>> NOODLE_RIVER_FEATURE = FEATURES.register("noodle_river", () ->
            new NoodleRiverFeature(NoneFeatureConfiguration.CODEC));

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
