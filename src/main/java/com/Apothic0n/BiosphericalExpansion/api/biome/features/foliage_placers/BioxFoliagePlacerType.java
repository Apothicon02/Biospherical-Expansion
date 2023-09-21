package com.Apothic0n.BiosphericalExpansion.api.biome.features.foliage_placers;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.Apothic0n.EcosphericalExpansion.api.biome.features.foliage_placers.GiantPineFoliagePlacer;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.foliageplacers.FoliagePlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BioxFoliagePlacerType {
    public static final DeferredRegister<FoliagePlacerType<?>> FOLIAGE_PLACER_TYPE = DeferredRegister.create(Registries.FOLIAGE_PLACER_TYPE, BiosphericalExpansion.MODID);

    public static final RegistryObject<FoliagePlacerType<SphericalCapFoliagePlacer>> SPHERICAL_CAP_FOLIAGE_PLACER = FOLIAGE_PLACER_TYPE.register("spherical_cap_foliage_placer", () ->
            new FoliagePlacerType<>(SphericalCapFoliagePlacer.CODEC));

    public static void register(IEventBus eventBus) {
        FOLIAGE_PLACER_TYPE.register(eventBus);
    }
}