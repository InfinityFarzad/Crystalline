package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farzad.crystalline.block.ModBlocks;
import net.farzad.crystalline.common.item.ModItems;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;

import java.util.concurrent.CompletableFuture;

public class ModItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public ModItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(ItemTags.STAIRS).add(ModBlocks.AMETHYST_BRICK_STAIRS.asItem());
        getOrCreateTagBuilder(ItemTags.SLABS).add(ModBlocks.AMETHYST_BRICK_SLAB.asItem());
        getOrCreateTagBuilder(ItemTags.BUTTONS).add(ModBlocks.AMETHYST_BRICK_BUTTON.asItem());
        getOrCreateTagBuilder(ItemTags.WALLS).add(ModBlocks.AMETHYST_BRICK_WALL.asItem());
        getOrCreateTagBuilder(ItemTags.SWORDS).add(ModItems.DIVIDER);
    }
}