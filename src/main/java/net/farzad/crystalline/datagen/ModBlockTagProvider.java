package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farzad.crystalline.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public ModBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {
        getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE)
                .add(ModBlocks.AMETHYST_BRICKS)
                .add(ModBlocks.AMETHYST_PILLAR)
                .add(ModBlocks.AMETHYST_LAMP)
                .add(ModBlocks.AMETHYST_BRICK_STAIRS)
                .add(ModBlocks.AMETHYST_BRICK_SLAB)
                .add(ModBlocks.AMETHYST_BRICK_PRESSURE_PLATE);

        getOrCreateTagBuilder(BlockTags.STAIRS).add(ModBlocks.AMETHYST_BRICK_STAIRS);
        getOrCreateTagBuilder(BlockTags.SLABS).add(ModBlocks.AMETHYST_BRICK_SLAB);
        getOrCreateTagBuilder(BlockTags.PRESSURE_PLATES).add(ModBlocks.AMETHYST_BRICK_PRESSURE_PLATE);
        getOrCreateTagBuilder(BlockTags.BUTTONS).add(ModBlocks.AMETHYST_BRICK_BUTTON);
        getOrCreateTagBuilder(BlockTags.WALLS).add(ModBlocks.AMETHYST_BRICK_WALL);
    }
}