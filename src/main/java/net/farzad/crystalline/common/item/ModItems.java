package net.farzad.crystalline.common.item;

import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.common.item.custom.CrusherItem;
import net.farzad.crystalline.common.item.custom.DividerHeartItem;
import net.farzad.crystalline.common.item.custom.DividerItem;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

import java.util.function.Function;

public class ModItems {

    public static final Item DIVIDER = register("divider", settings -> new DividerItem(settings, 8, -2.7f, 0.3),new Item.Settings()
            .maxCount(1).rarity(Rarity.RARE)
            .repairable(Items.AMETHYST_SHARD).enchantable(15)
            .component(ModDataComponentTypes.DIVIDE_CHARGE,0));

    public static final Item CRUSHER = register("crusher", settings -> new CrusherItem(settings, 7, -3.2f, 0.2),new Item.Settings().maxCount(1).rarity(Rarity.RARE).repairable(Items.AMETHYST_BLOCK).rarity(Rarity.RARE).enchantable(15));

    public static final Item CRYSTAL_HEART = register("crystal_heart", settings -> new DividerHeartItem(settings,"crystal_heart"), new Item.Settings().component(ModDataComponentTypes.HEART_DATA, DividerItem.DividerAbilityTypes.CRYSTAL_HEART));
    public static final Item CRYSTAL_CORE = register("crystal_core", settings -> new DividerHeartItem(settings,"crystal_core"), new Item.Settings().component(ModDataComponentTypes.HEART_DATA, DividerItem.DividerAbilityTypes.AMETHYST_RAIN));


    public static Item register(String name, Function<Item.Settings, Item> itemFactory, Item.Settings settings) {
        RegistryKey<Item> itemKey = RegistryKey.of(RegistryKeys.ITEM, Identifier.of(Crystalline.MOD_ID, name));
        Item item = itemFactory.apply(settings.registryKey(itemKey).component(DataComponentTypes.TOOLTIP_STYLE,Identifier.ofVanilla("crystal")));
        Registry.register(Registries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries -> {
            entries.add(DIVIDER);
            entries.add(CRUSHER);

        });
        DefaultItemComponentEvents.MODIFY.register(context -> {
            context.modify(Items.HEART_OF_THE_SEA,wa -> wa.add(ModDataComponentTypes.HEART_DATA, DividerItem.DividerAbilityTypes.HEART_OF_THE_SEA));
        });
    }
}