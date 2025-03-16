package net.farzad.crystalline.items.customItems.Weapons;


import net.farzad.crystalline.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.enchantments.ModEnchantments;
import net.farzad.crystalline.entity.custom.AmethystShardProjectileEntity;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stats;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class DividerItem extends SwordItem {
    public static final Identifier BASE_ATTACK_RANGE_MODIFIER_ID = Identifier.of("base_attack_range");

    public DividerItem(ToolMaterial toolMaterial, Settings settings) {


        super(toolMaterial, settings);
    }


    private int getCharge(ItemStack stack) {
        if (stack.contains(ModDataComponentTypes.DIVIDE_CHARGE)) {
            return stack.get(ModDataComponentTypes.DIVIDE_CHARGE);
        } else {
            return 0;
        }
    }

    private void setCharge(ItemStack stack, int charge) {
        stack.set(ModDataComponentTypes.DIVIDE_CHARGE, charge);
    }

    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        if (getCharge(stack) >= 1) {
            return true;
        } else {
            return false;
        }

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
        return Text.translatable(this.getTranslationKey(stack)).setStyle(Style.EMPTY.withColor(MathHelper.hsvToRgb(290 / 360f, 0.38f, 1.0f)));
    }


    public static boolean hasEnchantment(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        return stack.getEnchantments().getEnchantments().toString().
                contains(enchantment.getValue().toString());
    }

    public static int getLevel(ItemStack stack, RegistryKey<Enchantment> enchantment) {
        for (RegistryEntry<Enchantment> enchantments : stack.getEnchantments().getEnchantments()) {
            if (enchantments.toString().contains(enchantment.getValue().toString())) {
                return stack.getEnchantments().getLevel(enchantments);
            }
        }
        return 0;
    }

    public static AttributeModifiersComponent createAttributeModifiers(ToolMaterial material, int baseAttackDamage, float attackSpeed, double attackrange) {
        return AttributeModifiersComponent.builder()
                .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, new EntityAttributeModifier(BASE_ATTACK_DAMAGE_MODIFIER_ID, (double) ((float) baseAttackDamage), EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.GENERIC_ATTACK_SPEED, new EntityAttributeModifier(BASE_ATTACK_SPEED_MODIFIER_ID, (double) attackSpeed, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND)
                .add(EntityAttributes.PLAYER_ENTITY_INTERACTION_RANGE, new EntityAttributeModifier(BASE_ATTACK_RANGE_MODIFIER_ID, (double) attackrange, EntityAttributeModifier.Operation.ADD_VALUE), AttributeModifierSlot.MAINHAND).build();
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        boolean isTryingToCrit =  attacker.fallDistance > 0.0F
                && !attacker.isOnGround()
                && !attacker.isClimbing()
                && !attacker.isTouchingWater()
                && !attacker.hasStatusEffect(StatusEffects.DARKNESS)
                && !attacker.hasVehicle()
                && target instanceof LivingEntity;
        isTryingToCrit = isTryingToCrit && !attacker.isSprinting();





        if (isTryingToCrit && getCharge(stack) < 16 && getCharge(stack) + 2 != 17){
            setCharge(stack,getCharge(stack) + 2);
        } else {
            setCharge(stack, getCharge(stack) + 1);
        }
        return super.postHit(stack, target, attacker);
    }


    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        int maxAttackValue;
        int minAttackValue;
        ItemStack itemStack = user.getStackInHand(hand);
        world.playSound(user, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_HIT, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));

        if (hasEnchantment(itemStack, ModEnchantments.SHATTER_SWEEP) && !user.getItemCooldownManager().isCoolingDown(this) && getCharge(itemStack) >= 4) {
            if (getCharge(itemStack) >= 7) {
                maxAttackValue = 5;
                minAttackValue = -5;
            } else {
                maxAttackValue = 3;
                minAttackValue = -3;
            }

            if (!world.isClient) {
                for (int i = minAttackValue; i <= maxAttackValue; i++) {


                    AmethystShardProjectileEntity eggEntity = new AmethystShardProjectileEntity(world, user);
                    float d = user.getPitch();
                    float e = user.getYaw();
                    eggEntity.setVelocity(user, d, e + i * 5, 0.0F, 1.5F, 1.0F);
                    world.spawnEntity(eggEntity);


                }
                setCharge(itemStack, getCharge(itemStack) - 4);
                user.getItemCooldownManager().set(this, 120);
            }

            setCharge(itemStack, getCharge(itemStack) - 4);
        } else if (hasEnchantment(itemStack, ModEnchantments.AMETHYST_RAIN) && getCharge(itemStack) >= 16) {
            if (getCharge(itemStack) >= 16 && user.isSneaking()) {
                if (!world.isClient) {
                    for (int yawOffset = -6; yawOffset <= 6; yawOffset++) {
                        for (int pitchOffset = -3; pitchOffset <= 3; pitchOffset++) { // Adjust vertical spread
                            AmethystShardProjectileEntity projectile = new AmethystShardProjectileEntity(world, user);

                            float pitch = user.getPitch() + pitchOffset * 20; // Vertical angle variation
                            float yaw = user.getYaw() + yawOffset * 20; // Horizontal angle variation

                            projectile.setVelocity(user, pitch, yaw, 0.0F, 1.5F, 1.0F);
                            world.spawnEntity(projectile);
                        }
                    }
                }
                  user.getItemCooldownManager().set(this, 500);
                  setCharge(itemStack,0);
            }}

        else if (getCharge(itemStack) >= 2) {
            world.playSound((PlayerEntity) null, user.getX(), user.getY(), user.getZ(), SoundEvents.ITEM_TRIDENT_HIT, SoundCategory.PLAYERS, 0.5F, 0.4F / (world.getRandom().nextFloat() * 0.4F + 0.8F));
            if (!world.isClient) {


                AmethystShardProjectileEntity eggEntity = new AmethystShardProjectileEntity(world, user);
                float d = user.getPitch();
                float e = user.getYaw();
                world.spawnEntity(eggEntity);
                eggEntity.setVelocity(user, user.getPitch(), user.getYaw(), 0.0F, 1.5F, 1.0F);


                }
                user.getItemCooldownManager().set(this, 30);
                setCharge(itemStack, getCharge(itemStack) - 2);}




            user.incrementStat(Stats.USED.getOrCreateStat(this));


        return TypedActionResult.success(itemStack, world.isClient());
    }




/*    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (attacker instanceof PlayerEntity)
            spawnSweepAttackParticles((PlayerEntity) attacker);
        return true;
    }





    public void spawnSweepAttackParticles(PlayerEntity player) {
        double d = (-MathHelper.sin(player.getYaw() * (float) (Math.PI / 180.0)));
        double e = MathHelper.cos(player.getYaw() * (float) (Math.PI / 180.0));
        if (player.getWorld() instanceof ServerWorld) {
            ((ServerWorld)player.getWorld()).spawnParticles(ModParticles.SWEEP_OF_DARKNESS_PARTICLE, player.getX() + d, player.getBodyY(0.5), player.getZ() + e, 0, d, 0.0, e, 0.0);

        }
    }*/

}


