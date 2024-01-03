package com.Apothic0n.BiosphericalExpansion.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.util.random.WeightedRandomList;
import net.minecraft.world.entity.*;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Optional;

import static com.mojang.text2speech.Narrator.LOGGER;
import static net.minecraft.world.level.NaturalSpawner.isSpawnPositionOk;

@Mixin(value = NaturalSpawner.class, priority = 69420)
public abstract class NaturalSpawnerMixin {

    @Shadow
    private static BlockPos getTopNonCollidingPos(LevelReader p_47066_, EntityType<?> p_47067_, int p_47068_, int p_47069_) {
        return null;
    }

    /**
     * @author Apothicon
     * @reason Fixes a moderately important issue where creatures can spawn in the wrong biome if the correct biome is at a different elevation than the actual spawn point. Also allows mobs to spawn in the lithospherical world preset.
     */
    @Overwrite
    public static void spawnMobsForChunkGeneration(ServerLevelAccessor p_220451_, Holder<Biome> p_220452_, ChunkPos p_220453_, RandomSource p_220454_) {
        MobSpawnSettings mobspawnsettings = p_220452_.value().getMobSettings();
        WeightedRandomList<MobSpawnSettings.SpawnerData> weightedrandomlist = mobspawnsettings.getMobs(MobCategory.CREATURE);
        if (!weightedrandomlist.isEmpty()) {
            int i = p_220453_.getMinBlockX();
            int j = p_220453_.getMinBlockZ();

            while(p_220454_.nextFloat() < mobspawnsettings.getCreatureProbability()) {
                Optional<MobSpawnSettings.SpawnerData> optional = weightedrandomlist.getRandom(p_220454_);
                if (!optional.isEmpty()) {
                    MobSpawnSettings.SpawnerData mobspawnsettings$spawnerdata = optional.get();
                    int k = mobspawnsettings$spawnerdata.minCount + p_220454_.nextInt(1 + mobspawnsettings$spawnerdata.maxCount - mobspawnsettings$spawnerdata.minCount);
                    SpawnGroupData spawngroupdata = null;
                    int l = i + p_220454_.nextInt(16);
                    int i1 = j + p_220454_.nextInt(16);
                    int j1 = l;
                    int k1 = i1;

                    for(int l1 = 0; l1 < k; ++l1) {
                        boolean flag = false;

                        for(int i2 = 0; !flag && i2 < 4; ++i2) {
                            BlockPos blockpos = getTopNonCollidingPos(p_220451_, mobspawnsettings$spawnerdata.type, l, i1);
                            if (blockpos != null && p_220451_.getBiome(blockpos) == p_220452_ && mobspawnsettings$spawnerdata.type.canSummon() && isSpawnPositionOk(SpawnPlacements.getPlacementType(mobspawnsettings$spawnerdata.type), p_220451_, blockpos, mobspawnsettings$spawnerdata.type)) {
                                float f = mobspawnsettings$spawnerdata.type.getWidth();
                                double d0 = Mth.clamp((double)l, (double)i + (double)f, (double)i + 16.0D - (double)f);
                                double d1 = Mth.clamp((double)i1, (double)j + (double)f, (double)j + 16.0D - (double)f);
                                boolean skipChecks = false;
                                if (p_220451_.getMaxBuildHeight() != 63) {
                                    skipChecks = true;
                                }

                                if (skipChecks == false && (!p_220451_.noCollision(mobspawnsettings$spawnerdata.type.getAABB(d0, (double) blockpos.getY(), d1)) || !SpawnPlacements.checkSpawnRules(mobspawnsettings$spawnerdata.type, p_220451_, MobSpawnType.CHUNK_GENERATION, BlockPos.containing(d0, (double) blockpos.getY(), d1), p_220451_.getRandom()))) {
                                    continue;
                                }

                                Entity entity;
                                try {
                                    entity = mobspawnsettings$spawnerdata.type.create(p_220451_.getLevel());
                                } catch (Exception exception) {
                                    LOGGER.warn("Failed to create mob", (Throwable)exception);
                                    continue;
                                }

                                if (entity == null) {
                                    continue;
                                }

                                entity.moveTo(d0, (double)blockpos.getY(), d1, p_220454_.nextFloat() * 360.0F, 0.0F);
                                if (entity instanceof Mob) {
                                    Mob mob = (Mob)entity;
                                    if (skipChecks == true || net.minecraftforge.event.ForgeEventFactory.checkSpawnPosition(mob, p_220451_, MobSpawnType.CHUNK_GENERATION)) {
                                        spawngroupdata = mob.finalizeSpawn(p_220451_, p_220451_.getCurrentDifficultyAt(mob.blockPosition()), MobSpawnType.CHUNK_GENERATION, spawngroupdata, (CompoundTag)null);
                                        p_220451_.addFreshEntityWithPassengers(mob);
                                        flag = true;
                                    }
                                }
                            }

                            l += p_220454_.nextInt(5) - p_220454_.nextInt(5);

                            for(i1 += p_220454_.nextInt(5) - p_220454_.nextInt(5); l < i || l >= i + 16 || i1 < j || i1 >= j + 16; i1 = k1 + p_220454_.nextInt(5) - p_220454_.nextInt(5)) {
                                l = j1 + p_220454_.nextInt(5) - p_220454_.nextInt(5);
                            }
                        }
                    }
                }
            }

        }
    }
}