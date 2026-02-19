package net.farzad.crystalline.common.entity;

import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.entity.custom.AmethystShardProjectileEntity;
import net.farzad.crystalline.common.entity.custom.CrystalCoreEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEntities {

    private static final RegistryKey<EntityType<?>> AMETHYST_SHARD_PROJECTILE_ENTITY_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Crystalline.MOD_ID, "amethyst_shard_projectile_entity"));

    private static final RegistryKey<EntityType<?>> CRYSTAL_CORE_ENTITY_KEY =
            RegistryKey.of(RegistryKeys.ENTITY_TYPE, Identifier.of(Crystalline.MOD_ID, "crystal_core_entity"));


    public static final EntityType<AmethystShardProjectileEntity> AMETHYST_SHARD_PROJECTILE_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Crystalline.MOD_ID, "amethyst_shard_projectile_entity"),
            EntityType.Builder.<AmethystShardProjectileEntity>create(AmethystShardProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 0.1f).build(AMETHYST_SHARD_PROJECTILE_ENTITY_KEY));

    public static final EntityType<CrystalCoreEntity> CRYSTAL_CORE_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Crystalline.MOD_ID, "crystal_core_entity"),
            EntityType.Builder.<CrystalCoreEntity>create(CrystalCoreEntity::new, SpawnGroup.MISC).dropsNothing().makeFireImmune().maxTrackingRange(10).trackingTickInterval(Integer.MAX_VALUE)
                    .dimensions(2f, 2f).disableSummon().build(CRYSTAL_CORE_ENTITY_KEY));

    public static void init() {}
}