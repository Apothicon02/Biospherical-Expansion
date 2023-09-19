package com.Apothic0n.BiosphericalExpansion.core.events;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.Minecraft;
import net.minecraft.util.FastColor;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.synth.PerlinSimplexNoise;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiosphericalExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModEvents {
    private static final PerlinSimplexNoise SATURATION_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(2345L)), ImmutableList.of(0));
    private static final PerlinSimplexNoise BRIGHTNESS_NOISE = new PerlinSimplexNoise(new WorldgenRandom(new LegacyRandomSource(5432L)), ImmutableList.of(0));
    @SubscribeEvent
    public static void onBlockColors(RegisterColorHandlersEvent.Block event) {
        event.register((blockState, blockAndTintGetter, blockPos, tint) -> {
            int x = blockPos.getX();
            int z = blockPos.getZ();
            int color = -328966;
            double saturate = -Mth.clamp((SATURATION_NOISE.getValue(x * 0.05, z * 0.01, false) + 0.33) * 0.33, -0.33, 0.33);
            double temperature = (Minecraft.getInstance().level.getBiome(blockPos).get().getBaseTemperature() - 1) * 0.1;
            float red = (float) ((Mth.clamp(FastColor.ABGR32.red(color), 1, 255) / 255) + temperature);
            float green = (float) Mth.clamp(FastColor.ABGR32.green(color), 1, 255) / 255;
            float blue = (float) ((Mth.clamp(FastColor.ABGR32.blue(color), 1, 255) / 255) - temperature);
            float gray = (red + green + blue) / (3);
            return FastColor.ABGR32.color(FastColor.ABGR32.alpha(color),
                    (int) (Mth.clamp(red + (gray - red) * saturate, 0, 1) * 255),
                    (int) (Mth.clamp(green + (gray - green) * saturate, 0, 1) * 255),
                    (int) (Mth.clamp(blue + (gray - blue) * saturate, 0, 1) * 255));
        }, Blocks.STONE, Blocks.STONE_STAIRS, Blocks.STONE_SLAB, Blocks.COAL_ORE, Blocks.IRON_ORE, Blocks.LAPIS_ORE, Blocks.REDSTONE_ORE, Blocks.GOLD_ORE, Blocks.DIAMOND_ORE, Blocks.DEEPSLATE, Blocks.DRIPSTONE_BLOCK, Blocks.POINTED_DRIPSTONE);
    }
}