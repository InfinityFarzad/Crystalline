package net.farzad.crystalline.common.init;

import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.entity.AmethystShardProjectileEntity;
import net.farzad.crystalline.common.entity.CrystalCoreEntity;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class CrystallineEntities {

    private static final ResourceKey<EntityType<?>> AMETHYST_SHARD_PROJECTILE_ENTITY_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, Crystalline.id("amethyst_shard_projectile_entity"));

    private static final ResourceKey<EntityType<?>> CRYSTAL_CORE_ENTITY_KEY =
            ResourceKey.create(Registries.ENTITY_TYPE, Crystalline.id("crystal_core_entity"));


    public static final EntityType<AmethystShardProjectileEntity> AMETHYST_SHARD_PROJECTILE_ENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Crystalline.id("amethyst_shard_projectile_entity"),
            EntityType.Builder.<AmethystShardProjectileEntity>of(AmethystShardProjectileEntity::new, MobCategory.MISC)
                    .sized(0.5f, 0.5f).build(AMETHYST_SHARD_PROJECTILE_ENTITY_KEY));

    public static final EntityType<CrystalCoreEntity> CRYSTAL_CORE_ENTITY = Registry.register(BuiltInRegistries.ENTITY_TYPE,
            Crystalline.id("crystal_core_entity"),
            EntityType.Builder.<CrystalCoreEntity>of(CrystalCoreEntity::new, MobCategory.MISC).noLootTable().fireImmune().clientTrackingRange(10).updateInterval(Integer.MAX_VALUE)
                    .sized(2f, 2f).noSummon().build(CRYSTAL_CORE_ENTITY_KEY));

    public static void init() {}
}