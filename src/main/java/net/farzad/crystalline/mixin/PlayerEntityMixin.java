package net.farzad.crystalline.mixin;

import net.farzad.crystalline.common.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.common.item.ModItems;
import net.farzad.crystalline.common.item.custom.DividerItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.farzad.crystalline.common.item.custom.DividerItem.getCharge;
import static net.farzad.crystalline.common.item.custom.DividerItem.throwCrystal;
import static net.farzad.crystalline.common.item.custom.SingleSlotAbilityItem.hasHeart;

@Mixin(PlayerEntity.class)
public class PlayerEntityMixin {

    @Unique
    private int crystallineTimerDelay = 40;

    @Inject(method = "writeCustomDataToNbt", at = @At("TAIL"))
    private void crystalline$addData(NbtCompound nbt, CallbackInfo ci) {
        nbt.putInt("crystallineTimerDelay",this.crystallineTimerDelay);
    }

    @Inject(method = "readCustomDataFromNbt", at = @At("TAIL"))
    private void crystalline$readData(NbtCompound nbt, CallbackInfo ci) {
        nbt.getInt("crystallineTimerDelay",this.crystallineTimerDelay);
    }

    @ModifyArg(method = "spawnSweepAttackParticles", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/world/ServerWorld;spawnParticles(Lnet/minecraft/particle/ParticleEffect;DDDIDDDD)I"), index = 0)
    private ParticleEffect crystalline$injectSpawnSweepAttackParticles(ParticleEffect original) {
        PlayerEntity player = (PlayerEntity) (Object) this;
        if (player.getWeaponStack().isOf(ModItems.DIVIDER)) {
            ParticleEffect particleEffect = ParticleTypes.SWEEP_ATTACK;
            if (particleEffect != null) {
                return particleEffect;
            }
        }
        return original;
    }

    @Inject(method = "tick", at = @At("TAIL"))
    private void crystalline$enableCrysatalSpread(CallbackInfo ci) {
        PlayerEntity player = (PlayerEntity) (Object)this;
        ItemStack stack = player.getMainHandStack();
        World world = player.getWorld();
        if (stack.getItem() instanceof DividerItem && stack.getOrDefault(ModDataComponentTypes.DIVIDE_CHARGE,0) >= 2  && hasHeart(DividerItem.DividerAbilityTypes.CRYSTAL_HEART,stack) && player.isUsingItem()) {
            player.setVelocity(0,0,0);
            player.fallDistance = 0;
            if (getCharge(stack) >= 2/* && this.crystallineTimerDelay <= 0*/) {
                if (!world.isClient) {
//                    for (int yawOffset = value ; yawOffset <= value; yawOffset++) {
//                        for (int pitchOffset = -2; pitchOffset <= 2; pitchOffset++) {
//                            float pitch = player.getPitch() + pitchOffset * 20;
//                            float yaw = player.getYaw() + yawOffset * 20;
//                            throwCrystal(player, pitch, yaw, world);
//                        }
//                    }
                    for (int i = 5; i > 0; i--) {
                        float pitch = player.getPitch() + MathHelper.nextBetween(player.getRandom(),-10.0f,10.0f) * 20;
                        float yaw = player.getYaw() + MathHelper.nextBetween(player.getRandom(),-10.0f,10.0f) * 20;
                        throwCrystal(player,pitch,yaw,world);
                    }



                    crystallineTimerDelay = 40;
                }
                this.crystallineTimerDelay--;

            }

            DividerItem.setCharge(stack,DividerItem.getCharge(stack) - 1);


        }
    }

}
