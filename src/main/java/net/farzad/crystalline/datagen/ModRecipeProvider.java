package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.farzad.crystalline.items.ModItems;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider {
    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.THE_DIVIDER)
                .pattern(" VV")
                .pattern("SHV")
                .pattern("RS ")
                .input('R', Items.NETHERITE_SWORD)
                .input('S', Items.AMETHYST_BLOCK)
                .input('V', Items.AMETHYST_SHARD)
                .input('H',Items.HEART_OF_THE_SEA)
                .criterion(hasItem(ModItems.THE_DIVIDER), conditionsFromItem(ModItems.THE_DIVIDER))
                .offerTo(exporter);


    }
}