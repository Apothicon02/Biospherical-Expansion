package com.Apothic0n.BiosphericalExpansion.core.events;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks;
import com.google.common.base.Stopwatch;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.network.chat.Component;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiosphericalExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {
    public static boolean isScanningBiome = false;
    public static BlockPos biomeScanBlockpos = null;
    public static CommandSourceStack cmdSource = null;
    public static ResourceOrTagArgument.Result<Biome> biome = null;
    public static int attempt = 0;

    @SubscribeEvent
    public static void serverTick(TickEvent.ServerTickEvent event) {
        if (isScanningBiome && biomeScanBlockpos != null && cmdSource != null && biome != null) {
            if (attempt == 0) {
                cmdSource.sendSystemMessage(Component.literal("Now searching within an unreasonable distance."));
            }
            attempt = attempt+1;
            Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
            Pair<BlockPos, Holder<Biome>> pair = cmdSource.getLevel().findClosestBiome3d(biome, biomeScanBlockpos, 6400, 32, 320);
            stopwatch.stop();
            if (pair != null) {
                LocateCommand.showLocateResult(cmdSource, biome, biomeScanBlockpos, pair, "commands.locate.biome.success", true, stopwatch.elapsed());
                isScanningBiome = false;
                biomeScanBlockpos = null;
                cmdSource = null;
                biome = null;
                attempt = 0;
            } else if (attempt >= 50) {
                cmdSource.sendSystemMessage(Component.literal("Reached attempt 50 without locating " + biome.asPrintable()));
                isScanningBiome = false;
                biomeScanBlockpos = null;
                cmdSource = null;
                biome = null;
                attempt = 0;
            } else {
                cmdSource.sendSystemMessage(Component.literal("Reached attempt " + attempt + " without locating " + biome.asPrintable()));
                biomeScanBlockpos = biomeScanBlockpos.east(204800);
            }
        }
    }

    @SubscribeEvent
    public static void itemUsed(PlayerInteractEvent.RightClickBlock event) {
        Level pLevel = event.getLevel();
        BlockPos pPos = event.getHitVec().getBlockPos();
        BlockState pBlock = pLevel.getBlockState(pPos);
        ItemStack pStack = event.getItemStack();
        Player player = event.getEntity();
        if (!pLevel.isClientSide) { //Runs stuff on the server every time a player right-clicks a block
            if (pStack.getItem() == Items.GLOW_INK_SAC && pBlock.getBlock() == Blocks.AMETHYST_CLUSTER) {
                pLevel.setBlock(pPos, BioxBlocks.GLOWING_AMETHYST.get().withPropertiesOf(pBlock), 2);
                float f = Mth.randomBetween(pLevel.random, 0.8F, 1.2F);
                pLevel.playSound((Player) null, pPos, SoundEvents.DOLPHIN_EAT, SoundSource.BLOCKS, 1.0F, f);
                player.swing(event.getHand(), true);
                if (!player.isCreative()) {
                    pStack.setCount(pStack.getCount() - 1);
                }
            }
        }
    }
}