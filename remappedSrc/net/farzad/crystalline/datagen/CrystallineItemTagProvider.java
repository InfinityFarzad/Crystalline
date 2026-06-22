package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farzad.crystalline.common.init.CrystallineBlocks;
import net.farzad.crystalline.common.init.CrystallineItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import org.jspecify.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class CrystallineItemTagProvider extends FabricTagProvider.ItemTagProvider {

    public CrystallineItemTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {

        valueLookupBuilder(ItemTags.STAIRS).add(CrystallineBlocks.AMETHYST_BRICK_STAIRS.asItem());
        valueLookupBuilder(ItemTags.SLABS).add(CrystallineBlocks.AMETHYST_BRICK_SLAB.asItem());
        valueLookupBuilder(ItemTags.WALLS).add(CrystallineBlocks.AMETHYST_BRICK_WALL.asItem());
        valueLookupBuilder(ItemTags.SWORDS).add(CrystallineItems.DIVIDER);
    }
}
