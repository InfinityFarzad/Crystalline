package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farzad.crystalline.common.init.CrystallineDamageTypes;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.registry.*;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.damagesource.DamageType;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public class CrystallineDamageTypeTagProvider extends FabricTagProvider<DamageType> {


    public CrystallineDamageTypeTagProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, Registries.DAMAGE_TYPE, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider wrapperLookup) {

        getOrCreateRawBuilder(DamageTypeTags.IS_PROJECTILE).addOptionalElement(CrystallineDamageTypes.AMETHYST_SHARD.identifier()).addOptionalElement(CrystallineDamageTypes.STRONG_AMETHYST_SHARD.identifier());
        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .addOptionalElement(CrystallineDamageTypes.STRONG_AMETHYST_SHARD.identifier());
        getOrCreateRawBuilder(DamageTypeTags.BYPASSES_COOLDOWN)
                .addOptionalElement(CrystallineDamageTypes.AMETHYST_SHARD.identifier());

    }
}
