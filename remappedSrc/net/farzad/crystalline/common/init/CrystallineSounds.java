package net.farzad.crystalline.common.init;

import net.farzad.crystalline.common.Crystalline;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvent;

public class CrystallineSounds {

    public static final SoundEvent HEART_BEAT = register("heart_beat");

    private static SoundEvent register(String id) {
        Identifier path = Crystalline.id(id);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, path,SoundEvent.createVariableRangeEvent(path));
    }

    public static void init() {}

}
