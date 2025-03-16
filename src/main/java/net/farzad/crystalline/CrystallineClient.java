package net.farzad.crystalline;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.farzad.crystalline.entity.ModEntities;
import net.farzad.crystalline.entity.client.AmethystShardProjectileEntityModel;
import net.farzad.crystalline.entity.client.AmethystShardProjectileEntityRenderer;
import net.farzad.crystalline.particle.AmethystShineParticle;
import net.farzad.crystalline.particle.ModParticles;

public class CrystallineClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityModelLayerRegistry.registerModelLayer(AmethystShardProjectileEntityModel.AMETHYST_SHARD_PROJECTILE, AmethystShardProjectileEntityModel::getTexturedModelData);
        EntityRendererRegistry.register(ModEntities.AMETHYST_SHARD_PROJECTILE_ENTITY, AmethystShardProjectileEntityRenderer::new);

        ParticleFactoryRegistry.getInstance().register(ModParticles.AMETHYST_SHINE, AmethystShineParticle.Factory::new);

    }
}
