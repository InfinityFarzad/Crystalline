package net.farzad.crystalline.mixin;


import net.farzad.crystalline.common.item.custom.DividerItem;
import net.farzad.crystalline.client.render.entity.PlayerRenderStateInterface;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Arm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(method = "getArmPose(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/util/Arm;)Lnet/minecraft/client/render/entity/model/BipedEntityModel$ArmPose;", at = @At("HEAD"), cancellable = true)
    private static void crystalline$getArmPose(AbstractClientPlayerEntity player, Arm arm, CallbackInfoReturnable<BipedEntityModel.ArmPose> cir) {
        ItemStack itemStack = player.getStackInArm(arm);

        if (itemStack.getItem() instanceof DividerItem) {
            if (!player.isUsingItem()) {
                cir.setReturnValue(BipedEntityModel.ArmPose.CROSSBOW_CHARGE);
            }
        }
    }

    @Inject(method = "updateRenderState(Lnet/minecraft/client/network/AbstractClientPlayerEntity;Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;F)V", at = @At("HEAD"))
    private void crystalline$updateRenderState(AbstractClientPlayerEntity abstractClientPlayerEntity, PlayerEntityRenderState playerEntityRenderState, float f, CallbackInfo ci) {
        ((PlayerRenderStateInterface)playerEntityRenderState).crystalline$setPlayer(abstractClientPlayerEntity);
    }
}
