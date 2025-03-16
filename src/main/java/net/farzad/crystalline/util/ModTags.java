package net.farzad.crystalline.util;


import net.farzad.crystalline.Crystalline;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;

public class ModTags {


    public static class Items {
        public static final TagKey<Item> SHATTER_ENCHANTABLE = createTag("shatter_enchantable");

        private static TagKey<Item> createTag(String name) {
            return TagKey.of(RegistryKeys.ITEM, Identifier.of(Crystalline.MOD_ID, name));
        }
    }

    public static class Enchantments {
        public static final TagKey<Enchantment> SHATTER_SWEEP_SET = TagKey.of(RegistryKeys.ENCHANTMENT, Crystalline.id("exclusive_set/shatter_sweep_set"));
        public static final TagKey<Enchantment> AMETHYST_RAIN_SET = TagKey.of(RegistryKeys.ENCHANTMENT, Crystalline.id("exclusive_set/amethyst_rain_set"));



    }
}