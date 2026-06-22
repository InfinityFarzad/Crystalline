package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.farzad.crystalline.common.init.CrystallineBlocks;
import net.minecraft.core.HolderLookup;

import java.util.concurrent.CompletableFuture;

public class CrystallineLootTableProvider extends FabricBlockLootTableProvider {
    public CrystallineLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        dropSelf(CrystallineBlocks.AMETHYST_BRICK_SLAB);
        dropSelf(CrystallineBlocks.AMETHYST_BRICK_STAIRS);
        dropSelf(CrystallineBlocks.AMETHYST_LAMP);
        dropSelf(CrystallineBlocks.AMETHYST_PILLAR);
        dropSelf(CrystallineBlocks.AMETHYST_BRICKS);
    }

}