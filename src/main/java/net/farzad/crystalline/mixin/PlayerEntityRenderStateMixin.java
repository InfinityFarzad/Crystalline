package net.farzad.crystalline.mixin;

import net.farzad.crystalline.client.render.entity.PlayerRenderStateInterface;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(PlayerEntityRenderState.class)
public class PlayerEntityRenderStateMixin implements PlayerRenderStateInterface {

    @Unique
    PlayerEntity player;


    @Override
    public void crystalline$setPlayer(PlayerEntity Iplayer) {
        this.player = Iplayer;
    }

    @Override
    public PlayerEntity crystalline$getPlayer() {
        return this.player;
    }
}
