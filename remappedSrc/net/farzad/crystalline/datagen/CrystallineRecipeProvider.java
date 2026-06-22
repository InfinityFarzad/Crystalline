package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.farzad.crystalline.common.init.CrystallineBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Blocks;
import java.util.concurrent.CompletableFuture;

public class CrystallineRecipeProvider extends FabricRecipeProvider {
    public CrystallineRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup,exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.RegistryLookup<Item> itemLookup = registries.lookupOrThrow(Registries.ITEM);
                twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS,CrystallineBlocks.AMETHYST_BRICKS,Items.AMETHYST_BLOCK);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICKS, Blocks.AMETHYST_BLOCK);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_PILLAR, Blocks.AMETHYST_BLOCK);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_SLAB, CrystallineBlocks.AMETHYST_BRICKS,2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_STAIRS, CrystallineBlocks.AMETHYST_BRICKS);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_WALL, CrystallineBlocks.AMETHYST_BRICKS);

                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_SLAB, Blocks.AMETHYST_BLOCK,2);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_STAIRS, Blocks.AMETHYST_BLOCK);
                stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_WALL, Blocks.AMETHYST_BLOCK);
                shaped(RecipeCategory.REDSTONE, CrystallineBlocks.AMETHYST_LAMP,4)
                        .define('a', Blocks.AMETHYST_BLOCK).define('g', Items.GLOWSTONE_DUST).define('t', Items.TORCH)
                        .pattern("aaa").pattern("aga").pattern("ata")
                        .unlockedBy(getHasName(Items.AMETHYST_BLOCK),has(Items.AMETHYST_BLOCK)).save(output);

                wall(RecipeCategory.BUILDING_BLOCKS,CrystallineBlocks.AMETHYST_BRICK_WALL,CrystallineBlocks.AMETHYST_BRICKS);
                shaped(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_SLAB, 6).define('#', CrystallineBlocks.AMETHYST_BRICKS).pattern("###").unlockedBy(getHasName(Items.AMETHYST_BLOCK),has(Items.AMETHYST_BLOCK)).save(output);;
                shaped(RecipeCategory.BUILDING_BLOCKS, CrystallineBlocks.AMETHYST_BRICK_STAIRS, 4).define('#', CrystallineBlocks.AMETHYST_BRICKS).pattern("#  ").pattern("## ").pattern("###").unlockedBy(getHasName(Items.AMETHYST_BLOCK),has(Items.AMETHYST_BLOCK)).save(output);;
            }
        };
    }

    @Override
    public String getName() {
        return "CrystallineRecipeProvider";
    }
}
