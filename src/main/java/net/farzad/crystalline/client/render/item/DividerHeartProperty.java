package net.farzad.crystalline.client.render.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.farzad.crystalline.common.init.CrystallineDataComponentTypes;
import net.farzad.crystalline.common.component.CustomHeartData;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DividerHeartProperty implements SelectItemModelProperty<String> {

    public static final Type<DividerHeartProperty, String> TYPE = Type.create(MapCodec.unit(new DividerHeartProperty()), Codec.STRING);
    public static final MapCodec<DividerHeartProperty> CODEC = MapCodec.unit(DividerHeartProperty::new);


    @Override
    public @Nullable String get(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        return getVal(stack);
    }

    private String getVal(ItemStack stack) {
        ItemStack stack1 = stack.getOrDefault(CrystallineDataComponentTypes.DIVIDER_HEART, ItemStack.EMPTY);

        return stack1.getOrDefault(CrystallineDataComponentTypes.HEART_DATA, CustomHeartData.NONE).id();

    }

    @Override
    public Codec<String> valueCodec() {
        return Codec.STRING;
    }

    @Override
    public Type<? extends SelectItemModelProperty<String>, String> type() {
        return TYPE;
    }
}
