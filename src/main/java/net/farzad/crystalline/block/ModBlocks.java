package net.farzad.crystalline.block;

import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.block.custom.AmethystLampBlock;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.util.Identifier;

import java.util.function.Function;

import static net.minecraft.block.Blocks.createLightLevelFromLitBlockState;

public class ModBlocks {

    public static final BlockSetType AMETHYST_BLOCKTYPE = new BlockSetType(Identifier.of(Crystalline.MOD_ID, "amethyst").toString());


    public static final Block AMETHYST_BRICKS = register("amethyst_bricks", properties -> new Block(properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()),true);
    public static final Block AMETHYST_BRICK_STAIRS = register("amethyst_brick_stairs", properties -> new StairsBlock(ModBlocks.AMETHYST_BRICKS.getDefaultState(),properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()),true);
    public static final Block AMETHYST_BRICK_SLAB = register("amethyst_brick_slab", properties -> new SlabBlock(properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()),true);
    public static final Block AMETHYST_BRICK_WALL = register("amethyst_brick_wall", properties -> new WallBlock(properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()),true);
    public static final Block AMETHYST_BRICK_PRESSURE_PLATE = register("amethyst_brick_pressure_plate",properties -> new PressurePlateBlock(AMETHYST_BLOCKTYPE,properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()),true);
    public static final Block AMETHYST_BRICK_BUTTON = register("amethyst_brick_button",properties -> new ButtonBlock(AMETHYST_BLOCKTYPE,20,properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()),true);
    public static final Block AMETHYST_PILLAR = register("amethyst_pillar", properties -> new PillarBlock(properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool()),true);
    public static final Block AMETHYST_LAMP = register("amethyst_lamp", properties -> new AmethystLampBlock(properties.mapColor(MapColor.PURPLE).strength(1.5F).sounds(BlockSoundGroup.AMETHYST_BLOCK).requiresTool().luminance(createLightLevelFromLitBlockState(15))),true);


    private static Block register(String name, Function<AbstractBlock.Settings, Block> function, boolean registerItem) {
        Block toRegister = function.apply(AbstractBlock.Settings.create().registryKey(RegistryKey.of(RegistryKeys.BLOCK, Identifier.of(Crystalline.MOD_ID, name))));
        if (registerItem) {
            registerBlockItem(name, toRegister);
        }
        return Registry.register(Registries.BLOCK, Identifier.of(Crystalline.MOD_ID, name), toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(Registries.ITEM, Identifier.of(Crystalline.MOD_ID, name),
                new BlockItem(block, new Item.Settings().useBlockPrefixedTranslationKey()
                        .registryKey(RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Crystalline.MOD_ID, name)))));
    }

    public static void init() {}

}