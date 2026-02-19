package net.farzad.crystalline.common.sound;

import net.farzad.crystalline.common.item.ModItems;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MovingSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;

import java.util.Set;

public class ClientSideAmbientSoundInstance extends MovingSoundInstance {
    private final Entity entity;

    public ClientSideAmbientSoundInstance(SoundEvent soundEvent, SoundCategory soundCategory, Entity entity) {
        super(soundEvent, soundCategory, SoundInstance.createRandom());
        this.repeat = false;
        this.entity = entity;
        this.repeatDelay = 2;
    }

    @Override
    public void tick() {
        if (entity.isRemoved()) {
            this.setDone();
        } else {
            this.setPositionToEntity();
            if (entity instanceof PlayerEntity player && !player.getInventory().containsAny(Set.of(ModItems.CRYSTAL_HEART))) {
                this.setDone();
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

    @Override
    public boolean shouldAlwaysPlay() {
        return false;
    }

    public static ClientSideAmbientSoundInstance createSoundInstance(SoundEvent sound, SoundCategory soundCategory, Entity entity) {
        return new ClientSideAmbientSoundInstance(sound,soundCategory,entity);
    }
}
