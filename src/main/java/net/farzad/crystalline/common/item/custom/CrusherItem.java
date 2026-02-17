package net.farzad.crystalline.common.item.custom;

import net.farzad.crystalline.common.Crystalline;

import net.farzad.crystalline.common.entity.custom.AmethystShardProjectileEntity;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.MaceItem;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.registry.RegistryKey;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.function.Predicate;

public class CrusherItem extends MaceItem {

    public CrusherItem( Settings settings, float attackDamage, float attackSpeed, double reach) {
        super(settings.attributeModifiers(createAttributeModifiers(attackDamage,attackSpeed,reach)));
    }

    private static double getKnockback(PlayerEntity player, LivingEntity attacked, Vec3d distance) {
        return (3.5 - distance.length()) * 0.699999988079071 * (double) (player.fallDistance > 5.0F ? 2 : 1) * (1.0 - attacked.getAttributeValue(EntityAttributes.KNOCKBACK_RESISTANCE));
    }

    private static Predicate<LivingEntity> getKnockbackPredicate(PlayerEntity player, Entity attacked) {
        return (entity) -> {
            boolean var10000;
            boolean bl;
            boolean bl2;
            boolean bl3;
            label62:
            {
                bl = !entity.isSpectator();
                bl2 = entity != player && entity != attacked;
                bl3 = !player.isTeammate(entity);
                if (entity instanceof TameableEntity tameableEntity) {
                    if (tameableEntity.isTamed() && player.equals(tameableEntity.getOwner())) {
                        var10000 = true;
                        break label62;
                    }
                }

                var10000 = false;
            }

            boolean bl4;
            label55:
            {
                bl4 = !var10000;
                if (entity instanceof ArmorStandEntity armorStandEntity) {
                    if (armorStandEntity.isMarker()) {
                        break label55;
                    }
                }

                var10000 = true;
            }

            boolean bl5 = var10000;
            boolean bl6 = attacked.squaredDistanceTo(entity) <= Math.pow(3.5, 2.0);
            return bl && bl2 && bl3 && bl4 && bl5 && bl6;
        };
    }

    public static boolean hasEnchantment(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        return stack.getEnchantments().getEnchantments().toString().
                contains(enchantment.getValue().toString());
    }

    private static void knockbackNearbyEntities(World world, PlayerEntity player, Entity attacked) {
        world.syncWorldEvent(2013, attacked.getSteppingPos(), 750);
        world.getEntitiesByClass(LivingEntity.class, attacked.getBoundingBox().expand(3.5), getKnockbackPredicate(player, attacked)).forEach((entity) -> {
            Vec3d vec3d = entity.getPos().subtract(attacked.getPos());
            double d = getKnockback(player, entity, vec3d);
            Vec3d vec3d2 = vec3d.normalize().multiply(d);
            if (d > 0.0) {
                entity.addVelocity(vec3d2.x, 0.699999988079071, vec3d2.z);
                if (entity instanceof ServerPlayerEntity serverPlayerEntity) {
                    serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
                }
            }
        });

    }

    public static AttributeModifiersComponent createAttributeModifiers(float attackDamage, float attackSpeed, double attackRange) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (attackDamage), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(Identifier.of(Crystalline.MOD_ID, "base_attack_range"), attackRange, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    @Override
    public float getBonusAttackDamage(Entity target, float baseAttackDamage, DamageSource damageSource) {
        return 0;
    }

    public int getBonusCrystal(LivingEntity player) {
        Entity var5 = player;
        if (var5 instanceof LivingEntity livingEntity) {
            int h = (int) livingEntity.fallDistance;


            if (h >= 5) {
                return 2;
            } else {
                return 0;
            }

        }
        return 0;
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(MathHelper.hsvToRgb(290 / 360f, 0.38f, 1.0f)));
    }

    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof ServerPlayerEntity serverPlayerEntity) {
            if (shouldDealAdditionalDamage(serverPlayerEntity)) {
                ServerWorld serverWorld = (ServerWorld) attacker.getWorld();
                if (!attacker.getWorld().isClient) {
                    for (int yawOffset = -6 * getBonusCrystal(attacker); yawOffset <= 6; yawOffset++) {
                        for (int pitchOffset = -3 * getBonusCrystal(attacker); pitchOffset <= 3; pitchOffset++) {
                            AmethystShardProjectileEntity projectile = new AmethystShardProjectileEntity(attacker.getWorld(), (PlayerEntity) attacker);
                            float pitch = -40 + pitchOffset * 20;
                            float yaw = 160 + yawOffset * 20;
                            projectile.setVelocity(attacker, pitch, yaw, 0.0F, 1.5F, 1.0F);
                            attacker.getWorld().spawnEntity(projectile);
                        }
                    }
                }


                if (serverPlayerEntity.shouldIgnoreFallDamageFromCurrentExplosion() && serverPlayerEntity.currentExplosionImpactPos != null) {
                    if (serverPlayerEntity.currentExplosionImpactPos.y > serverPlayerEntity.getPos().y) {
                        serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
                    }
                } else {
                    serverPlayerEntity.currentExplosionImpactPos = serverPlayerEntity.getPos();
                }

                serverPlayerEntity.setIgnoreFallDamageFromCurrentExplosion(true);
                serverPlayerEntity.setVelocity(serverPlayerEntity.getVelocity().withAxis(Direction.Axis.Y, 0.009999999776482582));
                serverPlayerEntity.networkHandler.sendPacket(new EntityVelocityUpdateS2CPacket(serverPlayerEntity));
                if (target.isOnGround()) {
                    serverPlayerEntity.setSpawnExtraParticlesOnFall(true);
                    SoundEvent soundEvent = serverPlayerEntity.fallDistance > 5.0F ? SoundEvents.ITEM_MACE_SMASH_GROUND_HEAVY : SoundEvents.ITEM_MACE_SMASH_GROUND;
                    serverWorld.playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), soundEvent, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F);
                } else {
                    serverWorld.playSound(null, serverPlayerEntity.getX(), serverPlayerEntity.getY(), serverPlayerEntity.getZ(), SoundEvents.ITEM_MACE_SMASH_AIR, serverPlayerEntity.getSoundCategory(), 1.0F, 1.0F);
                }

                knockbackNearbyEntities(serverWorld, serverPlayerEntity, target);
            }
        }
        super.postHit(stack,target,attacker);
    }

}
