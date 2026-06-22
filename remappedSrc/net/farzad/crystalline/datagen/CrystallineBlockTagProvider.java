package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farzad.crystalline.common.init.CrystallineBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import java.util.concurrent.CompletableFuture;

public class CrystallineBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public CrystallineBlockTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {
        valueLookupBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(CrystallineBlocks.AMETHYST_BRICKS)
                .add(CrystallineBlocks.AMETHYST_PILLAR)
                .add(CrystallineBlocks.AMETHYST_LAMP)
                .add(CrystallineBlocks.AMETHYST_BRICK_STAIRS)
                .add(CrystallineBlocks.AMETHYST_BRICK_SLAB);

        valueLookupBuilder(BlockTags.STAIRS).add(CrystallineBlocks.AMETHYST_BRICK_STAIRS);
        valueLookupBuilder(BlockTags.SLABS).add(CrystallineBlocks.AMETHYST_BRICK_SLAB);
        valueLookupBuilder(BlockTags.WALLS).add(CrystallineBlocks.AMETHYST_BRICK_WALL);
    }
}
