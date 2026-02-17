package net.farzad.crystalline.common.item.custom;


import com.mojang.serialization.Codec;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.common.entity.custom.AmethystShardProjectileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.ToolComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.particle.ItemStackParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryEntryLookup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.entry.RegistryEntryList;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

import java.util.List;

public class DividerItem extends SingleSlotAbilityItem {

    public static final List<DividerAbilityTypes> types = List.of(DividerAbilityTypes.CRYSTAL_HEART,DividerAbilityTypes.AMETHYST_RAIN,DividerAbilityTypes.HEART_OF_THE_SEA);


    public DividerItem(Settings settings, float attackDamage, float attackSpeed, double reach) {
        super(applyToolSettings(settings, attackDamage,attackSpeed,reach));
    }

    public static Item.Settings applyToolSettings(Item.Settings settings, float attackDamage, float attackSpeed, double attackRange) {
        RegistryEntryLookup<Block> registryEntryLookup = Registries.createEntryLookup(Registries.BLOCK);
        return settings.component(DataComponentTypes.TOOL, new ToolComponent(List.of(ToolComponent.Rule.ofAlwaysDropping(RegistryEntryList.of(new RegistryEntry[]{Blocks.COBWEB.getRegistryEntry()}), 15.0F), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_INSTANTLY_MINES), Float.MAX_VALUE), ToolComponent.Rule.of(registryEntryLookup.getOrThrow(BlockTags.SWORD_EFFICIENT), 1.5F)), 1.0F, 2, false)).attributeModifiers(createAttributeModifiers(attackDamage, attackSpeed, attackRange));
    }

    public static AttributeModifiersComponent createAttributeModifiers(float attackDamage, float attackSpeed, double attackRange) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.ATTACK_DAMAGE, new EntityAttributeModifier(Item.BASE_ATTACK_DAMAGE_MODIFIER_ID, (attackDamage), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ATTACK_SPEED, new EntityAttributeModifier(Item.BASE_ATTACK_SPEED_MODIFIER_ID, attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(Identifier.of(Crystalline.MOD_ID, "base_attack_range"), attackRange, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .build();
    }

    public static void throwCrystal(PlayerEntity user, float pitch, float yaw, World world) {
        if (!world.isClient) {
            AmethystShardProjectileEntity amethystShardProjectile = new AmethystShardProjectileEntity(world, user);
            amethystShardProjectile.setVelocity(user, pitch, yaw, 0.0F, 1.5F, 1.0F);
            world.spawnEntity(amethystShardProjectile);
        }
    }

    public static int getCharge(ItemStack stack) {
        return stack.getOrDefault(ModDataComponentTypes.DIVIDE_CHARGE, 0);
    }

    public static void setCharge(ItemStack stack, int charge) {
        stack.set(ModDataComponentTypes.DIVIDE_CHARGE, charge);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return getCharge(stack) >= 1;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        int currentCharge = getCharge(stack);
        int maxCharge = 16;
        int maxBarValue = 13;
        return Math.min((currentCharge * maxBarValue) / maxCharge, maxBarValue);
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return MathHelper.hsvToRgb(288F, 38F, 60F);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(MathHelper.hsvToRgb(290 / 360f, 0.38f, 1.0f)));
    }

    @Override
    public void postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity player) {
            boolean isTryingToCrit = player.fallDistance > 0.0F
                    && !player.isOnGround()
                    && !player.isClimbing()
                    && !player.isTouchingWater()
                    && !player.hasStatusEffect(StatusEffects.DARKNESS)
                    && !player.hasVehicle()
                    && target instanceof LivingEntity
                    && !(target instanceof ArmorStandEntity);
            isTryingToCrit = isTryingToCrit && !attacker.isSprinting();

            if (isTryingToCrit && getCharge(stack) < 16 && getCharge(stack) + 2 != 17) {
                setCharge(stack, getCharge(stack) + 2);
            } else {
                setCharge(stack, getCharge(stack) + 1);
            }
        }
        super.postHit(stack, target, attacker);
    }

    @Override
    public boolean onStoppedUsing(ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        if (hasHeart(DividerAbilityTypes.CRYSTAL_HEART,stack)) {
            return true;
        } else {
            return super.onStoppedUsing(stack,world,user,remainingUseTicks);
        }
    }

    public ActionResult use(World world, PlayerEntity user, Hand hand) {
        int maxAttackValue;
        int minAttackValue;
        ItemStack stack = user.getStackInHand(hand);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_HIT, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
        Random random = world.getRandom();
        //Crystalline.LOGGER.info(stack.getOrDefault(ModDataComponentTypes.CURRENT_DIVIDER_HEART,ItemStack.EMPTY).toString());
        for (int i = 0; i < 8; ++i) {
            world.addParticleClient(new ItemStackParticleEffect(ParticleTypes.ITEM, new ItemStack(Blocks.AMETHYST_BLOCK, 1)), user.getX() + random.nextGaussian() / 20f, user.getY() + 1 + random.nextGaussian() / 20f, user.getZ() + random.nextGaussian() / 20f, random.nextGaussian() / 20f, 0.2D + random.nextGaussian() / 20f, random.nextGaussian() / 20f);
        }
        if (hasHeart(DividerAbilityTypes.HEART_OF_THE_SEA,stack) && !user.getItemCooldownManager().isCoolingDown(stack) && getCharge(stack) >= 4) {

            if (getCharge(stack) >= 7) {
                maxAttackValue = 5;
                minAttackValue = -5;
            } else {
                maxAttackValue = 3;
                minAttackValue = -3;
            }

            if (!world.isClient) {
                for (int i = minAttackValue; i <= maxAttackValue; i++) {
                    float d = user.getPitch();
                    float e = user.getYaw();
                    throwCrystal(user, d, e + i * 5, world);
                }
            }

            setCharge(stack, getCharge(stack) - 4);
            user.getItemCooldownManager().set(stack, 20 * 6);
            return ActionResult.CONSUME;

        } else if (hasHeart(DividerAbilityTypes.AMETHYST_RAIN,stack)) {
            return ActionResult.PASS;
        } else if (hasHeart(DividerAbilityTypes.CRYSTAL_HEART,stack)) {
            user.setCurrentHand(hand);
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    @Override
    public int getMaxUseTime(ItemStack stack, LivingEntity user) {
        if (hasHeart(DividerAbilityTypes.AMETHYST_RAIN,stack)) {
            return 72000;
        } else {
            return super.getMaxUseTime(stack, user);
        }
    }


    @Override
    public UseAction getUseAction(ItemStack stack) {
        if (hasHeart(DividerAbilityTypes.CRYSTAL_HEART,stack)) {
            return UseAction.BLOCK;
        } else {
            return super.getUseAction(stack);
        }
    }

    public static enum DividerAbilityTypes implements StringIdentifiable{
        NONE("none"),
        AMETHYST_RAIN("crystal_core"),
        CRYSTAL_HEART("crystal_heart"),
        HEART_OF_THE_SEA("heart_of_the_sea");

        public static final Codec<DividerAbilityTypes> CODEC = StringIdentifiable.createCodec(DividerAbilityTypes::values);

        public String id;
        private DividerAbilityTypes(String type) {
            id = type;
        }

        @Override
        public String asString() {
            return this.id;
        }
    }


}


