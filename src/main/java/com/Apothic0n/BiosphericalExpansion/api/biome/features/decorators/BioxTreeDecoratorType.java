package com.Apothic0n.BiosphericalExpansion.api.biome.features.decorators;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public abstract class BioxTreeDecoratorType<P extends TreeDecorator> {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATOR_TYPE = DeferredRegister.create(Registries.TREE_DECORATOR_TYPE, BiosphericalExpansion.MODID);
    public static void register(IEventBus eventBus) {
        TREE_DECORATOR_TYPE.register(eventBus);
    }
}