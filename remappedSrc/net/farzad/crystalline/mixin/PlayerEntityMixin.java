package net.farzad.crystalline.mixin;

import net.farzad.crystalline.common.init.CrystallineDataComponentTypes;
import net.farzad.crystalline.common.item.DividerItem;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import static net.farzad.crystalline.common.item.DividerItem.getCharge;
import static net.farzad.crystalline.common.item.DividerItem.throwCrystal;
import static net.farzad.crystalline.common.item.SingleSlotAbilityItem.hasHeart;

@Mixin(Player.class)
public class PlayerEntityMixin {

    @Inject(method = "tick", at = @At("TAIL"))
    private void crystalline$enableCrysatalSpread(CallbackInfo ci) {
        Player player = (Player) (Object)this;
        ItemStack stack = player.getMainHandItem();
        Level world = player.level();
        if (stack.getItem() instanceof DividerItem && stack.getOrDefault(CrystallineDataComponentTypes.DIVIDE_CHARGE,0) >= 2  && hasHeart(DividerItem.DividerAbilityTypes.CRYSTAL_HEART,stack) && player.isUsingItem()) {
            player.setDeltaMovement(0,0,0);
            player.fallDistance = 0;
            if (getCharge(stack) >= 2) {
                if (!world.isClientSide()) {
                    for (int i = 5; i > 0; i--) {
                        float pitch = player.getXRot() + Mth.randomBetween(player.getRandom(),-10.0f,10.0f) * 20;
                        float yaw = player.getYRot() + Mth.randomBetween(player.getRandom(),-10.0f,10.0f) * 20;
                        Vec3 crystalPos = new Vec3(player.getX(),player.getY() + 1.2, player.getZ());

                        throwCrystal(player,pitch,yaw,world,false, crystalPos);
                        world.playSound(player, player.blockPosition(),SoundEvents.AMETHYST_BLOCK_BREAK,player.getSoundSource(),1,player.getRandom().nextIntBetweenInclusive(1,2));
                    }
                }

            }

            DividerItem.setCharge(stack,DividerItem.getCharge(stack) - 1);
        }
    }

}
