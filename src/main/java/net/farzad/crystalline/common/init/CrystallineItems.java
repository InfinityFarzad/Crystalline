package net.farzad.crystalline.common.init;

import net.akws.chiseled_lib.common.item.component.ItemHighlightComponent;
import net.akws.chiseled_lib.common.registries.ChiseledLibComponents;
import net.fabricmc.fabric.api.item.v1.DefaultItemComponentEvents;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.item.DividerHeartItem;
import net.farzad.crystalline.common.item.DividerItem;
import net.farzad.crystalline.common.component.CustomHeartData;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.*;

import java.util.function.Function;

public class CrystallineItems {

    public static final CreativeModeTab CRYSTALLINE_ITEMGROUP = Registry.register(BuiltInRegistries.CREATIVE_MODE_TAB,
            Crystalline.id("crystalline_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(CrystallineItems.DIVIDER))
                    .title(Component.translatable("itemgroup.crystalline_group"))
                    .displayItems((displayContext, entries) -> {
                        entries.accept(CrystallineItems.DIVIDER);
                        entries.accept(CrystallineItems.CRYSTAL_CORE);
                        entries.accept(CrystallineItems.CRYSTAL_HEART);
                        entries.accept(Items.HEART_OF_THE_SEA);
                        entries.accept(CrystallineBlocks.AMETHYST_LAMP);
                        entries.accept(CrystallineBlocks.AMETHYST_PILLAR);
                        entries.accept(CrystallineBlocks.AMETHYST_BRICKS);
                        entries.accept(CrystallineBlocks.AMETHYST_BRICK_STAIRS);
                        entries.accept(CrystallineBlocks.AMETHYST_BRICK_SLAB);
                    }).build());

    public static final Item DIVIDER = register("divider", settings -> new DividerItem(settings, 8, -2.7f, 0.3), new Item.Properties()
            .stacksTo(1).rarity(Rarity.RARE)
            .repairable(Items.AMETHYST_SHARD).enchantable(15)
            .component(CrystallineDataComponentTypes.DIVIDE_CHARGE, 0));

    public static final Item CRUSHER = register("crusher", Item::new, new Item.Properties().stacksTo(1).rarity(Rarity.RARE).repairable(Items.AMETHYST_BLOCK).rarity(Rarity.RARE).enchantable(15));

    public static final Item CRYSTAL_HEART = register("crystal_heart", DividerHeartItem::new, new Item.Properties().component(CrystallineDataComponentTypes.HEART_DATA, new CustomHeartData("crystal_heart",0,0)));
    public static final Item CRYSTAL_CORE = register("crystal_core", DividerHeartItem::new, new Item.Properties().component(CrystallineDataComponentTypes.HEART_DATA, new CustomHeartData("crystal_core",16,400)));

    public static <T extends Item> T register(String name, Function<Item.Properties, T> itemFactory, Item.Properties settings) {
        ResourceKey<Item> itemKey = ResourceKey.create(Registries.ITEM, Crystalline.id(name));
        T item = itemFactory.apply(settings.setId(itemKey).component(DataComponents.TOOLTIP_STYLE, Crystalline.id("crystal")).component(ChiseledLibComponents.ITEM_HIGHLIGHT, new ItemHighlightComponent(11767539, true)));
        Registry.register(BuiltInRegistries.ITEM, itemKey, item);
        return item;
    }

    public static void init() {
        ItemGroupEvents.modifyEntriesEvent(CreativeModeTabs.COMBAT).register(entries -> {
            entries.accept(DIVIDER);
            entries.accept(CRUSHER);
        });
        DefaultItemComponentEvents.MODIFY.register(context -> context.modify(Items.HEART_OF_THE_SEA, wa -> wa.set(CrystallineDataComponentTypes.HEART_DATA, new CustomHeartData("heart_of_the_sea",4,15)).set(ChiseledLibComponents.ITEM_HIGHLIGHT, new ItemHighlightComponent(4052180, true))));
    }
}