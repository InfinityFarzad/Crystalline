package net.farzad.crystalline.mixin;

import net.farzad.crystalline.client.render.entity.PlayerRenderStateInterface;
import net.minecraft.client.renderer.entity.state.AvatarRenderState;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(AvatarRenderState.class)
public class PlayerEntityRenderStateMixin implements PlayerRenderStateInterface {

    @Unique
    Player player;


    @Override
    public void crystalline$setPlayer(Player Iplayer) {
        this.player = Iplayer;
    }

    @Override
    public Player crystalline$getPlayer() {
        return this.player;
    }
}
