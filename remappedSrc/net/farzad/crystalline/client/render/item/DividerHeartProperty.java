package net.farzad.crystalline.client.render.item;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.farzad.crystalline.common.init.CrystallineDataComponentTypes;
import net.farzad.crystalline.common.item.DividerItem;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.select.SelectItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class DividerHeartProperty implements SelectItemModelProperty<DividerItem.DividerAbilityTypes> {

    public static final Codec<DividerItem.DividerAbilityTypes> VALUE_CODEC = DividerItem.DividerAbilityTypes.CODEC;
    public static final Type<DividerHeartProperty, DividerItem.DividerAbilityTypes> TYPE = Type.create(MapCodec.unit(new DividerHeartProperty()), VALUE_CODEC);
    public static MapCodec<DividerHeartProperty> CODEC = MapCodec.unit(DividerHeartProperty::new);


    @Override
    public @Nullable DividerItem.DividerAbilityTypes get(ItemStack stack, @Nullable ClientLevel world, @Nullable LivingEntity user, int seed, ItemDisplayContext displayContext) {
        return getActualValueLmao(stack);
    }

    private DividerItem.DividerAbilityTypes getActualValueLmao(ItemStack stack) {
        ItemStack stack1 = stack.getOrDefault(CrystallineDataComponentTypes.DIVIDER_HEART,ItemStack.EMPTY);

        DividerItem.DividerAbilityTypes abilityType = stack1.getOrDefault(CrystallineDataComponentTypes.HEART_DATA, DividerItem.DividerAbilityTypes.NONE);
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
    public Type<? extends SelectItemModelProperty<DividerItem.DividerAbilityTypes>, DividerItem.DividerAbilityTypes> type() {
        return TYPE;
    }
}
