package net.farzad.crystalline.common.init;

import net.farzad.crystalline.common.Crystalline;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.damagesource.DamageType;

public class CrystallineDamageTypes {

    public static final ResourceKey<DamageType> AMETHYST_SHARD = ResourceKey.create(Registries.DAMAGE_TYPE, Crystalline.id("amethyst_shard"));
    public static final ResourceKey<DamageType> STRONG_AMETHYST_SHARD = ResourceKey.create(Registries.DAMAGE_TYPE, Crystalline.id("strong_amethyst_shard"));

}
