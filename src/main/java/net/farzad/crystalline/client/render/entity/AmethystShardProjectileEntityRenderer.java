package net.farzad.crystalline.client.render.entity;

import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.entity.custom.AmethystShardProjectileEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;

public class AmethystShardProjectileEntityRenderer extends ProjectileEntityRenderer<AmethystShardProjectileEntity, AmethystShardProjectileEntityRenderState> {
    protected AmethystShardProjectileEntityModel model;
    protected Identifier TEXTURE = Identifier.of(Crystalline.MOD_ID,"textures/entity/amethyst_shard/amethyst_shard_projectile_entity.png");

    public AmethystShardProjectileEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.model = new AmethystShardProjectileEntityModel(ctx.getPart(AmethystShardProjectileEntityModel.AMETHYST_SHARD_PROJECTILE));
    }

    @Override
    public AmethystShardProjectileEntityRenderState createRenderState() {
        return new AmethystShardProjectileEntityRenderState();
    }

    @Override
    public void render(AmethystShardProjectileEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        matrices.push();
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(state.yaw - 90.0F));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(state.pitch));
        VertexConsumer vertexconsumer = ItemRenderer.getItemGlintConsumer(vertexConsumers, this.model.getLayer(TEXTURE), false, false);
        this.model.setAngles(state);
        this.model.render(matrices, vertexconsumer, light, OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    @Override
    protected Identifier getTexture(AmethystShardProjectileEntityRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(AmethystShardProjectileEntity entity, AmethystShardProjectileEntityRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
    }
}
