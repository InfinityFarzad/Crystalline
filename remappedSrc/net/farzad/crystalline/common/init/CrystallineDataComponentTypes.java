package net.farzad.crystalline.common.init;


import com.mojang.serialization.Codec;
import net.farzad.crystalline.common.Crystalline;
import net.farzad.crystalline.common.item.DividerItem;
import net.minecraft.core.Registry;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.ItemStack;
import java.util.function.UnaryOperator;

public class CrystallineDataComponentTypes {
    public static final DataComponentType<Integer> DIVIDE_CHARGE =
            register("divide_charge", builder -> builder.persistent(Codec.INT));

    public static final DataComponentType<DividerItem.DividerAbilityTypes> HEART_DATA = register("heart_data",
            dividerHeartComponentBuilder -> dividerHeartComponentBuilder.persistent(DividerItem.DividerAbilityTypes.CODEC));

    public static final DataComponentType<ItemStack> DIVIDER_HEART = register("divider_heart",
            dividerHeartComponentBuilder -> dividerHeartComponentBuilder.persistent(ItemStack.CODEC).networkSynchronized(ItemStack.STREAM_CODEC));



    private static <T> DataComponentType<T> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return Registry.register(BuiltInRegistries.DATA_COMPONENT_TYPE, Crystalline.id(name),
                builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void registerDataComponentTypes() {}
}