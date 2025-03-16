package net.farzad.crystalline.enchantments;

import com.mojang.serialization.MapCodec;
import net.farzad.crystalline.Crystalline;
import net.farzad.crystalline.enchantments.customItems.AmethystRainEnchantmentEffect;
import net.farzad.crystalline.enchantments.customItems.ShatterSweepEnchantmentEffect;
import net.minecraft.enchantment.effect.EnchantmentEntityEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModEnchantmentEffects {
    public static final MapCodec<? extends EnchantmentEntityEffect> SHATTER_SWEEP =
            registerEntityEffect("shatter_sweep", ShatterSweepEnchantmentEffect.CODEC);
    public static final MapCodec<? extends EnchantmentEntityEffect> AMETHYST_RAIN =
            registerEntityEffect("amethyst_rain", AmethystRainEnchantmentEffect.CODEC);


    private static MapCodec<? extends EnchantmentEntityEffect> registerEntityEffect(String name,
                                                                                    MapCodec<? extends EnchantmentEntityEffect> codec) {
        return Registry.register(Registries.ENCHANTMENT_ENTITY_EFFECT_TYPE, Identifier.of(Crystalline.MOD_ID, name), codec);
    }

    public static void registerEnchantmentEffects() {
        Crystalline.LOGGER.info("Registering Mod Enchantment Effects for " + Crystalline.MOD_ID);
    }
}