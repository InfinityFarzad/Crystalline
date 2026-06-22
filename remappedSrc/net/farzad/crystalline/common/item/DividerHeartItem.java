package net.farzad.crystalline.common.item;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.util.Mth;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class DividerHeartItem extends Item {

    public DividerHeartItem(Properties settings) {
        super(settings);
    }

    @Override
    public Component getName(ItemStack stack) {
        return Component.translatable(this.getDescriptionId()).setStyle(Style.EMPTY.withColor(Mth.hsvToRgb(290 / 360f, 0.38f, 1.0f)));
    }
}
