package net.farzad.crystalline.common.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {


    public static final ItemGroup CRYSTALLINE_ITEMGROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Crystalline.MOD_ID, "crystalline_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.DIVIDER))
                    .displayName(Text.translatable("itemgroup.crystalline_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.DIVIDER);
                        entries.add(ModItems.CRUSHER);
                        entries.add(ModBlocks.AMETHYST_LAMP);
                        entries.add(ModBlocks.AMETHYST_PILLAR);
                        entries.add(ModBlocks.AMETHYST_BRICKS);
                        entries.add(ModBlocks.AMETHYST_BRICK_STAIRS);
                        entries.add(ModBlocks.AMETHYST_BRICK_SLAB);
                        entries.add(ModBlocks.AMETHYST_BRICK_PRESSURE_PLATE);
                        entries.add(ModBlocks.AMETHYST_BRICK_BUTTON);
                    }).build());


    public static void init() {}
}