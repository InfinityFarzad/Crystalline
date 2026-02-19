package net.farzad.crystalline.client.render.entity;

import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.entity.custom.AmethystShardProjectileEntity;
import net.farzad.crystalline.common.entity.custom.CrystalCoreEntity;
import net.minecraft.client.item.ItemModelManager;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.ItemEntityRenderer;
import net.minecraft.client.render.entity.ProjectileEntityRenderer;
import net.minecraft.client.render.entity.state.ItemEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;

import java.util.Random;

public class CrystalCoreEntityRenderer extends EntityRenderer<CrystalCoreEntity, CrystalCoreRenderState> {
    ItemModelManager itemModelManager;


    public CrystalCoreEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
        this.itemModelManager = ctx.getItemModelManager();
    }


    @Override
    public CrystalCoreRenderState createRenderState() {
        return new CrystalCoreRenderState();
    }

    @Override
    public void render(CrystalCoreRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {

        //ItemEntityRenderer.render(matrices,vertexConsumers,light,new ItemEntityRenderState(),state.random);


        renderCrystal(state,matrices,vertexConsumers,light,0);
        renderCrystal(state,matrices,vertexConsumers,light,20);
        renderCrystal(state,matrices,vertexConsumers,light,40);
        renderCrystal(state,matrices,vertexConsumers,light,80);
        renderCrystal(state,matrices,vertexConsumers,light,100);
        renderCrystal(state,matrices,vertexConsumers,light,120);

        super.render(state,matrices,vertexConsumers,light);
    }

    private void renderCrystal(CrystalCoreRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int offset) {
        matrices.push();
        matrices.translate(0.2,-1.4 + MathHelper.sin((state.age + offset) / 40),0);
        matrices.scale(4,4,4);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotation((state.age + offset) / 5.0f));
        matrices.multiply(RotationAxis.POSITIVE_Z.rotation((float)Math.toRadians(45)));
        state.itemRenderState.render(matrices,vertexConsumers,light,OverlayTexture.DEFAULT_UV);
        matrices.pop();
    }

    @Override
    public void updateRenderState(CrystalCoreEntity entity, CrystalCoreRenderState state, float tickProgress) {
        super.updateRenderState(entity, state, tickProgress);
        state.random = entity.getRandom();
        state.itemRenderState = new ItemRenderState();
        itemModelManager.updateForNonLivingEntity(state.itemRenderState,new ItemStack(Items.AMETHYST_SHARD),ItemDisplayContext.THIRD_PERSON_LEFT_HAND,entity);
    }
}
