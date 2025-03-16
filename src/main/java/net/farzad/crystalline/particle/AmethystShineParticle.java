package net.farzad.crystalline.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;


public class AmethystShineParticle extends SpriteBillboardParticle {
    private final SpriteProvider spriteProvider;
    private float rotationAngle = 0;

    AmethystShineParticle(ClientWorld world, double x, double y, double z, SpriteProvider spriteProvider) {
        super(world, x, y, z, 0.0, 0.0, 0.0);  // Initialize with zero velocity
        this.spriteProvider = spriteProvider;
        this.maxAge = 70;


        float f = this.random.nextFloat() * 0.6F + 0.4F;
        this.red = f;
        this.green = f;
        this.blue = f;


        this.scale = 0.09F;


        double randomVelocityX = this.random.nextGaussian() * 0.05;  // Random X velocity
        double randomVelocityY = this.random.nextGaussian() * 0.05;  // Random Y velocity
        double randomVelocityZ = this.random.nextGaussian() * 0.05;  // Random Z velocity
        this.collidesWithWorld = true;


        this.setVelocity(randomVelocityX, randomVelocityY, randomVelocityZ);

        this.setSpriteForAge(spriteProvider);
    }

    private void fadeOut() {
        this.alpha = (-(1 / (float) maxAge) * age + 1);
    }

    @Override
    protected void method_60373(VertexConsumer vertexConsumer, Camera camera, Quaternionf quaternionf, float f) {
        Vec3d cameraPos = camera.getPos();
        float offsetX = (float) (MathHelper.lerp(f, this.prevPosX, this.x) - cameraPos.getX());
        float offsetY = (float) (MathHelper.lerp(f, this.prevPosY, this.y) - cameraPos.getY());
        float offsetZ = (float) (MathHelper.lerp(f, this.prevPosZ, this.z) - cameraPos.getZ());


        Quaternionf rotation = new Quaternionf(camera.getRotation());


        rotation.rotateZ((float) Math.toRadians(rotationAngle));

        this.method_60374(vertexConsumer, rotation, offsetX, offsetY, offsetZ, f);
    }

    @Override
    public int getBrightness(float tint) {
        return 15728880;
    }

    @Override
    public void tick() {
        this.prevPosX = this.x;
        this.prevPosY = this.y;
        this.prevPosZ = this.z;


        this.x += this.velocityX;
        this.y += this.velocityY;
        this.z += this.velocityZ;

        fadeOut();
        if (this.age++ >= this.maxAge) {
            this.markDead();
        } else {
            this.setSpriteForAge(this.spriteProvider);
            rotationAngle = (rotationAngle + 5) % 360;
        }
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_TRANSLUCENT;
    }

    @Environment(EnvType.CLIENT)
    public static class Factory implements ParticleFactory<SimpleParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        public Particle createParticle(SimpleParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            return new AmethystShineParticle(clientWorld, d, e, f, this.spriteProvider);
        }
    }
}
