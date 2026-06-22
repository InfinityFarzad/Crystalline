package net.farzad.crystalline.common.init;

import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.farzad.crystalline.client.particle.AmethystLampParticle;
import net.farzad.crystalline.common.Crystalline;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

public class CrystallineParticles {

    public static final SimpleParticleType AMETHYST_LAMP_PARTICLE = FabricParticleTypes.simple();

    public static void init() {
        Registry.register(BuiltInRegistries.PARTICLE_TYPE, Crystalline.id("amethyst_lamp_particle"), AMETHYST_LAMP_PARTICLE);
    }
    public static void clientInit() {
        ParticleFactoryRegistry.getInstance().register(CrystallineParticles.AMETHYST_LAMP_PARTICLE, AmethystLampParticle.Factory::new);
    }
}
