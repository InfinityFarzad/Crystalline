package net.farzad.crystalline.entity.client;

import net.farzad.crystalline.Crystalline;
import net.farzad.crystalline.entity.custom.AmethystShardProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class AmethystShardProjectileEntityRenderer extends ProjectileEntityRenderer<AmethystShardProjectileEntity> {
    protected AmethystShardProjectileEntityModel model;
    public static final EntityModelLayer AMETHYST_SHARD_PROJECTILE = new EntityModelLayer(Identifier.of(Crystalline.MOD_ID, "amethyst_shard_projectile2"), "main");

    public AmethystShardProjectileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new AmethystShardProjectileEntityModel(ctx.getPart(AMETHYST_SHARD_PROJECTILE));
    }

    @Override
    public Identifier getTexture(AmethystShardProjectileEntity entity) {
        return Identifier.of(Crystalline.MOD_ID, "textures/entity/amethyst_shard_projectile_entity/amethyst_shard_projectile_entity.png");

    }




    @Override
    public void render(AmethystShardProjectileEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();



        VertexConsumer vertexconsumer = ItemRenderer.getDirectItemGlintConsumer(vertexConsumers,
                this.model.getLayer(Identifier.of(Crystalline.MOD_ID, "textures/entity/amethyst_shard__projectile_entity/amethyst_shard_projectile_entity.png")), false, false);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);

        matrices.pop();
        super.render(entity, yaw, tickDelta, matrices, vertexConsumers, light);
    }
}
