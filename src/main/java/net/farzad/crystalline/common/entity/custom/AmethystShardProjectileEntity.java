package net.farzad.crystalline.common.entity.custom;

import net.farzad.crystalline.common.entity.ModEntities;
import net.farzad.crystalline.common.util.ModDamageTypes;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

import java.util.Random;

public class AmethystShardProjectileEntity extends PersistentProjectileEntity {

    public AmethystShardProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public AmethystShardProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.AMETHYST_SHARD_PROJECTILE_ENTITY, player, world, ItemStack.EMPTY, player.getMainHandStack());
    }

    @Override
    protected double getGravity() {
        return 0.08;
    }

    @Override
    protected ItemStack getDefaultItemStack() {
        return this.getItemStack();
    }

    @Override
    protected void onCollision(HitResult hitResult) {
        super.onCollision(hitResult);
    }

    @Override
    protected SoundEvent getHitSound() {
        return SoundEvents.BLOCK_AMETHYST_CLUSTER_BREAK;
    }

    public void tick() {
        super.tick();
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

    protected DamageSource getDamageSource(Entity owner) {
        return new DamageSource(
                owner.getWorld().getRegistryManager()
                        .getOrThrow(RegistryKeys.DAMAGE_TYPE)
                        .getEntry(ModDamageTypes.AMETHYST_SHARD.getValue()).get());
    }
}

