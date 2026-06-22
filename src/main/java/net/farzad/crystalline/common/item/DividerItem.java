package net.farzad.crystalline.common.item;

import net.akws.chiseled_lib.client.util.RenderUtil;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.entity.AmethystShardProjectileEntity;
import net.farzad.crystalline.common.entity.CrystalCoreEntity;
import net.farzad.crystalline.common.init.CrystallineDataComponentTypes;
import net.farzad.crystalline.common.init.CrystallineEntities;
import net.farzad.crystalline.common.component.CustomHeartData;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.HolderSet;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUseAnimation;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Objects;

public class DividerItem extends SingleSlotAbilityItem {

    public DividerItem(Properties settings, float attackDamage, float attackSpeed, double reach) {
        super(applyToolSettings(settings, attackDamage, attackSpeed, reach));
    }

    public static Properties applyToolSettings(Properties settings, float attackDamage, float attackSpeed, double attackRange) {
        HolderGetter<Block> registryEntryLookup = BuiltInRegistries.acquireBootstrapRegistrationLookup(BuiltInRegistries.BLOCK);
        return settings
                .component(DataComponents.TOOL, new Tool(List.of(Tool.Rule.minesAndDrops(HolderSet.direct(Blocks.COBWEB.builtInRegistryHolder()), 15.0F), Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE), Tool.Rule.overrideSpeed(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false))
                .attributes(createAttributeModifiers(attackDamage, attackSpeed, attackRange));
    }

    public static ItemAttributeModifiers createAttributeModifiers(float attackDamage, float attackSpeed, double attackRange) {
        return ItemAttributeModifiers.builder()
                .add(Attributes.ATTACK_DAMAGE, new AttributeModifier(Item.BASE_ATTACK_DAMAGE_ID, (attackDamage), AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ATTACK_SPEED, new AttributeModifier(Item.BASE_ATTACK_SPEED_ID, attackSpeed, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .add(Attributes.ENTITY_INTERACTION_RANGE, new AttributeModifier(Crystalline.id("base_attack_range"), attackRange, AttributeModifier.Operation.ADD_VALUE), EquipmentSlotGroup.MAINHAND)
                .build();
    }

    public static void throwCrystal(Player user, float pitch, float yaw, Level world, boolean ignoreIframes, boolean inheritVelocity, Vec3 pos) {
        if (world instanceof ServerLevel serverWorld) {
            AmethystShardProjectileEntity amethystShardProjectile = new AmethystShardProjectileEntity(world, user, ignoreIframes, pos);
            amethystShardProjectile.shootFromRotation(user, pitch, yaw, 0.0F, 1.5F, 1.0F,inheritVelocity);
            serverWorld.addFreshEntity(amethystShardProjectile);
        }
    }

    public static int getCharge(ItemStack stack) {
        return stack.getOrDefault(CrystallineDataComponentTypes.DIVIDE_CHARGE, 0);
    }

    public static void setCharge(ItemStack stack, int charge) {
        stack.set(CrystallineDataComponentTypes.DIVIDE_CHARGE, charge);
    }

    @Override
    public boolean isBarVisible(ItemStack stack) {
        return getCharge(stack) >= 1;
    }

    @Override
    public int getBarWidth(ItemStack stack) {
        int currentCharge = getCharge(stack);
        int maxBarValue = 13;
        return Math.min((currentCharge * maxBarValue) / 16, maxBarValue);
    }

    @Override
    public int getBarColor(ItemStack stack) {
        return Mth.hsvToRgb(288F, 38F, 60F);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId()).setStyle(Style.EMPTY.withColor(Mth.hsvToRgb(290 / 360f, 0.38f, 1.0f)));
    }

    @Override
    public void hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof Player player) {
            boolean isTryingToCrit = player.fallDistance > 0.0F
                    && !player.onGround()
                    && !player.onClimbable()
                    && !player.isInWater()
                    && !player.hasEffect(MobEffects.DARKNESS)
                    && !player.isPassenger()
                    && target instanceof LivingEntity
                    && !(target instanceof ArmorStand);
            isTryingToCrit = isTryingToCrit && !attacker.isSprinting();

            if (isTryingToCrit && getCharge(stack) < 16 && getCharge(stack) + 2 != 17) {
                setCharge(stack, getCharge(stack) + 2);
            } else {
                setCharge(stack, getCharge(stack) + 1);
            }
        }
        super.hurtEnemy(stack, target, attacker);
    }

    @Override
    public boolean releaseUsing(ItemStack stack, Level world, LivingEntity user, int remainingUseTicks) {
        if (hasHeart("crystal_heart", stack)) {
            if (user instanceof Player player && !player.isUsingItem()) {
                player.getCooldowns().addCooldown(stack, 20 * 4);
            }
            return true;
        } else {
            return super.releaseUsing(stack, world, user, remainingUseTicks);
        }
    }

    public InteractionResult use(Level world, Player user, InteractionHand hand) {
        ItemStack stack = user.getItemInHand(hand);
        float pitch = user.getXRot();
        float yaw = user.getYRot();
        if (hasHeart("none",stack)) {
            if (getCharge(stack) >= 1) {
                throwCrystal(user, pitch, yaw, world, false, true, new Vec3(user.getX(), user.getY() + 1.5, user.getZ()));
                applyUseEffects(user,stack,world);
            }
        } else {
            if (hasHeart("heart_of_the_sea", stack) && getCharge(stack) >= getHeart(stack).minimumCharge()) {
                if (!world.isClientSide()) {
                    for (int i = -4; i < 4; i++) {
                        throwCrystal(user, pitch, yaw + i * 4, world, true, true, new Vec3(user.getX(), user.getY() + 1.5, user.getZ()));
                    }
                }
                applyUseEffects(user,stack,world);
                return InteractionResult.PASS;
            } else if (hasHeart("crystal_core", stack) && getCharge(stack) >= getHeart(stack).minimumCharge()) {
                if (world instanceof ServerLevel serverWorld) {
                    CrystalCoreEntity entity = new CrystalCoreEntity(CrystallineEntities.CRYSTAL_CORE_ENTITY, user, user.position());
                    entity.setDeltaMovement(user.getLookAngle().add(entity.getKnownMovement()));
                    serverWorld.addFreshEntity(entity);
                }
                applyUseEffects(user,stack,world);
                return InteractionResult.PASS;
            } else if (hasHeart("crystal_heart", stack) && getCharge(stack) >= 4) {
                user.startUsingItem(hand);
                return InteractionResult.PASS;
            }
        }
        return super.use(world, user, hand);
    }

    private void applyUseEffects(Player user, ItemStack stack, Level level) {
        CustomHeartData data = getHeart(stack);
        double offsetFunny = level.getRandom().nextGaussian() / 20.0;
        int minimumCharge = Objects.equals(data.id(), "none") ? 1 : data.minimumCharge();
        if (!user.getCooldowns().isOnCooldown(stack)) {
            user.getCooldowns().addCooldown(stack, Objects.equals(data.id(), "none") ? 1 : data.cooldown());
        }
        if ((getCharge(stack) - minimumCharge) >= 0) {
            setCharge(stack,getCharge(stack) - minimumCharge);
            level.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        } else {
            level.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.TRIDENT_HIT, SoundSource.PLAYERS, 0.5F, 0.4F / (level.getRandom().nextFloat() * 0.4F + 0.8F));
        }

        RenderUtil.spawnParticle(level, new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK, 1)), false, user.getX(), user.getY(), user.getZ(), offsetFunny, offsetFunny, offsetFunny);
    }

    @Override
    public ItemUseAnimation getUseAnimation(ItemStack stack) {
        return ItemUseAnimation.BOW;
    }

    @Override
    public int getUseDuration(ItemStack itemStack, LivingEntity livingEntity) {
        return 78000;
    }

}


