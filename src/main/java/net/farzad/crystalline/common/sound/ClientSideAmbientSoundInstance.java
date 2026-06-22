package net.farzad.crystalline.common.sound;

import net.farzad.crystalline.common.init.CrystallineItems;
import net.minecraft.client.resources.sounds.AbstractTickableSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

import java.util.Set;

public class ClientSideAmbientSoundInstance extends AbstractTickableSoundInstance {
    private final Entity entity;

    public ClientSideAmbientSoundInstance(SoundEvent soundEvent, SoundSource soundCategory, Entity entity) {
        super(soundEvent, soundCategory, SoundInstance.createUnseededRandom());
        this.looping = false;
        this.entity = entity;
        this.delay = 2;
    }

    @Override
    public void tick() {
        if (entity.isRemoved()) {
            this.stop();
        } else {
            this.setPositionToEntity();
            if (entity instanceof Player player && !player.getInventory().hasAnyOf(Set.of(CrystallineItems.CRYSTAL_HEART))) {
                this.stop();
            }
        }
    }

    private void setPositionToEntity() {
        if (entity != null) {
            this.x = this.entity.getX();
            this.y = this.entity.getY();
            this.z = this.entity.getZ();
        }
    }

    public static ClientSideAmbientSoundInstance createSoundInstance(SoundEvent sound, SoundSource soundCategory, Entity entity) {
        return new ClientSideAmbientSoundInstance(sound, soundCategory, entity);
    }
}
