package com.Apothic0n.BiosphericalExpansion.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Slice;

@Mixin(value = Blocks.class, priority = 1)
public class BlocksMixin {

    @ModifyExpressionValue(
        method = "<clinit>",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;of()Lnet/minecraft/world/level/block/state/BlockBehaviour$Properties;"),
            slice = @Slice(from = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;DANDELION:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC),
                    to = @At(value = "FIELD", target = "Lnet/minecraft/world/level/block/Blocks;TORCHFLOWER:Lnet/minecraft/world/level/block/Block;", opcode = Opcodes.PUTSTATIC)),
            require = 1
    )
    private static BlockBehaviour.Properties setTorchFlower(BlockBehaviour.Properties properties) {
        return properties.lightLevel(brightness -> 9);
    }
}