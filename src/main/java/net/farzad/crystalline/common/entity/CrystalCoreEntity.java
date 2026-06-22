package net.farzad.crystalline.common.entity;

import net.akws.chiseled_lib.client.util.RenderUtil;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import static net.farzad.crystalline.common.item.DividerItem.throwCrystal;

public class CrystalCoreEntity extends Entity implements TraceableEntity {
    private LivingEntity owner;
    private int maxTime;
    private int currentTime;
    private static final EntityDataAccessor<Float> HEALTH = SynchedEntityData.defineId(CrystalCoreEntity.class, EntityDataSerializers.FLOAT);
    private int crystalShootDelay = 1;


    public CrystalCoreEntity(EntityType<?> type, Level world) {
        super(type, world);
    }

    public CrystalCoreEntity(EntityType<?> type, LivingEntity owner, Vec3 pos) {
        super(type, owner.level());
        this.owner = owner;
        this.setPos(pos);
        this.maxTime = 20 * 12;
        this.currentTime = 0;
    }

    @Override
    public boolean canBeHitByProjectile() {

        return true;
    }

    @Override
    public void handleDamageEvent(DamageSource damageSource) {
        super.handleDamageEvent(damageSource);
        if (this.getEntityData().get(HEALTH) - 1 <= 0) {
            this.getEntityData().set(HEALTH, this.getEntityData().get(HEALTH) - 1);
            this.playSound(SoundEvents.AMETHYST_BLOCK_BREAK, 1, 1);
            if (this.level() instanceof ServerLevel world) {
                world.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK)), this.getX(), this.getY(), this.getZ(), 8, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, 0);
            }
        } else {
            this.remove(RemovalReason.KILLED);
        }
    }

    @Override
    protected void defineSynchedData(SynchedEntityData.Builder builder) {
        builder.define(HEALTH, 3.0f);
        builder.build();
    }


    @Override
    public void tick() {
        if (this.getDeltaMovement() != Vec3.ZERO) {
            this.setDeltaMovement(this.getDeltaMovement().scale(0.5));
            this.move(MoverType.SELF, this.getDeltaMovement());
        }
        if (this.currentTime >= maxTime) {
            this.remove(RemovalReason.DISCARDED);
        } else {
            this.currentTime++;
        }
        if (this.getOwner() instanceof Player player && crystalShootDelay >= 1) {
            for (int i = 4; i > 0; i--) {
                float pitch = -90 + Mth.randomBetween(player.getRandom(), 70, 120.0f) * (this.random.nextBoolean() ? 1 : -1);
                float yaw = Mth.randomBetween(player.getRandom(), -360.0f, 360.0f);
                throwCrystal(player, pitch, yaw, this.level(), false,false, new Vec3(this.getX(), this.getY() + 1, this.getZ()));

                double offsetFunny = this.level().getRandom().nextGaussian() / 20.0;
                RenderUtil.spawnParticle(this.level(), new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK, 1)), false, this.getX(), this.getY(), this.getZ(), offsetFunny, offsetFunny, offsetFunny);

            }

            crystalShootDelay = 0;
        } else {
            crystalShootDelay++;
        }

    }

    @Override
    public boolean hurtServer(ServerLevel world, DamageSource source, float amount) {
        return false;
    }

    @Override
    protected void readAdditionalSaveData(ValueInput view) {
        view.getIntOr("maxTime", this.maxTime);
        view.getIntOr("currentTime", this.currentTime);
        view.getIntOr("shootDelay", this.crystalShootDelay);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput view) {
        view.putInt("maxTime", this.maxTime);
        view.putInt("currentTime", this.currentTime);
        view.putInt("shootDelay", this.crystalShootDelay);
    }

    @Override
    protected double getDefaultGravity() {
        return 0;
    }

    @Override
    public void remove(RemovalReason reason) {
        if (this.level() instanceof ServerLevel world) {
            world.sendParticles(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK)), this.getX(), this.getY(), this.getZ(), 8, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, random.nextGaussian() / 20.0f, 0);
            super.remove(reason);
        }
    }

    @Override
    public EntityDimensions getDimensions(Pose pose) {
        return EntityDimensions.fixed(2, 2);
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
