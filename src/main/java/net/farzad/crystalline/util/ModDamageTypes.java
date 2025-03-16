package net.farzad.crystalline.util;

import net.farzad.crystalline.Crystalline;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class ModDamageTypes {

    public static final RegistryKey<DamageType> AMETHYST_SHARD = RegistryKey.of(RegistryKeys.DAMAGE_TYPE, Identifier.of(Crystalline.MOD_ID,"amethyst_shard"));

    public static DamageSource amethystShard(PersistentProjectileEntity projectile, @Nullable Entity attacker) {
        return create(AMETHYST_SHARD, projectile.getWorld(), attacker);
    }

    private static DamageSource create(RegistryKey<DamageType> key, World world, @Nullable Entity attacker) {
        return world.getRegistryManager()
                .get(RegistryKeys.DAMAGE_TYPE)
                .getEntry(key)
                .map((type) -> new DamageSource(type, null, attacker))
                .orElse(world.getDamageSources().genericKill()); // Fallback, should never reach this
    }

}
