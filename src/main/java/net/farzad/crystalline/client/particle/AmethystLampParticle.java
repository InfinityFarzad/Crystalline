package net.farzad.crystalline.client.particle;

import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Quaternionf;

import java.util.Random;


public class AmethystLampParticle extends SpriteBillboardParticle {

    private float rotAngle;

    public AmethystLampParticle(ClientWorld clientWorld, double x, double y, double z,
                                SpriteProvider spriteProvider) {
        super(clientWorld, x, y, z);
        Random rand = new Random();
        this.velocityMultiplier = 0.75f;
        /*this.scale *= 0.75F;*/
        this.scale = 4;
        this.collidesWithWorld = false;
        this.maxAge = 20 * clientWorld.random.nextBetween(3,5);
        this.setSpriteForAge(spriteProvider);
        this.red = 1f;
        this.alpha = 0.2f;
        this.green = 1f;
        this.blue = 1f;
        this.velocityX += rand.nextFloat(-0.50f,0.50f);
        this.velocityY += rand.nextFloat(-0.50f,0.50f);
        this.velocityZ += rand.nextFloat(-0.50f,0.50f);
    }

    @Override
    protected int getBrightness(float tint) {
        return 240;
    }

    private void fadeOut() {
        //this.alpha = (-(1f / (float) maxAge) * age + 1f);
        this.scale = +(-(1 / (float) maxAge) * age + 1);
        this.angle++;
    }

    public void render(VertexConsumer vertexConsumer, Camera camera, float tickProgress) {
        Quaternionf quaternionf = new Quaternionf();
        this.getRotator().setRotation(quaternionf, camera, tickProgress);
        if (this.angle != 0.0F) {
            quaternionf.rotateZ(MathHelper.lerp(tickProgress, this.lastAngle, this.angle));
        }

        this.render(vertexConsumer, camera, quaternionf, tickProgress);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            fadeOut();
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;

    }

    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Nullable
        @Override
        public Particle createParticle(SimpleParticleType parameters, ClientWorld world, double x, double y, double z,
                                       double velocityX, double velocityY, double velocityZ) {
            return new AmethystLampParticle(world, x, y, z, this.spriteProvider);
        }
    }
}
