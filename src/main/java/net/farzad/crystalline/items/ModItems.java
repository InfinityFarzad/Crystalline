package net.farzad.crystalline.items;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.farzad.crystalline.Crystalline;
import net.farzad.crystalline.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.items.customItems.Weapons.CrusherItem;
import net.farzad.crystalline.items.customItems.Weapons.DividerItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ToolMaterials;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item THE_DIVIDER = registerItem("the_divider", new DividerItem(ToolMaterials.NETHERITE,new Item.Settings().attributeModifiers(DividerItem.createAttributeModifiers(ToolMaterials.NETHERITE,8,-2.7F,0.3)).component(ModDataComponentTypes.DIVIDE_CHARGE,0)));
    public static final Item THE_CRUSHER = registerItem("the_crusher", new CrusherItem(ToolMaterials.NETHERITE,new Item.Settings().attributeModifiers(CrusherItem.createAttributeModifiers(ToolMaterials.NETHERITE,7,-3.2F,0.2)).maxCount(1)));

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(Crystalline.MOD_ID, name), item);
    }

    public static void registerModItems() {
        Crystalline.LOGGER.info("Registering Mod Items for " + Crystalline.MOD_ID);
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(THE_DIVIDER);
            entries.add(THE_CRUSHER);

        });
    }
}