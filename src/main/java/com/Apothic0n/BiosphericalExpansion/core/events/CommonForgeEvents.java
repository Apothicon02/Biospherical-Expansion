package com.Apothic0n.BiosphericalExpansion.core.events;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BiomeTags;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.storage.ServerLevelData;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.LevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BiosphericalExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonForgeEvents {

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock event) {
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

    @SubscribeEvent
    public static void onCreateSpawnPosition(LevelEvent.CreateSpawnPosition event) {
        boolean cancel = false;
        if (event.getLevel() instanceof ServerLevel level && !level.dimensionType().hasCeiling()) {
            for (int x = 0; x < 128000; x = x+128) {
                for (int z = 0; z < 128000; z = z+128) {
                    BlockPos spawnPos = new BlockPos(x, 56, z);
                    if (!level.getBiome(spawnPos).is(BiomeTags.IS_OCEAN)) {
                        event.getSettings().setSpawn(spawnPos.atY(level.getHeight(Heightmap.Types.WORLD_SURFACE, x, z)), 0.0F);
                        cancel = true;
                        x = 128000;
                        z = 128000;
                        break;
                    }
                }
            }
        }
        event.setCanceled(cancel);
    }
}