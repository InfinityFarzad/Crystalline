package net.farzad.crystalline.client.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.farzad.crystalline.common.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.common.item.custom.DividerItem;
import net.minecraft.client.render.item.property.bool.BooleanProperty;
import net.minecraft.client.render.item.property.select.ChargeTypeProperty;
import net.minecraft.client.render.item.property.select.SelectProperty;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DividerHeartProperty implements SelectProperty<DividerItem.DividerAbilityTypes> {

    public static final Codec<DividerItem.DividerAbilityTypes> VALUE_CODEC = DividerItem.DividerAbilityTypes.CODEC;
    public static final SelectProperty.Type<DividerHeartProperty, DividerItem.DividerAbilityTypes> TYPE = Type.create(MapCodec.unit(new DividerHeartProperty()), VALUE_CODEC);
    public static MapCodec<DividerHeartProperty> CODEC = MapCodec.unit(DividerHeartProperty::new);


    @Override
    public @Nullable DividerItem.DividerAbilityTypes getValue(ItemStack stack, @Nullable ClientWorld world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        return getActualValueLmao(stack);
    }

    private DividerItem.DividerAbilityTypes getActualValueLmao(ItemStack stack) {
        ItemStack stack1 = stack.getOrDefault(ModDataComponentTypes.DIVIDER_HEART,ItemStack.EMPTY);

        DividerItem.DividerAbilityTypes abilityType = stack1.getOrDefault(ModDataComponentTypes.HEART_DATA, DividerItem.DividerAbilityTypes.NONE);
        if (stack1 == ItemStack.EMPTY || !DividerItem.types.contains(abilityType)) {
            return DividerItem.DividerAbilityTypes.NONE;
        } else {
            return abilityType;
        }

    }

    @Override
    public Codec<DividerItem.DividerAbilityTypes> valueCodec() {
        return VALUE_CODEC;
    }

    @Override
    public Type<? extends SelectProperty<DividerItem.DividerAbilityTypes>, DividerItem.DividerAbilityTypes> getType() {
        return TYPE;
    }
}
