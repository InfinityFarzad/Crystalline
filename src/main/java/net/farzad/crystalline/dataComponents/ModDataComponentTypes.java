package net.farzad.crystalline.dataComponents;


import com.mojang.serialization.Codec;
import net.farzad.crystalline.Crystalline;
import net.minecraft.component.ComponentType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.util.function.UnaryOperator;

public class ModDataComponentTypes {
    public static final ComponentType<Integer> DIVIDE_CHARGE =
            register("divide_charge", builder -> builder.codec(Codec.INT));


    private static <T>ComponentType<T> register(String name, UnaryOperator<ComponentType.Builder<T>> builderOperator) {
        return Registry.register(Registries.DATA_COMPONENT_TYPE, Identifier.of(Crystalline.MOD_ID, name),
                builderOperator.apply(ComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {
        Crystalline.LOGGER.info("Registering Data Component Types for " + Crystalline.MOD_ID);
    }
}