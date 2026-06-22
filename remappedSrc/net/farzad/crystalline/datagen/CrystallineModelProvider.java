package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.farzad.crystalline.common.init.CrystallineBlocks;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;


public class CrystallineModelProvider extends FabricModelProvider {
    public CrystallineModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators blockStateModelGenerator) {
        BlockModelGenerators.BlockFamilyProvider crystalineModelLootTable = blockStateModelGenerator.family(CrystallineBlocks.AMETHYST_BRICKS);

        crystalineModelLootTable.stairs(CrystallineBlocks.AMETHYST_BRICK_STAIRS);
        crystalineModelLootTable.slab(CrystallineBlocks.AMETHYST_BRICK_SLAB);
        crystalineModelLootTable.wall(CrystallineBlocks.AMETHYST_BRICK_WALL);

    }


    @Override
    public void generateItemModels(ItemModelGenerators itemModelGenerator) {}
}