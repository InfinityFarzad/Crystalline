package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;

import net.farzad.crystalline.block.ModBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool crystalineModelLootTable = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.AMETHYST_BRICK);
        BlockStateModelGenerator.BlockTexturePool amethystModelLootTable = blockStateModelGenerator.registerCubeAllModelTexturePool(Blocks.AMETHYST_BLOCK);

        crystalineModelLootTable.stairs(ModBlocks.AMETHYST_BRICK_STAIRS);
        crystalineModelLootTable.slab(ModBlocks.AMETHYST_BRICK_SLAB);
        crystalineModelLootTable.button(ModBlocks.AMETHYST_BRICK_BUTTON);
        crystalineModelLootTable.pressurePlate(ModBlocks.AMETHYST_BRICK_PRESSURE_PLATE);




        amethystModelLootTable.stairs(ModBlocks.AMETHYST_STAIRS);
       amethystModelLootTable.slab(ModBlocks.AMETHYST_SLAB);
        amethystModelLootTable.button(ModBlocks.AMETHYST_BUTTON);
        amethystModelLootTable.pressurePlate(ModBlocks.AMETHYST_PRESSURE_PLATE);




   }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {

    }
}