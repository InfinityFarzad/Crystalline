package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.client.datagen.v1.provider.FabricModelProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.farzad.crystalline.block.ModBlocks;
import net.minecraft.client.data.BlockStateModelGenerator;
import net.minecraft.client.data.ItemModelGenerator;


public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        BlockStateModelGenerator.BlockTexturePool crystalineModelLootTable = blockStateModelGenerator.registerCubeAllModelTexturePool(ModBlocks.AMETHYST_BRICKS);

        crystalineModelLootTable.stairs(ModBlocks.AMETHYST_BRICK_STAIRS);
        crystalineModelLootTable.slab(ModBlocks.AMETHYST_BRICK_SLAB);
        crystalineModelLootTable.button(ModBlocks.AMETHYST_BRICK_BUTTON);
        crystalineModelLootTable.wall(ModBlocks.AMETHYST_BRICK_WALL);
        crystalineModelLootTable.pressurePlate(ModBlocks.AMETHYST_BRICK_PRESSURE_PLATE);

    }


    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {}
}