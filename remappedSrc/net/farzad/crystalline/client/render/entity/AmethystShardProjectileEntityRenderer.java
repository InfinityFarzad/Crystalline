package net.farzad.crystalline.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.entity.AmethystShardProjectileEntity;
import net.minecraft.client.render.*;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.ArrowRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.state.ArrowRenderState;
import net.minecraft.client.renderer.rendertype.RenderTypes;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.Identifier;
import net.minecraft.util.Mth;

public class AmethystShardProjectileEntityRenderer extends ArrowRenderer<AmethystShardProjectileEntity, ArrowRenderState> {
    protected AmethystShardProjectileEntityModel model;
    protected Identifier TEXTURE = Crystalline.id("textures/entity/amethyst_shard/amethyst_shard_projectile_entity.png");

    public AmethystShardProjectileEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.model = new AmethystShardProjectileEntityModel(ctx.bakeLayer(AmethystShardProjectileEntityModel.AMETHYST_SHARD_PROJECTILE));
    }

    @Override
    public ArrowRenderState createRenderState() {
        return new ArrowRenderState();
    }

    @Override
    public void submit(ArrowRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraRenderState) {
        matrices.pushPose();
        matrices.mulPose(Axis.YP.rotationDegrees(state.yRot - 90.0F));
        matrices.mulPose(Axis.ZP.rotationDegrees(state.xRot));
        this.model.setAngles(state);
        queue.submitModel(this.model,state,matrices,RenderTypes.entityCutoutNoCull(TEXTURE), (int) (state.lightCoords + Mth.sin(state.ageInTicks)),OverlayTexture.NO_OVERLAY,state.outlineColor,null);
        matrices.popPose();
    }

    @Override
    protected Identifier getTextureLocation(ArrowRenderState state) {
        return TEXTURE;
    }

    @Override
    public void updateRenderState(AmethystShardProjectileEntity entity, ArrowRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
    }
}
