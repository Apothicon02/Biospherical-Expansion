package com.Apothic0n.BiosphericalExpansion;

import com.Apothic0n.BiosphericalExpansion.api.biome.features.BioxFeatureRegistry;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.decorators.BioxTreeDecoratorType;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.foliage_placers.BioxFoliagePlacerType;
import com.Apothic0n.BiosphericalExpansion.api.biome.features.trunk_placers.BioxTrunkPlacerType;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxItems;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxParticleTypes;
import com.google.common.collect.ImmutableList;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BiosphericalExpansion.MODID)
public class BiosphericalExpansion {
    public static final String MODID = "biox";

    public BiosphericalExpansion() {
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::commonSetup);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::clientSetup);

        BioxParticleTypes.PARTICLE_TYPES.register(eventBus);
        BioxBlocks.BLOCKS.register(eventBus);
        BioxBlocks.generateStairsSlabsWalls();
        BioxItems.ITEMS.register(eventBus);
        BioxItems.generateStairsSlabsWalls();
        BioxFeatureRegistry.register(eventBus);
        BioxTrunkPlacerType.register(eventBus);
        BioxFoliagePlacerType.register(eventBus);
        BioxTreeDecoratorType.register(eventBus);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            addLight(Blocks.SUNFLOWER.getStateDefinition().getPossibleStates(), 2);
            addLight(Blocks.SPORE_BLOSSOM.getStateDefinition().getPossibleStates(), 4);
            addLight(Blocks.BLUE_ICE.getStateDefinition().getPossibleStates(), 7);
            addLight(Blocks.TORCHFLOWER.getStateDefinition().getPossibleStates(), 9);
            addLight(Blocks.TORCHFLOWER_CROP.getStateDefinition().getPossibleStates(), 9);
        });
    }

    private void addLight(ImmutableList<BlockState> blockStates, int light) {
        for (int i = 0; i < blockStates.size(); i++) {
            blockStates.get(i).lightEmission = light;
        }
    }

    private void clientSetup(final FMLClientSetupEvent event) {BioxBlocks.fixBlockRenderLayers();}
}