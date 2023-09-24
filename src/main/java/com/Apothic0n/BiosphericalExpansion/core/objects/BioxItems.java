package com.Apothic0n.BiosphericalExpansion.core.objects;

import com.Apothic0n.BiosphericalExpansion.BiosphericalExpansion;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public final class BioxItems extends Items {
    private BioxItems() {}

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, BiosphericalExpansion.MODID);

    public static final RegistryObject<Item> GLOWING_AMETHYST = ITEMS.register("glowing_amethyst", () ->
            new BlockItem(BioxBlocks.GLOWING_AMETHYST.get(), new Item.Properties()));

    public static final RegistryObject<Item> AQUATIC_LICHEN = ITEMS.register("aquatic_lichen", () ->
            new PlaceOnWaterBlockItem(BioxBlocks.AQUATIC_LICHEN.get(), new Item.Properties()));
}