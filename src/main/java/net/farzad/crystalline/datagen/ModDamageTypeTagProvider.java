package net.farzad.crystalline.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.farzad.crystalline.util.ModDamageTypes;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.*;
import net.minecraft.registry.tag.DamageTypeTags;

import java.util.concurrent.CompletableFuture;

public class ModDamageTypeTagProvider extends FabricTagProvider<DamageType> {

    public ModDamageTypeTagProvider(FabricDataOutput output) {
        super(output, RegistryKeys.DAMAGE_TYPE, CompletableFuture.supplyAsync(BuiltinRegistries::createWrapperLookup));
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup wrapperLookup) {

        getOrCreateTagBuilder(DamageTypeTags.BYPASSES_ARMOR)
                .addOptional(ModDamageTypes.AMETHYST_SHARD);
        getOrCreateTagBuilder(DamageTypeTags.IS_PROJECTILE)
                .addOptional(ModDamageTypes.AMETHYST_SHARD);




    }
}
