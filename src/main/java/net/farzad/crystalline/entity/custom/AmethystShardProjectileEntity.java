package net.farzad.crystalline.entity.custom;

import net.farzad.crystalline.entity.ModEntities;
import net.farzad.crystalline.util.ModDamageTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.world.World;

public class AmethystShardProjectileEntity extends PersistentProjectileEntity {

    public int ticksUntilRemoval = -1;

    @Override
    protected double getGravity() {
        return 0.08;
    }

    public AmethystShardProjectileEntity(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public AmethystShardProjectileEntity(World world, PlayerEntity player) {
        super(ModEntities.AMETHYST_SHARD_PROJECTILE_ENTITY, player, world, new ItemStack(Items.AMETHYST_SHARD), player.getMainHandStack());
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

    public boolean isGrounded() {
        return inGround;
    }



    public void tick() {
        super.tick();
        if (inGround) {

                for (int i = 0; i < 8; ++i) {
                    getWorld().addParticle(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(getBreakItemParticle(), 1)), getX() + random.nextGaussian() / 20f, getY() + random.nextGaussian() / 20f, getZ() + random.nextGaussian() / 20f, random.nextGaussian() / 20f, 0.2D + random.nextGaussian() / 20f, random.nextGaussian() / 20f);
                }

                remove(RemovalReason.DISCARDED);

        }

    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        Entity entity = entityHitResult.getEntity();
        Entity owner = getOwner();
        DamageSource damageSource = getDamageSource(owner);
        if (owner instanceof LivingEntity living) {
            living.onAttacking(entity);
        }
        if (entity.getType() != EntityType.ENDERMAN && entity != owner) {
            if (isOnFire()) {
                entity.setOnFireFor(5);
            }
            if (entity instanceof LivingEntity living) {
                if (getWorld() instanceof ServerWorld serverWorld) {
                    entity.damage(damageSource, (float) 6);
                }
                onHit(living);
                if (living != owner && living instanceof PlayerEntity && owner instanceof ServerPlayerEntity serverPlayer && !isSilent()) {
                    serverPlayer.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER, GameStateChangeS2CPacket.DEMO_OPEN_SCREEN));
                }
            }
        }
        discard();
        getWorld().playSound(null, getBlockPos(), SoundEvents.BLOCK_AMETHYST_BLOCK_BREAK, SoundCategory.NEUTRAL, 1.0f, 1.5f);
    }

    protected Item getBreakItemParticle() {
        return Items.AMETHYST_BLOCK;
    }

    protected DamageSource getDamageSource(Entity owner) {
        return ModDamageTypes.amethystShard(this, owner != null ? owner : this);
    }
}

