package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.farzad.crystalline.block.ModBlocks;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModLootTableProvider extends FabricBlockLootTableProvider {
    public ModLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.AMETHYST_BRICK_BUTTON);
        addDrop(ModBlocks.AMETHYST_BRICK_PRESSURE_PLATE);
        addDrop(ModBlocks.AMETHYST_BRICK_SLAB);
        addDrop(ModBlocks.AMETHYST_BRICK_STAIRS);
        addDrop(ModBlocks.AMETHYST_LAMP);
        addDrop(ModBlocks.AMETHYST_PILLAR);
        addDrop(ModBlocks.AMETHYST_BRICKS);
    }

}