package net.farzad.crystalline.common.entity;

import net.akws.chiseled_lib.client.util.RenderUtil;
import net.farzad.crystalline.common.init.CrystallineDamageTypes;
import net.farzad.crystalline.common.init.CrystallineEntities;
import net.farzad.crystalline.common.init.CrystallineItems;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageType;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.Vec3;

public class AmethystShardProjectileEntity extends AbstractArrow {

    private boolean ignoreIFrames;

    public AmethystShardProjectileEntity(EntityType<? extends AbstractArrow> entityType, Level world) {
        super(entityType, world);
    }

    public AmethystShardProjectileEntity(Level world, Player player, boolean shouldIgnoreIframes, Vec3 pos) {
        super(CrystallineEntities.AMETHYST_SHARD_PROJECTILE_ENTITY, player, world, ItemStack.EMPTY, new ItemStack(CrystallineItems.DIVIDER));
        this.ignoreIFrames = shouldIgnoreIframes;
        this.setPos(pos);
        this.setPickupItemStack(new ItemStack(Items.AMETHYST_SHARD));
    }

    private DamageSource createDamageSource(Entity owner, ResourceKey<DamageType> damageType) {
        return owner.damageSources().source(damageType,this,owner);
    }

    @Override
    protected double getDefaultGravity() {
        return 0.08;
    }

    @Override
    protected SoundEvent getDefaultHitGroundSoundEvent() {
        return SoundEvents.AMETHYST_CLUSTER_BREAK;
    }

    private void spawnParticles() {
        for (int i = 0; i < 8; ++i) {
            double offset = random.nextGaussian() / 40f;
            RenderUtil.spawnParticle(this.level(), new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK, 1)), false, this.getX(), this.getY(), this.getZ(), offset, 0.2 + offset, offset);
        }
    }

    @Override
    protected void onHitBlock(BlockHitResult blockHitResult) {
        level().playSound(null, blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.NEUTRAL, 1.0f, 1.5f);
        spawnParticles();
        this.remove(RemovalReason.DISCARDED);
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Entity playerEntity = getOwner();
        Entity targetEntity = entityHitResult.getEntity();
        if (targetEntity.getType() != EntityType.ENDERMAN && targetEntity != playerEntity && playerEntity != null) {
            if (targetEntity instanceof LivingEntity living && level() instanceof ServerLevel serverWorld) {
                targetEntity.hurtServer(serverWorld, getDamageSource(playerEntity), (float) 3);
                doPostHurtEffects(living);
            }
        }
        discard();
        level().playSound(null, blockPosition(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.NEUTRAL, 1.0f, 1.5f);
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    public void shootFromRotation(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence, boolean inheritVelocity) {
        float f = -Mth.sin(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        float g = -Mth.sin((pitch + roll) * ((float) Math.PI / 180F));
        float h = Mth.cos(yaw * ((float) Math.PI / 180F)) * Mth.cos(pitch * ((float) Math.PI / 180F));
        if (inheritVelocity) {
            this.setDeltaMovement(this.getDeltaMovement().add(shooter.getKnownMovement()));
        }
        this.shoot(f, g, h, speed, divergence);
    }

    protected DamageSource getDamageSource(Entity owner) {
        return this.ignoreIFrames ? createDamageSource(owner, CrystallineDamageTypes.AMETHYST_SHARD) : createDamageSource(owner, CrystallineDamageTypes.STRONG_AMETHYST_SHARD);
    }

    @Override
    protected void readAdditionalSaveData(ValueInput view) {
        super.readAdditionalSaveData(view);
        view.getBooleanOr("crystallineIgnoreIframes", this.ignoreIFrames);
    }

    @Override
    protected ItemStack getDefaultPickupItem() {
        return new ItemStack(Items.AMETHYST_SHARD);
    }

    @Override
    protected void addAdditionalSaveData(ValueOutput view) {
        super.addAdditionalSaveData(view);
        view.putBoolean("crystallineIgnoreIframes", this.ignoreIFrames);
    }
}

