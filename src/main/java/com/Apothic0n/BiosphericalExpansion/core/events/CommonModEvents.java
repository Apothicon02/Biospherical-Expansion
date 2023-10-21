package com.Apothic0n.BiosphericalExpansion.core.events;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks;
import com.mojang.serialization.JsonOps;
import commoble.databuddy.datagen.BlockStateFile;
import commoble.databuddy.datagen.SimpleModel;
import net.minecraft.Util;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.BlockModelRotation;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.*;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.Apothic0n.BiosphericalExpansion.core.objects.BioxBlocks.*;

@Mod.EventBusSubscriber(modid = BiosphericalExpansion.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        // models
        SimpleModel.addDataProvider(event, BiosphericalExpansion.MODID, JsonOps.INSTANCE, Util.make(new HashMap<>(), map ->
        {
            for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
                Block baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                String name = baseBlockBlock.toString();
                String finalName = name.substring(16, name.length() - 1);
                ResourceLocation baseBlock = new ResourceLocation("minecraft", "block/" + finalName);
                map = makeWallModels(map, baseBlockBlock, baseBlock, new ResourceLocation("minecraft", "solid"));
                map = makeStairsModels(map, baseBlockBlock, baseBlock);
                map = makeSlabModels(map, baseBlockBlock, baseBlock);
            }
            for (int i = 0; i < blocksWithWalls.size(); i++) {
                Block baseBlockBlock = blocksWithWalls.get(i);
                String name = baseBlockBlock.toString();
                String finalName = name.substring(16, name.length() - 1);
                if (finalName.contains("wood")) {
                    finalName = finalName.substring(0, finalName.length() - 4) + "log";
                }
                ResourceLocation baseBlock = new ResourceLocation("minecraft", "block/" + finalName);
                map = makeWallModels(map, baseBlockBlock, baseBlock, new ResourceLocation("minecraft", "solid"));
            }
            for (int i = 0; i < blocksWithFragileWalls.size(); i++) {
                Block baseBlockBlock = blocksWithFragileWalls.get(i);
                String name = baseBlockBlock.toString();
                String finalName = name.substring(16, name.length() - 1);
                ResourceLocation baseBlock = new ResourceLocation("minecraft", "block/" + finalName);
                map = makeWallModels(map, baseBlockBlock, baseBlock, new ResourceLocation("minecraft", "cutout"));
            }
        }));
        // blockstates
        BlockStateFile.addDataProvider(event, BiosphericalExpansion.MODID, JsonOps.INSTANCE, Util.make(new HashMap<>(), map -> {
            for (int i = 0; i < blocksWithStairsSlabsAndWalls.size(); i++) {
                Block baseBlockBlock = blocksWithStairsSlabsAndWalls.get(i);
                map = makeWallBlockstates(map, baseBlockBlock);
                map = makeStairsBlockstates(map, baseBlockBlock);
                map = makeSlabBlockstates(map, baseBlockBlock);
            }
            for (int i = 0; i < blocksWithWalls.size(); i++) {
                Block baseBlockBlock = blocksWithWalls.get(i);
                map = makeWallBlockstates(map, baseBlockBlock);
            }
            for (int i = 0; i < blocksWithFragileWalls.size(); i++) {
                Block baseBlockBlock = blocksWithFragileWalls.get(i);
                map = makeWallBlockstates(map, baseBlockBlock);
            }
        }));
    }

    private static HashMap makeWallModels(HashMap map, Block baseBlockBlock, ResourceLocation baseBlock, ResourceLocation renderType) {
        ResourceLocation tempWallBlock = new ResourceLocation("block/failure");
        ResourceLocation tempWallBlockSide = new ResourceLocation("block/failure_side");
        ResourceLocation tempWallBlockSideTall = new ResourceLocation("block/failure_side_tall");
        ResourceLocation tempWallBlockItem = new ResourceLocation("block/failure_block_item");
        for (int o = 0; o < BioxBlocks.wallBlocks.size(); o++) {
            Map<Block, RegistryObject<Block>> wallBlockMap = BioxBlocks.wallBlocks.get(o);
            if (wallBlockMap.containsKey(baseBlockBlock)) {
                tempWallBlock = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_post");
                tempWallBlockSide = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_side");
                tempWallBlockSideTall = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_side_tall");
                tempWallBlockItem = new ResourceLocation(BiosphericalExpansion.MODID, "item/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(5));
            }
        }
        ResourceLocation wallBlock = tempWallBlock;
        ResourceLocation wallBlockSide = tempWallBlockSide;
        ResourceLocation wallBlockSideTall = tempWallBlockSideTall;
        ResourceLocation wallBlockItem = tempWallBlockItem;
        map.put(wallBlock,
                SimpleModel.create(new ResourceLocation("block/template_wall_post"), renderType)
                        .addTexture("wall", baseBlock));
        map.put(wallBlockSide,
                SimpleModel.create(new ResourceLocation("block/template_wall_side"), renderType)
                        .addTexture("wall", baseBlock));
        map.put(wallBlockSideTall,
                SimpleModel.create(new ResourceLocation("block/template_wall_side_tall"), renderType)
                        .addTexture("wall", baseBlock));
        map.put(wallBlockItem,
                SimpleModel.create(new ResourceLocation("block/wall_inventory"), renderType)
                        .addTexture("wall", baseBlock));
        return map;
    }

    private static HashMap makeStairsModels(HashMap map, Block baseBlockBlock, ResourceLocation baseBlock) {
        ResourceLocation tempStairsBlock = new ResourceLocation("block/failure");
        ResourceLocation tempStairsBlockInner = new ResourceLocation("block/failure_inner");
        ResourceLocation tempStairsBlockOuter = new ResourceLocation("block/failure_outer");
        ResourceLocation tempStairsBlockItem = new ResourceLocation("block/failure_block_item");
        for (int o = 0; o < BioxBlocks.stairBlocks.size(); o++) {
            Map<Block, RegistryObject<Block>> stairBlockMap = BioxBlocks.stairBlocks.get(o);
            if (stairBlockMap.containsKey(baseBlockBlock)) {
                tempStairsBlock = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(5));
                tempStairsBlockInner = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_inner");
                tempStairsBlockOuter = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_outer");
                tempStairsBlockItem = new ResourceLocation(BiosphericalExpansion.MODID, "item/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(5));
            }
        }
        ResourceLocation stairsBlock = tempStairsBlock;
        ResourceLocation stairsBlockInner = tempStairsBlockInner;
        ResourceLocation stairsBlockOuter = tempStairsBlockOuter;
        ResourceLocation stairsBlockItem = tempStairsBlockItem;
        map.put(stairsBlock,
                SimpleModel.create(new ResourceLocation("block/stairs"))
                        .addTexture("bottom", baseBlock)
                        .addTexture("side", baseBlock)
                        .addTexture("top", baseBlock));
        map.put(stairsBlockInner,
                SimpleModel.create(new ResourceLocation("block/inner_stairs"))
                        .addTexture("bottom", baseBlock)
                        .addTexture("side", baseBlock)
                        .addTexture("top", baseBlock));
        map.put(stairsBlockOuter,
                SimpleModel.create(new ResourceLocation("block/outer_stairs"))
                        .addTexture("bottom", baseBlock)
                        .addTexture("side", baseBlock)
                        .addTexture("top", baseBlock));
        map.put(stairsBlockItem,
                SimpleModel.create(stairsBlock));
        return map;
    }

    private static HashMap makeSlabModels(HashMap map, Block baseBlockBlock, ResourceLocation baseBlock) {
        ResourceLocation tempSlabBlock = new ResourceLocation("block/failure");
        ResourceLocation tempSlabBlockTop = new ResourceLocation("block/failure_top");
        ResourceLocation tempSlabBlockItem = new ResourceLocation("block/failure_block_item");
        for (int o = 0; o < BioxBlocks.slabBlocks.size(); o++) {
            Map<Block, RegistryObject<Block>> slabBlockMap = BioxBlocks.slabBlocks.get(o);
            if (slabBlockMap.containsKey(baseBlockBlock)) {
                tempSlabBlock = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(5));
                tempSlabBlockTop = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_top");
                tempSlabBlockItem = new ResourceLocation(BiosphericalExpansion.MODID, "item/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(5));
            }
        }
        ResourceLocation slabBlock = tempSlabBlock;
        ResourceLocation slabBlockTop = tempSlabBlockTop;
        ResourceLocation slabBlockItem = tempSlabBlockItem;
        map.put(slabBlock,
                SimpleModel.create(new ResourceLocation("block/slab"))
                        .addTexture("bottom", baseBlock)
                        .addTexture("side", baseBlock)
                        .addTexture("top", baseBlock));
        map.put(slabBlockTop,
                SimpleModel.create(new ResourceLocation("block/slab_top"))
                        .addTexture("bottom", baseBlock)
                        .addTexture("side", baseBlock)
                        .addTexture("top", baseBlock));
        map.put(slabBlockItem,
                SimpleModel.create(slabBlock));
        return map;
    }

    private static HashMap makeWallBlockstates(HashMap map, Block baseBlockBlock) {
        ResourceLocation tempWallState = new ResourceLocation("failure");
        ResourceLocation tempWallBlock = new ResourceLocation("block/failure");
        ResourceLocation tempWallBlockSide = new ResourceLocation("block/failure_side");
        ResourceLocation tempWallBlockSideTall = new ResourceLocation("block/failure_side_tall");
        for (int o = 0; o < BioxBlocks.wallBlocks.size(); o++) {
            Map<Block, RegistryObject<Block>> wallBlockMap = BioxBlocks.wallBlocks.get(o);
            if (wallBlockMap.containsKey(baseBlockBlock)) {
                tempWallState = new ResourceLocation(BiosphericalExpansion.MODID, wallBlockMap.get(baseBlockBlock).getId().toString().substring(5));
                tempWallBlock = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_post");
                tempWallBlockSide = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_side");
                tempWallBlockSideTall = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + wallBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_side_tall");
            }
        }
        ResourceLocation wallState = tempWallState;
        ResourceLocation wallBlock = tempWallBlock;
        ResourceLocation wallBlockSide = tempWallBlockSide;
        ResourceLocation wallBlockSideTall = tempWallBlockSideTall;
        map.put(wallState,
                BlockStateFile.multipart(BlockStateFile.Multipart.builder()
                        .addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(WallBlock.UP, true),
                                BlockStateFile.Model.create(wallBlock)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.NORTH_WALL, WallSide.LOW),
                                BlockStateFile.Model.create(wallBlockSide)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.EAST_WALL, WallSide.LOW),
                                BlockStateFile.Model.create(wallBlockSide, BlockModelRotation.X0_Y90)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.SOUTH_WALL, WallSide.LOW),
                                BlockStateFile.Model.create(wallBlockSide, BlockModelRotation.X0_Y180)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.WEST_WALL, WallSide.LOW),
                                BlockStateFile.Model.create(wallBlockSide, BlockModelRotation.X0_Y270)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.NORTH_WALL, WallSide.TALL),
                                BlockStateFile.Model.create(wallBlockSideTall)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.EAST_WALL, WallSide.TALL),
                                BlockStateFile.Model.create(wallBlockSideTall, BlockModelRotation.X0_Y90)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.SOUTH_WALL, WallSide.TALL),
                                BlockStateFile.Model.create(wallBlockSideTall, BlockModelRotation.X0_Y180)
                        )).addWhenApply(BlockStateFile.WhenApply.when(
                                BlockStateFile.Case.create(BlockStateProperties.WEST_WALL, WallSide.TALL),
                                BlockStateFile.Model.create(wallBlockSideTall, BlockModelRotation.X0_Y270)
                        ))));
        return map;
    }

    private static HashMap makeStairsBlockstates(HashMap map, Block baseBlockBlock) {
        ResourceLocation tempStairState = new ResourceLocation("failure");
        ResourceLocation tempStairBlock = new ResourceLocation("block/failure");
        ResourceLocation tempStairBlockInner = new ResourceLocation("block/failure_inner");
        ResourceLocation tempStairBlockOuter = new ResourceLocation("block/failure_outer");
        for (int o = 0; o < BioxBlocks.stairBlocks.size(); o++) {
            Map<Block, RegistryObject<Block>> stairBlockMap = BioxBlocks.stairBlocks.get(o);
            if (stairBlockMap.containsKey(baseBlockBlock)) {
                tempStairState = new ResourceLocation(BiosphericalExpansion.MODID, stairBlockMap.get(baseBlockBlock).getId().toString().substring(5));
                tempStairBlock = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(5));
                tempStairBlockInner = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_inner");
                tempStairBlockOuter = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + stairBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_outer");
            }
        }
        ResourceLocation stairState = tempStairState;
        ResourceLocation stairBlock = tempStairBlock;
        ResourceLocation stairBlockInner = tempStairBlockInner;
        ResourceLocation stairBlockOuter = tempStairBlockOuter;
        BlockStateFile.Variants variants = BlockStateFile.Variants.builder();
        for (Direction facing : StairBlock.FACING.getPossibleValues()) {
            for (Half half : StairBlock.HALF.getPossibleValues()) {
                for (StairsShape shape : StairBlock.SHAPE.getPossibleValues()) {
                    ResourceLocation model =
                            shape == StairsShape.INNER_LEFT || shape == StairsShape.INNER_RIGHT ? stairBlockInner
                                    : shape == StairsShape.OUTER_LEFT || shape == StairsShape.OUTER_RIGHT ? stairBlockOuter
                                    : stairBlock;
                    int x = half == Half.TOP ? 180 : 0;
                    int y = ((int) facing.toYRot() + 90
                            + (shape == StairsShape.INNER_LEFT || shape == StairsShape.OUTER_LEFT ? 270 : 0)
                            + (half == Half.TOP && shape != StairsShape.STRAIGHT ? 90 : 0))
                            % 360;
                    boolean uvlock = x != 0 || y != 0;
                    variants.addVariant(List.of(BlockStateFile.PropertyValue.create(StairBlock.FACING, facing), BlockStateFile.PropertyValue.create(StairBlock.HALF, half), BlockStateFile.PropertyValue.create(StairBlock.SHAPE, shape)),
                            BlockStateFile.Model.create(model, BlockModelRotation.by(x, y), uvlock, 1));
                }
            }
        }
        map.put(stairState, BlockStateFile.variants(variants));
        return map;
    }

    private static HashMap makeSlabBlockstates(HashMap map, Block baseBlockBlock) {
        String name = baseBlockBlock.toString();
        ResourceLocation baseBlock = new ResourceLocation("minecraft", "block/" + name.substring(16, name.length() - 1));
        ResourceLocation tempSlabState = new ResourceLocation("failure");
        ResourceLocation tempSlabBlock = new ResourceLocation("block/failure");
        ResourceLocation tempSlabBlockTop = new ResourceLocation("block/failure_top");
        for (int o = 0; o < BioxBlocks.slabBlocks.size(); o++) {
            Map<Block, RegistryObject<Block>> slabBlockMap = BioxBlocks.slabBlocks.get(o);
            if (slabBlockMap.containsKey(baseBlockBlock)) {
                tempSlabState = new ResourceLocation(BiosphericalExpansion.MODID, slabBlockMap.get(baseBlockBlock).getId().toString().substring(5));
                tempSlabBlock = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(5));
                tempSlabBlockTop = new ResourceLocation(BiosphericalExpansion.MODID, "block/" + slabBlockMap.get(baseBlockBlock).getId().toString().substring(5) + "_top");
            }
        }
        ResourceLocation slabState = tempSlabState;
        ResourceLocation slabBlock = tempSlabBlock;
        ResourceLocation slabBlockTop = tempSlabBlockTop;
        map.put(slabState,
                BlockStateFile.variants(BlockStateFile.Variants.builder()
                        .addVariant(
                                BlockStateFile.PropertyValue.create(SlabBlock.TYPE, SlabType.BOTTOM),
                                BlockStateFile.Model.create(slabBlock))
                        .addVariant(
                                BlockStateFile.PropertyValue.create(SlabBlock.TYPE, SlabType.DOUBLE),
                                BlockStateFile.Model.create(baseBlock))
                        .addVariant(
                                BlockStateFile.PropertyValue.create(SlabBlock.TYPE, SlabType.TOP),
                                BlockStateFile.Model.create(slabBlockTop))));
        return map;
    }
}
