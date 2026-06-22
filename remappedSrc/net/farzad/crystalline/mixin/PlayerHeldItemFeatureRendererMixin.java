package net.farzad.crystalline.mixin;


import com.mojang.blaze3d.vertex.PoseStack;
import net.farzad.crystalline.client.render.entity.PlayerRenderStateInterface;
import net.farzad.crystalline.common.init.CrystallineItems;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.HeadedModel;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ItemInHandLayer;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
public abstract class PlayerHeldItemFeatureRendererMixin<S extends AvatarRenderState, M extends EntityModel<S> & ArmedModel & HeadedModel> extends ItemInHandLayer<S, M> {

    public PlayerHeldItemFeatureRendererMixin(RenderLayerParent<S, M> featureRendererContext) {
        super(featureRendererContext);
    }

    @Inject(method = "submitArmWithItem(Lnet/minecraft/client/renderer/entity/state/AvatarRenderState;Lnet/minecraft/client/renderer/item/ItemStackRenderState;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/HumanoidArm;Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;I)V", at = @At("HEAD"), cancellable = true)
    private void crystalline$repositionHandPos(S playerEntityRenderState, ItemStackRenderState itemRenderState, ItemStack itemStack, HumanoidArm arm, PoseStack matrixStack, SubmitNodeCollector orderedRenderCommandQueue, int i, CallbackInfo ci) {
        Player player = ((PlayerRenderStateInterface)playerEntityRenderState).crystalline$getPlayer();
        if (player != null && !player.isUsingItem()) {

            if ((player.getMainArm() == HumanoidArm.RIGHT && arm == HumanoidArm.LEFT) && player.getMainHandItem().getItem() == CrystallineItems.DIVIDER) {
                ci.cancel();
                return;
            }
            if ((player.getMainArm() == HumanoidArm.LEFT && arm == HumanoidArm.RIGHT) && player.getMainHandItem().getItem() == CrystallineItems.DIVIDER) {
                ci.cancel();
                return;
            }

            super.submitArmWithItem(playerEntityRenderState,itemRenderState,itemStack,arm,matrixStack,orderedRenderCommandQueue,i);
        }
    }
}
