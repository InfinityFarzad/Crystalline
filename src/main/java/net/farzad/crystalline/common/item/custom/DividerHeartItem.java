package net.farzad.crystalline.common.item.custom;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;

public class DividerHeartItem extends Item {

    public DividerHeartItem(Settings settings, String id) {
        super(settings);
    }

    @Override
    public Text getName(ItemStack stack) {
        return Text.translatable(this.getTranslationKey()).setStyle(Style.EMPTY.withColor(MathHelper.hsvToRgb(290 / 360f, 0.38f, 1.0f)));
    }
}
