package net.farzad.crystalline.common.dataComponents;


import com.mojang.serialization.Codec;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.item.custom.DividerItem;
import net.minecraft.component.ComponentType;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Integer> DIVIDE_CHARGE =
            register("divide_charge", builder -> builder.codec(Codec.INT));

    public static final ComponentType<DividerItem.DividerAbilityTypes> HEART_DATA = register("heart_data",
            dividerHeartComponentBuilder -> dividerHeartComponentBuilder.codec(DividerItem.DividerAbilityTypes.CODEC));

    public static final ComponentType<ItemStack> DIVIDER_HEART = register("divider_heart",
            dividerHeartComponentBuilder -> dividerHeartComponentBuilder.codec(ItemStack.CODEC).packetCodec(ItemStack.PACKET_CODEC));



    private static <T> ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Crystalline.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {}
}