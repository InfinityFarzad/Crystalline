package net.farzad.crystalline.mixin;


import net.farzad.crystalline.common.item.ModItems;
import net.farzad.crystalline.client.render.entity.PlayerRenderStateInterface;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.feature.PlayerHeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.ModelWithArms;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerHeldItemFeatureRenderer.class)
public abstract class PlayerHeldItemFeatureRendererMixin<S extends PlayerEntityRenderState, M extends EntityModel<S> & ModelWithArms & ModelWithHead> extends HeldItemFeatureRenderer<S, M> {

    public PlayerHeldItemFeatureRendererMixin(FeatureRendererContext<S, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Inject(method = "renderItem(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/render/item/ItemRenderState;Lnet/minecraft/util/Arm;Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", at = @At("HEAD"), cancellable = true)
    private void crystalline$repositionHandPos(S playerEntityRenderState, ItemRenderState itemRenderState, Arm arm, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        PlayerEntity player = ((PlayerRenderStateInterface)playerEntityRenderState).crystalline$getPlayer();
        if (!player.isUsingItem()) {

            if ((player.getMainArm() == Arm.RIGHT && arm == Arm.LEFT) && player.getMainHandStack().getItem() == ModItems.DIVIDER) {
                ci.cancel();
                return;
            }
            if ((player.getMainArm() == Arm.LEFT && arm == Arm.RIGHT) && player.getMainHandStack().getItem() == ModItems.DIVIDER) {
                ci.cancel();
                return;
            }

            super.renderItem(playerEntityRenderState, itemRenderState, arm, matrixStack, vertexConsumerProvider, i);
        }
    }
}
