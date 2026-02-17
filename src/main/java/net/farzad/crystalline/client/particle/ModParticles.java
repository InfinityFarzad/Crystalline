package net.farzad.crystalline.client.particle;

import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.farzad.crystalline.common.Crystalline;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModParticles {

    public static final SimpleParticleType AMETHYST_LAMP_PARTICLE = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(Registries.PARTICLE_TYPE,Crystalline.id("amethyst_lamp_particle"),AMETHYST_LAMP_PARTICLE);
    }
}
