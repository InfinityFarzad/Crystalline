package net.farzad.crystalline.mixin;

import net.farzad.crystalline.common.init.CrystallineDataComponentTypes;
import net.farzad.crystalline.common.item.DividerItem;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.farzad.crystalline.common.item.DividerItem.getCharge;
import static net.farzad.crystalline.common.item.DividerItem.throwCrystal;
import static net.farzad.crystalline.common.item.SingleSlotAbilityItem.hasHeart;

@Mixin(Player.class)
public abstract class PlayerEntityMixin {

    @Shadow
    public abstract void playSound(SoundEvent soundEvent, float f, float g);

    @Inject(method = "tick", at = @At("TAIL"))
    private void crystalline$enableCrystalSpread(CallbackInfo ci) {
        Player player = (Player) (Object) this;
        ItemStack stack = player.getMainHandItem();
        Level world = player.level();
        if (stack.getItem() instanceof DividerItem && stack.getOrDefault(CrystallineDataComponentTypes.DIVIDE_CHARGE, 0) >= 2 && hasHeart("crystal_heart", stack) && player.isUsingItem()) {
            player.setDeltaMovement(0, 0, 0);
            player.fallDistance = 0;
            if (getCharge(stack) >= 2) {
                if (!world.isClientSide()) {
                    for (int i = 5; i > 0; i--) {
                        float pitch = player.getXRot() + Mth.randomBetween(player.getRandom(), -10.0f, 10.0f) * 20;
                        float yaw = player.getYRot() + Mth.randomBetween(player.getRandom(), -10.0f, 10.0f) * 20;
                        Vec3 crystalPos = new Vec3(player.getX(), player.getY() + 1.2, player.getZ());
                        world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

                        throwCrystal(player, pitch, yaw, world, false, true, crystalPos);
                        this.playSound(SoundEvents.AMETHYST_BLOCK_BREAK,1,1);
                    }
                }

            }

            DividerItem.setCharge(stack, Math.max(getCharge(stack) - 1, 0));
        }
    }

}
