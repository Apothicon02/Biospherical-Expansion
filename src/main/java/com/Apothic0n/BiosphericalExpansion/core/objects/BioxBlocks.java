package com.Apothic0n.BiosphericalExpansion.core.objects;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.predicate.BlockStatePredicate;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static net.minecraft.world.level.block.Blocks.AMETHYST_CLUSTER;
import static net.minecraft.world.level.block.Blocks.MANGROVE_ROOTS;

public final class BioxBlocks {
    private BioxBlocks() {}

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, BiosphericalExpansion.MODID);

    public static final RegistryObject<Block> AMETHYST_VINES = BLOCKS.register("amethyst_vines", () ->
            new AmethystVinesBlock(BlockBehaviour.Properties.copy(AMETHYST_CLUSTER)
                    .randomTicks().strength(0.2F).noCollission().sound(SoundType.MEDIUM_AMETHYST_BUD)));

    public static final RegistryObject<Block> AMETHYST_VINES_PLANT = BLOCKS.register("amethyst_vines_plant", () ->
            new AmethystVinesBlock(BlockBehaviour.Properties.copy(AMETHYST_CLUSTER)
                    .noCollission().strength(0.2F).sound(SoundType.LARGE_AMETHYST_BUD)));

    public static final RegistryObject<Block> GLOWING_AMETHYST = BLOCKS.register("glowing_amethyst", () ->
            new GlowingAmethystBlock(3, 4, BlockBehaviour.Properties.copy(AMETHYST_CLUSTER)
                    .lightLevel(brightness -> {return 11;})));

    public static final RegistryObject<Block> AQUATIC_LICHEN = BLOCKS.register("aquatic_lichen", () ->
            new WaterlilyBlock(BlockBehaviour.Properties.of().mapColor(MapColor.GLOW_LICHEN)
                    .replaceable().noCollission().strength(0.2F).sound(SoundType.GLOW_LICHEN).lightLevel(brightness -> {return 7;}).ignitedByLava().pushReaction(PushReaction.DESTROY)));

    public static void fixBlockRenderLayers() {
        ItemBlockRenderTypes.setRenderLayer(AMETHYST_VINES.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AMETHYST_VINES_PLANT.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(GLOWING_AMETHYST.get(), RenderType.cutout());
        ItemBlockRenderTypes.setRenderLayer(AQUATIC_LICHEN.get(), RenderType.cutout());
    }
}
