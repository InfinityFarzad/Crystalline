package net.farzad.crystalline.common.util;

import net.farzad.crystalline.common.Crystalline;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModDamageTypes {

    public static final RegistryKey<DamageType> AMETHYST_SHARD = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Crystalline.MOD_ID, "amethyst_shard"));

}
