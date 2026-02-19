package net.farzad.crystalline.common.entity.custom;

import net.farzad.crystalline.common.entity.ModEntities;
import net.farzad.crystalline.common.item.ModItems;
import net.farzad.crystalline.common.util.ModDamageTypes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Random;
import java.util.Vector;

public class AmethystShardProjectileEntity extends PersistentProjectileEntity {

    private boolean ignoreIFrames;

    public AmethystShardProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public AmethystShardProjectileEntity(World world, PlayerEntity player, boolean shouldIgnoreIframes, Vec3d pos) {
        super(ModEntities.AMETHYST_SHARD_PROJECTILE_ENTITY, player, world, ItemStack.EMPTY, new ItemStack(ModItems.DIVIDER));
        this.ignoreIFrames = shouldIgnoreIframes;
        this.setPosition(pos);
        this.setStack(new ItemStack(Items.AMETHYST_SHARD));
    }

    @Override
    protected double getGravity() {
        return 0.08;
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK;
    }

    private void spawnParticles() {
        double offset = random.nextGaussian() / 40f;
        for (int i = 0; i < 8; ++i) {
            for (PlayerEntity player : getWorld().getPlayers()) {
                player.getEntityWorld().addParticleClient(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK, 1)), this.getX(), this.getY(), this.getZ(), offset,  0.2 + offset, offset);
            }
        }
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        spawnParticles();
        this.remove(RemovalReason.DISCARDED);

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity playerEntity = getOwner();
        Entity targetEntity = entityHitResult.getEntity();
        if (targetEntity.getType() != EntityType.ENDERMAN && targetEntity != playerEntity && playerEntity != null) {
            if (targetEntity instanceof LivingEntity living && getWorld() instanceof ServerWorld serverWorld) {
                targetEntity.damage(serverWorld, getDamageSource(playerEntity), (float) 3);
                onHit(living);
            }
        }
        discard();
        getWorld().playSound(null, getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.NEUTRAL, 1.0f, 1.5f);
    }

    @Override
    public boolean shouldRender(double cameraX, double cameraY, double cameraZ) {
        return true;
    }

    public void setVelocity(Entity shooter, float pitch, float yaw, float roll, float speed, float divergence) {
        float f = -MathHelper.sin(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        float g = -MathHelper.sin((pitch + roll) * ((float)Math.PI / 180F));
        float h = MathHelper.cos(yaw * ((float)Math.PI / 180F)) * MathHelper.cos(pitch * ((float)Math.PI / 180F));
        this.setVelocity(f, g, h, speed, divergence);
        Vec3d vec3d = Vec3d.ZERO;
        this.setVelocity(this.getVelocity().add(vec3d.x, shooter.isOnGround() ? (double)0.0F : vec3d.y, vec3d.z));
    }

    protected DamageSource getDamageSource(Entity owner) {
        return this.ignoreIFrames ? createDamageSource(owner,ModDamageTypes.AMETHYST_SHARD) : createDamageSource(owner,ModDamageTypes.STRONG_AMETHYST_SHARD);
    }

    private static DamageSource createDamageSource(Entity owner, RegistryKey<DamageType> damageType) {
        return new DamageSource(
                owner.getWorld().getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(damageType.getValue()).get());
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        nbt.getBoolean("crystallineIgnoreIframes",this.ignoreIFrames);
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return new ItemStack(Items.AMETHYST_SHARD);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putBoolean("crystallineIgnoreIframes",this.ignoreIFrames);
    }
}

