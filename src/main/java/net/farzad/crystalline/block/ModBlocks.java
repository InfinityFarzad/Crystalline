package net.farzad.crystalline.block;


import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;

import net.farzad.crystalline.Crystalline;
import net.farzad.crystalline.block.custom.AmethystLampBlock;
import net.farzad.crystalline.particle.ModParticles;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class ModBlocks {
    public static final Block AMETHYST_PILLAR = registerBlock("amethyst_pillar",
            new PillarBlock(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()));
    public static final Block AMETHYST_BRICK = registerBlock("amethyst_brick",new Block(AbstractBlock.Settings.create().sounds(BlockSoundGroup.AMETHYST_BLOCK).mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()));
    //test
    public static final Block AMETHYST_BRICK_STAIRS = registerBlock("amethyst_brick_stairs",
            new StairsBlock(ModBlocks.AMETHYST_BRICK.getDefaultState(),
                    AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block AMETHYST_BRICK_SLAB = registerBlock("amethyst_brick_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block AMETHYST_BRICK_BUTTON = registerBlock("amethyst_brick_button",
            new ButtonBlock(BlockSetType.IRON, 2, AbstractBlock.Settings.create().strength(2f).requiresTool().noCollision()));
    public static final Block AMETHYST_BRICK_PRESSURE_PLATE = registerBlock("amethyst_brick_pressure_plate",
            new PressurePlateBlock(BlockSetType.IRON, AbstractBlock.Settings.create().strength(2f).requiresTool()));


    public static final Block AMETHYST_STAIRS = registerBlock("amethyst_stairs",
            new StairsBlock(Blocks.AMETHYST_BLOCK.getDefaultState(),
                    AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block AMETHYST_SLAB = registerBlock("amethyst_slab",
            new SlabBlock(AbstractBlock.Settings.create().strength(2f).requiresTool()));
    public static final Block AMETHYST_BUTTON = registerBlock("amethyst_button",
            new ButtonBlock(BlockSetType.IRON, 2, AbstractBlock.Settings.create().strength(2f).requiresTool().noCollision()));
    public static final Block AMETHYST_PRESSURE_PLATE = registerBlock("amethyst_pressure_plate",
            new PressurePlateBlock(BlockSetType.IRON, AbstractBlock.Settings.create().strength(2f).requiresTool()));

    public static final Block AMETHYST_LAMP = registerBlock("amethyst_lamp",new AmethystLampBlock(AbstractBlock.Settings.create()
            .strength(1f).requiresTool().luminance(createLightLevelFromLitBlockState(10))));

    private static Block registerBlock(String name, Block block) {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Identifier.of(Crystalline.MOD_ID, name), block);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Crystalline.MOD_ID, name),
                new BlockItem(block, new Item.Settings()));
    }

    public static void registerModBlocks() {
        Crystalline.LOGGER.info("Registering Blocks - " + Crystalline.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.BUILDING_BLOCKS).register(entries -> {
            entries.add(ModBlocks.AMETHYST_BRICK);
            entries.add(ModBlocks.AMETHYST_PILLAR);
            entries.add(ModBlocks.AMETHYST_BRICK_STAIRS);
            entries.add(ModBlocks.AMETHYST_BRICK_SLAB);
            entries.add(ModBlocks.AMETHYST_SLAB);
            entries.add(ModBlocks.AMETHYST_STAIRS);
        });
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.REDSTONE).register(entries -> {

            entries.add(ModBlocks.AMETHYST_BRICK_BUTTON);
            entries.add(ModBlocks.AMETHYST_BRICK_PRESSURE_PLATE);
            entries.add(ModBlocks.AMETHYST_LAMP);
        });
    }
}