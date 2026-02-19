package net.farzad.crystalline.common.sound;

import net.farzad.crystalline.common.Crystalline;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class ModSounds {

    public static final SoundEvent HEART_BEAT = register("heart_beat");

    private static SoundEvent register(String id) {
        Identifier path = Crystalline.id(id);
        return Registry.register(Registries.SOUND_EVENT, path,SoundEvent.of(path));
    }

    public static void init() {}

}
