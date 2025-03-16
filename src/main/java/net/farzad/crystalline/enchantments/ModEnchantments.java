package net.farzad.crystalline.enchantments;


import net.farzad.crystalline.Crystalline;
import net.farzad.crystalline.enchantments.customItems.AmethystRainEnchantmentEffect;
import net.farzad.crystalline.enchantments.customItems.ShatterSweepEnchantmentEffect;
import net.farzad.crystalline.util.ModTags;
import net.minecraft.component.EnchantmentEffectComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.effect.EnchantmentEffectTarget;
import net.minecraft.registry.Registerable;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

public class ModEnchantments {
    public static final RegistryKey<Enchantment> SHATTER_SWEEP =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Crystalline.MOD_ID, "shatter_sweep"));
    public static final RegistryKey<Enchantment> AMETHYST_RAIN =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, Identifier.of(Crystalline.MOD_ID, "amethyst_rain"));

    public static void bootstrap(Registerable<Enchantment> registerable) {
        var enchantments = registerable.getRegistryLookup(RegistryKeys.ENCHANTMENT);
        var items = registerable.getRegistryLookup(RegistryKeys.ITEM);

        register(registerable, AMETHYST_RAIN, Enchantment.builder(Enchantment.definition(
                        items.getOrThrow(ModTags.Items.SHATTER_ENCHANTABLE),

                        5,
                        1,
                        Enchantment.leveledCost(5, 7),
                        Enchantment.leveledCost(25, 9),
                        2,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(ModTags.Enchantments.AMETHYST_RAIN_SET))

                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM,
                        new AmethystRainEnchantmentEffect()));

        register(registerable, SHATTER_SWEEP, Enchantment.builder(Enchantment.definition(
                        items.getOrThrow(ModTags.Items.SHATTER_ENCHANTABLE),

                        5,
                        1,
                        Enchantment.leveledCost(5, 7),
                        Enchantment.leveledCost(25, 9),
                        2,
                        AttributeModifierSlot.MAINHAND))
                .exclusiveSet(enchantments.getOrThrow(ModTags.Enchantments.SHATTER_SWEEP_SET))
                .addEffect(EnchantmentEffectComponentTypes.POST_ATTACK,
                        EnchantmentEffectTarget.ATTACKER, EnchantmentEffectTarget.VICTIM,
                        new ShatterSweepEnchantmentEffect()));
    }




    private static void register(Registerable<Enchantment> registry, RegistryKey<Enchantment> key, Enchantment.Builder builder) {
        registry.register(key, builder.build(key.getValue()));
    }
}