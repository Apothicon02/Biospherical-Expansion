package com.Apothic0n.BiosphericalExpansion.api.biome.features;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public abstract class BioxFeatureRegistry {
    public static final DeferredRegister<Feature<?>> FEATURES = DeferredRegister.create(ForgeRegistries.FEATURES, BiosphericalExpansion.MODID);

    public static void register(IEventBus eventBus) {
        FEATURES.register(eventBus);
    }
}
