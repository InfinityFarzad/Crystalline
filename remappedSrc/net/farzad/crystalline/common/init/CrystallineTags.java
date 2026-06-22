package net.farzad.crystalline.common.init;


import net.farzad.crystalline.common.Crystalline;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.enchantment.Enchantment;

public class CrystallineTags {


    public static class Items {
        public static final TagKey<Item> DIVIDER_HEART = TagKey.create(Registries.ITEM,Crystalline.id("divider_heart"));

        private static TagKey<Item> createTag(String name) {
            return TagKey.create(Registries.ITEM, Crystalline.id(name));
        }
    }

    public static class Enchantments {
        public static final TagKey<Enchantment> SHATTER_SWEEP_SET = TagKey.create(Registries.ENCHANTMENT, Crystalline.id("exclusive_set/shatter_sweep_set"));
        public static final TagKey<Enchantment> AMETHYST_RAIN_SET = TagKey.create(Registries.ENCHANTMENT, Crystalline.id("exclusive_set/amethyst_rain_set"));


    }
}