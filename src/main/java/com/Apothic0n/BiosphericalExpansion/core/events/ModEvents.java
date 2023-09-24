package com.Apothic0n.BiosphericalExpansion.core.events;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxItems;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiosphericalExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    @SubscribeEvent
    public static void addItemsToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().equals(CreativeModeTabs.NATURAL_BLOCKS)) {
            event.accept(BioxItems.GLOWING_AMETHYST.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
            event.accept(BioxItems.AQUATIC_LICHEN.get(), CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
        }
    }

    private static final PerlinSimplexNoise SATURATION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0));
    private static final PerlinSimplexNoise BRIGHTNESS_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(5432L)), ImmutableList.of(0));
    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
            if (blockPos != null) {
                int x = blockPos.getX();
                int z = blockPos.getZ();
                int color = -328966;
                double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.077, z * 0.09, false) * 0.33, -0.03, 0.03) + 1;
                double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.05, z * 0.01, false) * 0.55, -0.5, 0.5) + 0.75;
                float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255) / 255;
                float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255) / 255;
                float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255) / 255;
                float gray = (float) ((red + green + blue) / (3 + brighten));
                return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                        (int) (Mth.clamp((red + (gray - red)) * saturate, 0, 1) * 255),
                        (int) (Mth.clamp((green + (gray - green)) * saturate, 0, 1) * 255),
                        (int) (Mth.clamp((blue + (gray - blue)) * saturate, 0, 1) * 255));
            } else {
                return -328966;
            }
        },
                Blocks.DIRT, Blocks.ROOTED_DIRT, Blocks.COARSE_DIRT, Blocks.DIRT_PATH, Blocks.PODZOL, Blocks.MYCELIUM,
                Blocks.NETHERRACK);

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    if (blockPos != null) {
                        int x = blockPos.getX();
                        int z = blockPos.getZ();
                        int color = -328966;
                        double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.077, z * 0.09, false) * 0.33, -0.03, 0.03) + 1;
                        double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.05, z * 0.01, false) * 0.11, -0.1, 0.1);
                        float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255) / 255;
                        float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255) / 255;
                        float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255) / 255;
                        float gray = (float) ((red + green + blue) / (3 + brighten));
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp((red + (gray - red)) * saturate, 0, 1) * 255),
                                (int) (Mth.clamp((green + (gray - green)) * saturate, 0, 1) * 255),
                                (int) (Mth.clamp((blue + (gray - blue)) * saturate, 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                Blocks.SAND, Blocks.SANDSTONE, Blocks.SANDSTONE_STAIRS, Blocks.SANDSTONE_SLAB, Blocks.SANDSTONE_WALL,
                Blocks.RED_SAND, Blocks.RED_SANDSTONE, Blocks.RED_SANDSTONE_STAIRS, Blocks.RED_SANDSTONE_SLAB, Blocks.RED_SANDSTONE_WALL,
                Blocks.SNOW_BLOCK, Blocks.SNOW, Blocks.POWDER_SNOW);

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    if (blockPos != null) {
                        int x = blockPos.getX();
                        int z = blockPos.getZ();
                        int color = -328966;
                        double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.33, z * 0.3, false) * 0.3, -0.6, 0.66) + 0.9;
                        double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.05, z * 0.01, false) * 0.3, -0.66, 0.66);
                        float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255) / 255;
                        float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255) / 255;
                        float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255) / 255;
                        float gray = (float) ((red + green + blue) / (3 + brighten));
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp((red + (gray - red)) * saturate, 0, 1) * 255),
                                (int) (Mth.clamp((green + (gray - green)) * saturate, 0, 1) * 255),
                                (int) (Mth.clamp((blue + (gray - blue)) * saturate, 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                Blocks.MUD, Blocks.PACKED_MUD, Blocks.MUD_BRICKS, Blocks.MUD_BRICK_STAIRS, Blocks.MUD_BRICK_SLAB, Blocks.MUD_BRICK_WALL,
                Blocks.MANGROVE_ROOTS);

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
            if (blockPos != null) {
                int x = blockPos.getX();
                int z = blockPos.getZ();
                int color = -328966;
                double temperature = (Minecraft.getInstance().level.getBiome(blockPos).get().getBaseTemperature() - 1) * 0.1;
                double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.1, z * 0.1, false) * 0.33, -0.03, 0.03)+1;
                float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255)/255;
                float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255)/255;
                float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255)/255;
                float gray = (red+green+blue)/(3);
                return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                        (int) (Mth.clamp(((red + (gray - red)) * saturate) + temperature, 0, 1) * 255),
                        (int) (Mth.clamp((green + (gray - green)) * saturate, 0, 1) * 255),
                        (int) (Mth.clamp(((blue + (gray - blue)) * saturate) - temperature, 0, 1) * 255));
            } else {
                return -328966;
            }
        },
                Blocks.GRAVEL, Blocks.CLAY,
                Blocks.COBBLESTONE, Blocks.COBBLESTONE_STAIRS, Blocks.COBBLESTONE_SLAB, Blocks.COBBLESTONE_WALL,
                Blocks.MOSSY_COBBLESTONE, Blocks.MOSSY_COBBLESTONE_STAIRS, Blocks.MOSSY_COBBLESTONE_SLAB, Blocks.MOSSY_COBBLESTONE_WALL,
                Blocks.STONE, Blocks.STONE_STAIRS, Blocks.STONE_SLAB,
                Blocks.STONE_BRICKS, Blocks.STONE_BRICK_STAIRS, Blocks.STONE_BRICK_SLAB, Blocks.STONE_BRICK_WALL, Blocks.CRACKED_STONE_BRICKS, Blocks.INFESTED_CRACKED_STONE_BRICKS,
                Blocks.MOSSY_STONE_BRICKS, Blocks.MOSSY_STONE_BRICK_STAIRS, Blocks.MOSSY_STONE_BRICK_SLAB, Blocks.MOSSY_STONE_BRICK_WALL, Blocks.INFESTED_MOSSY_STONE_BRICKS,
                Blocks.COAL_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE, Blocks.GOLD_ORE, Blocks.DIAMOND_ORE, Blocks.EMERALD_ORE,
                Blocks.COBBLED_DEEPSLATE, Blocks.COBBLED_DEEPSLATE_STAIRS, Blocks.COBBLED_DEEPSLATE_SLAB, Blocks.COBBLED_DEEPSLATE_WALL,
                Blocks.DEEPSLATE, Blocks.INFESTED_DEEPSLATE,
                Blocks.DEEPSLATE_BRICKS, Blocks.DEEPSLATE_BRICK_SLAB, Blocks.DEEPSLATE_BRICK_STAIRS, Blocks.DEEPSLATE_BRICK_WALL, Blocks.CRACKED_DEEPSLATE_BRICKS,
                Blocks.DEEPSLATE_TILES, Blocks.DEEPSLATE_TILE_STAIRS, Blocks.DEEPSLATE_TILE_SLAB, Blocks.DEEPSLATE_TILE_WALL, Blocks.CRACKED_DEEPSLATE_TILES,
                Blocks.POLISHED_DEEPSLATE, Blocks.POLISHED_DEEPSLATE_STAIRS, Blocks.POLISHED_DEEPSLATE_SLAB, Blocks.POLISHED_DEEPSLATE_WALL,
                Blocks.DRIPSTONE_BLOCK, Blocks.POINTED_DRIPSTONE,
                Blocks.DEEPSLATE_COAL_ORE, Blocks.DEEPSLATE_IRON_ORE, Blocks.DEEPSLATE_LAPIS_ORE, Blocks.DEEPSLATE_REDSTONE_ORE, Blocks.DEEPSLATE_GOLD_ORE, Blocks.DEEPSLATE_DIAMOND_ORE, Blocks.DEEPSLATE_EMERALD_ORE);

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    if (blockPos != null && Minecraft.getInstance().level != null) {
                        int x = blockPos.getX();
                        int z = blockPos.getZ();
                        int color = -328966;
                        int maxHeight = Minecraft.getInstance().level.getMaxBuildHeight();
                        int midHeight = maxHeight/2;
                        int minHeight = Minecraft.getInstance().level.getMinBuildHeight();
                        int offset = 0;
                        if (minHeight < 0) {
                            offset = -(minHeight);
                            maxHeight = maxHeight + offset;
                            midHeight = (maxHeight/2);
                        }
                        BlockPos offsetPos = blockPos.above(offset);
                        double temperature = 0;
                        if (offsetPos.getY() < midHeight) {
                            temperature = (offsetPos.getY()-midHeight) * 0.005;
                        } else {
                            temperature = Mth.clamp(offsetPos.getY()-midHeight * 0.005, -1, 0.05);
                        }
                        double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.1, z * 0.1, false) * 0.33, -0.03, 0.03)+1.1;
                        double brighten = Mth.clamp(BRIGHTNESS_NOISE.getValue(x * 0.025, z * 0.025, false) * 0.3, -0.33, 0.33);
                        float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255)/255;
                        float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255)/255;
                        float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255)/255;
                        float gray = (float) ((red + green + blue) / (3 + brighten));
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp(((red + (gray - red)) * saturate) + temperature, 0, 1) * 255),
                                (int) (Mth.clamp(((green + (gray - green)) * saturate) + temperature, 0, 1) * 255),
                                (int) (Mth.clamp(((blue + (gray - blue)) * saturate) - temperature, 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                Blocks.END_STONE, Blocks.END_STONE_BRICKS, Blocks.END_STONE_BRICK_STAIRS, Blocks.END_STONE_BRICK_SLAB, Blocks.END_STONE_BRICK_WALL);

        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
                    if (blockPos != null) {
                        int x = blockPos.getX();
                        int z = blockPos.getZ();
                        int color = -328966;
                        Biome biome = Minecraft.getInstance().level.getBiome(blockPos).get();
                        double humidity = -(biome.getModifiedClimateSettings().downfall()) * 0.25;
                        if (biome.equals(Biomes.MUSHROOM_FIELDS)) {
                            humidity = -2;
                        }
                        double saturate = Mth.clamp(SATURATION_NOISE.getValue(x * 0.1, z * 0.1, false) * 0.33, -0.03, 0.03)+1;
                        float red = (float) Mth.clamp(FastColor.ABGR32.red(color), 1, 255)/255;
                        float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255)/255;
                        float blue = (float) Mth.clamp(FastColor.ABGR32.blue(color), 1, 255)/255;
                        float gray = (red+green+blue)/(3);
                        return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                                (int) (Mth.clamp(((red + (gray - red)) * saturate) + humidity, 0, 1) * 255),
                                (int) (Mth.clamp((green + (gray - green)) * saturate, 0, 1) * 255),
                                (int) (Mth.clamp(((blue + (gray - blue)) * saturate) - humidity, 0, 1) * 255));
                    } else {
                        return -328966;
                    }
                },
                Blocks.MOSS_BLOCK, Blocks.MOSS_CARPET,
                Blocks.CAVE_VINES, Blocks.CAVE_VINES_PLANT,
                Blocks.AZALEA, Blocks.FLOWERING_AZALEA,
                Blocks.AZALEA_LEAVES, Blocks.FLOWERING_AZALEA_LEAVES,
                Blocks.GLOW_LICHEN, BioxBlocks.AQUATIC_LICHEN.get());
    }
}