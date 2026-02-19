package net.farzad.crystalline.common.entity.custom;

import net.farzad.crystalline.common.Crystalline;
import net.minecraft.block.Blocks;
import net.minecraft.entity.*;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import static net.farzad.crystalline.common.item.custom.DividerItem.throwCrystal;

public class CrystalCoreEntity extends Entity implements Ownable {
    private LivingEntity owner;
    private int maxTime;
    private boolean isRemoved;
    private int currentTime;
    private static final TrackedData<Float> HEALTH = DataTracker.registerData(CrystalCoreEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private int crystalShootDelay = 2;


    public CrystalCoreEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    public CrystalCoreEntity(EntityType<?> type, LivingEntity owner,Vec3d pos) {
        super(type, owner.getWorld());
        this.owner = owner;
        this.setPosition(pos);
        this.maxTime = 20 * 12;
        this.currentTime = 0;
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        super.onDamaged(damageSource);
        if (this.getDataTracker().get(HEALTH) - 1 <= 0) {
            this.getDataTracker().set(HEALTH,this.getDataTracker().get(HEALTH) - 1);
            this.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK,1,1);
            if (this.getWorld() instanceof ServerWorld world) {
                world.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK)), this.getX(), this.getY(), this.getZ(), 8, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, 0);
            }
        } else {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(HEALTH,3.0f);
        builder.build();
    }

    @Override
    public void tick() {
        if (this.currentTime >= maxTime) {
            this.remove(RemovalReason.DISCARDED);
        } else {
            this.currentTime++;
        }
        if (this.getOwner() instanceof PlayerEntity player && crystalShootDelay >= 1) {
            for (int i = 5; i > 0; i--) {
                int offset = MathHelper.nextBetween(player.getRandom(), -1, 1);
                float pitch = -90 + MathHelper.nextBetween(player.getRandom(), -20.0f, 20.0f);
                float yaw = MathHelper.nextBetween(player.getRandom(), -360.0f, 360.0f);
                throwCrystal(player, pitch, yaw, this.getWorld(), true, new Vec3d(this.getX(), this.getY(), this.getZ()));
                if (this.getWorld() instanceof ServerWorld world) {

                    world.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK)), this.getX() + offset,this.getY(), this.getZ() + offset, 8, 0, 0, 0, 0);
                }
            }

            crystalShootDelay = 0;
        } else {
            crystalShootDelay++;
        }

        if (this.getVelocity() != Vec3d.ZERO) {
            this.addVelocity(-0.005,-0.005,-0.005);
        }
        //Crystalline.LOGGER.info(this.getPos().toString());
        //super.tick();
    }

    @Override
    public boolean damage(ServerWorld world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        nbt.getInt("maxTime",this.maxTime);
        nbt.getInt("currentTime",this.currentTime);
        nbt.getBoolean("isRemovedFromExist",this.isRemoved);
        nbt.getInt("shootDelay",this.crystalShootDelay);
    }

    @Override
    protected double getGravity() {
        return 0;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (this.getWorld() instanceof ServerWorld world) {
            world.spawnParticles(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK)), this.getX(), this.getY(), this.getZ(), 8, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, 0);
            super.remove(reason);
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt("maxTime",this.maxTime);
        nbt.putInt("currentTime",this.currentTime);
        nbt.putBoolean("isRemovedFromExist",this.isRemoved);
        nbt.putInt("shootDelay",this.crystalShootDelay);
    }

    @Override
    public EntityDimensions getDimensions(EntityPose pose) {
        return EntityDimensions.fixed(2,2);
    }

    @Override
    public boolean isInvisible() {
        return false;
    }

    @Override
    public @Nullable Entity getOwner() {
        return this.owner;
    }
}
