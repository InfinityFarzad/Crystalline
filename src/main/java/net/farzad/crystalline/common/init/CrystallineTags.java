package net.farzad.crystalline.common.init;


import net.farzad.crystalline.common.Crystalline;
import net.minecraft.core.registries.Registries;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

public class CrystallineTags {

    public static class Items {
        public static final TagKey<Item> DIVIDER_HEART = TagKey.create(Registries.ITEM, Crystalline.id("divider_heart"));
    }

}