package net.farzad.crystalline.client.render.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.farzad.crystalline.common.entity.CrystalCoreEntity;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.item.ItemModelResolver;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.client.renderer.state.CameraRenderState;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public class CrystalCoreEntityRenderer extends EntityRenderer<CrystalCoreEntity, CrystalCoreRenderState> {
    final ItemModelResolver itemModelManager;

    public CrystalCoreEntityRenderer(EntityRendererProvider.Context ctx) {
        super(ctx);
        this.itemModelManager = ctx.getItemModelResolver();
    }

    @Override
    public CrystalCoreRenderState createRenderState() {
        return new CrystalCoreRenderState();
    }

    @Override
    public void submit(CrystalCoreRenderState state, PoseStack matrices, SubmitNodeCollector queue, CameraRenderState cameraState) {

        renderCrystal(state, matrices, queue, state.lightCoords, 0);
        renderCrystal(state, matrices, queue, state.lightCoords, 20);
        renderCrystal(state, matrices, queue, state.lightCoords, 40);
        renderCrystal(state, matrices, queue, state.lightCoords, 80);
        renderCrystal(state, matrices, queue, state.lightCoords, 100);
        renderCrystal(state, matrices, queue, state.lightCoords, 120);
        super.submit(state, matrices, queue, cameraState);
    }

    private void renderCrystal(CrystalCoreRenderState state, PoseStack matrices, SubmitNodeCollector queue, int light, int offset) {
        matrices.pushPose();
        matrices.translate(0.2, -0.4 + Mth.sin((state.ageInTicks + offset) / 40), 0);
        matrices.scale(4, 4, 4);
        matrices.mulPose(Axis.YP.rotation((state.ageInTicks + offset) / 5.0f));
        matrices.mulPose(Axis.ZP.rotation((float) Math.toRadians(45)));
        state.itemRenderState.submit(matrices, queue, light, OverlayTexture.NO_OVERLAY, state.outlineColor);
        matrices.popPose();
    }

    @Override
    public void extractRenderState(CrystalCoreEntity entity, CrystalCoreRenderState state, float tickProgress) {
        super.extractRenderState(entity, state, tickProgress);
        state.itemRenderState = new ItemStackRenderState();
        itemModelManager.updateForNonLiving(state.itemRenderState, new ItemStack(Items.AMETHYST_SHARD), ItemDisplayContext.THIRD_PERSON_LEFT_HAND, entity);
    }
}
