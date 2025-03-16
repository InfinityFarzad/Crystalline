package net.farzad.crystalline.entity;

import net.farzad.crystalline.Crystalline;
import net.farzad.crystalline.entity.custom.AmethystShardProjectileEntity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEntities {


    public static final EntityType<AmethystShardProjectileEntity> AMETHYST_SHARD_PROJECTILE_ENTITY = Registry.register(Registries.ENTITY_TYPE,
            Identifier.of(Crystalline.MOD_ID, "amethyst_shard_projectile_entity"),
            EntityType.Builder.<AmethystShardProjectileEntity>create(AmethystShardProjectileEntity::new, SpawnGroup.MISC)
                    .dimensions(0.5f, 1.15f).build());


    public static void registerModEntities() {
        Crystalline.LOGGER.info("Registering Mod Entities for " + Crystalline.MOD_ID);
    }
}