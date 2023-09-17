package com.Apothic0n.BiosphericalExpansion.api.biome.features.foliage_placers;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BioxFoliagePlacerType {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, BiosphericalExpansion.MODID);
    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACER_TYPE.register(eventBus);
    }
}