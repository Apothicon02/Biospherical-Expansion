package com.Apothic0n.BiosphericalExpansion.api.biome.features.trunk_placers;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.trunkplacers.TrunkPlacerType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class BioxTrunkPlacerType {
    public static final DeferredRegister<TrunkPlacerType<?>> TRUNK_PLACER_TYPES = DeferredRegister.create(Registries.TRUNK_PLACER_TYPE, BiosphericalExpansion.MODID);
    public static void register(IEventBus eventBus) {
        TRUNK_PLACER_TYPES.register(eventBus);
    }
}