package com.Apothic0n.BiosphericalExpansion;

import com.Apothic0n.BiosphericalExpansion.api.biome.features.BioxFeatureRegistry;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.decorators.BioxTreeDecoratorType;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.foliage_placers.BioxFoliagePlacerType;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.trunk_placers.BioxTrunkPlacerType;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxItems;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BiosphericalExpansion.MODID)
public class BiosphericalExpansion {
    public static final String MODID = "biox";

    public BiosphericalExpansion() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        BioxBlocks.BLOCKS.register(eventBus);
        BioxItems.ITEMS.register(eventBus);
        BioxFeatureRegistry.register(eventBus);
        BioxTrunkPlacerType.register(eventBus);
        BioxFoliagePlacerType.register(eventBus);
        BioxTreeDecoratorType.register(eventBus);
    }

    private void clientSetup(final FMLClientSetupEvent event) {BioxBlocks.fixBlockRenderLayers();}
}