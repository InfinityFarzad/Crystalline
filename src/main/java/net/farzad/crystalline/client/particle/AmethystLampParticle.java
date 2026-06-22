package net.farzad.crystalline.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SingleQuadParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.Random;


public class AmethystLampParticle extends SingleQuadParticle {

    private final float randomModifier;

    public AmethystLampParticle(ClientLevel clientWorld, double x, double y, double z,
                                TextureAtlasSprite sprite) {
        super(clientWorld, x, y, z, sprite);
        Random rand = new Random();
        this.randomModifier = (float) (clientWorld.random.nextGaussian());
        this.friction = 0.75f;
        this.quadSize = 4;
        this.hasPhysics = false;
        this.lifetime = 20 * clientWorld.random.nextIntBetweenInclusive(3, 5);
        this.rCol = 1f;
        this.alpha = 0.2f;
        this.gCol = 1f;
        this.bCol = 1f;
        this.xd += rand.nextFloat(-0.50f, 0.50f);
        this.yd += rand.nextFloat(-0.50f, 0.50f);
        this.zd += rand.nextFloat(-0.50f, 0.50f);

    }

    @Override
    protected int getLightColor(float tint) {
        return 240;
    }

    private void fadeOut() {
        this.quadSize = -(1 / (float) lifetime) * age + 1;
        this.roll += (float) (0.4 + this.randomModifier) / this.age;
        this.oRoll = this.roll;
        this.yd += (double) this.age / 80;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age++ >= this.lifetime) {
            this.remove();
        } else {
            fadeOut();
        }
    }

    @Override
    protected Layer getLayer() {
        return Layer.OPAQUE;
    }

    public record Factory(SpriteSet spriteProvider) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(SimpleParticleType parameters, ClientLevel world, double x, double y, double z, double velocityX, double velocityY, double velocityZ, RandomSource random) {
            return new AmethystLampParticle(world, x, y, z, spriteProvider.get(random));
        }
    }
}
