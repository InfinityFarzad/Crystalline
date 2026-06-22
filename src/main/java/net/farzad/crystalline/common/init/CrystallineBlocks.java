package net.farzad.crystalline.common.init;

import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.block.AmethystLampBlock;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;

import java.util.function.Function;

import static net.minecraft.world.level.block.Blocks.litBlockEmission;

public class CrystallineBlocks {

    public static final Block AMETHYST_BRICKS = register("amethyst_bricks", properties -> new Block(properties.mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()), true);
    public static final Block AMETHYST_BRICK_STAIRS = register("amethyst_brick_stairs", properties -> new StairBlock(CrystallineBlocks.AMETHYST_BRICKS.defaultBlockState(), properties.mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()), true);
    public static final Block AMETHYST_BRICK_SLAB = register("amethyst_brick_slab", properties -> new SlabBlock(properties.mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()), true);
    public static final Block AMETHYST_BRICK_WALL = register("amethyst_brick_wall", properties -> new WallBlock(properties.mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()), true);
    public static final Block AMETHYST_PILLAR = register("amethyst_pillar", properties -> new RotatedPillarBlock(properties.mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops()), true);
    public static final Block AMETHYST_LAMP = register("amethyst_lamp", properties -> new AmethystLampBlock(properties.mapColor(MapColor.COLOR_PURPLE).strength(1.5F).sound(SoundType.AMETHYST).requiresCorrectToolForDrops().lightLevel(litBlockEmission(15))), true);


    private static <T extends Block> T register(String name, Function<BlockBehaviour.Properties, T> function, boolean registerItem) {
        T toRegister = function.apply(BlockBehaviour.Properties.of().setId(ResourceKey.create(Registries.BLOCK, Crystalline.id(name))));
        if (registerItem) {
            registerBlockItem(name, toRegister);
        }
        return Registry.register(BuiltInRegistries.BLOCK, Crystalline.id(name), toRegister);
    }

    private static void registerBlockItem(String name, Block block) {
        Registry.register(BuiltInRegistries.ITEM, Crystalline.id(name),
                new BlockItem(block, new Item.Properties().useBlockDescriptionPrefix()
                        .setId(ResourceKey.create(Registries.ITEM, Crystalline.id(name)))));
    }

    public static void init() {
    }

}