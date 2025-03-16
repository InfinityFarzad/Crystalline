package net.farzad.crystalline.particle;


import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;

import net.farzad.crystalline.Crystalline;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;


public class ModParticles {
    public static final SimpleParticleType AMETHYST_SHINE = FabricParticleTypes.simple();


    public static void registerParticles() {
        Registry.register(Registries.PARTICLE_TYPE, Identifier.of(Crystalline.MOD_ID, "amethyst_shine"),
                AMETHYST_SHINE);


    }
}
