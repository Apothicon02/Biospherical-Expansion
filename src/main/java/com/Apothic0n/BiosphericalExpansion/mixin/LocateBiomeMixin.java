package com.Apothic0n.BiosphericalExpansion.mixin;

import com.Apothic0n.BiosphericalExpansion.core.events.CommonForgeEvents;
import com.google.common.base.Stopwatch;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.datafixers.util.Pair;
import net.minecraft.Util;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.arguments.ResourceOrTagArgument;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.commands.LocateCommand;
import net.minecraft.world.level.biome.Biome;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import static net.minecraft.server.commands.LocateCommand.showLocateResult;

@Mixin(value = LocateCommand.class, priority = 69420)
public class LocateBiomeMixin {
    @Shadow @Final private static DynamicCommandExceptionType ERROR_BIOME_NOT_FOUND;

    /**
     * @author Apothicon
     * @reason Gives the command a million block range.
     */
    @Overwrite
    private static int locateBiome(CommandSourceStack cmdSource, ResourceOrTagArgument.Result<Biome> biome) throws CommandSyntaxException {
        BlockPos blockpos = BlockPos.containing(cmdSource.getPosition());
        Stopwatch stopwatch = Stopwatch.createStarted(Util.TICKER);
        Pair<BlockPos, Holder<Biome>> pair = cmdSource.getLevel().findClosestBiome3d(biome, blockpos, 6400, 32, 64);
        stopwatch.stop();
        if (pair == null) {
            if (CommonForgeEvents.isScanningBiome == false) {
                CommonForgeEvents.isScanningBiome = true;
                CommonForgeEvents.biomeScanBlockpos = blockpos.east(10000);
                CommonForgeEvents.cmdSource = cmdSource;
                CommonForgeEvents.biome = biome;
            }
            throw ERROR_BIOME_NOT_FOUND.create(biome.asPrintable());
        } else {
            return showLocateResult(cmdSource, biome, blockpos, pair, "commands.locate.biome.success", true, stopwatch.elapsed());
        }
    }
}
