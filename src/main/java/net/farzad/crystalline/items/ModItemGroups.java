package net.farzad.crystalline.items;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.farzad.crystalline.Crystalline;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CRYSTALINE_ITEMGROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(Crystalline.MOD_ID, "crystaline_group"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.THE_DIVIDER))
                    .displayName(Text.translatable("itemgroup.crystaline_group"))
                    .entries((displayContext, entries) -> {
                        entries.add(ModItems.THE_DIVIDER);



                    }).build());






    public static void registerItemGroups() {
        Crystalline.LOGGER.info("Registering Item Groups for " + Crystalline.MOD_ID);
    }
}