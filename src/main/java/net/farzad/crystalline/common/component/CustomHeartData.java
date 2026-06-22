package net.farzad.crystalline.common.component;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.util.StringRepresentable;

public record CustomHeartData(String id, int minimumCharge, int cooldown) implements StringRepresentable {
    public static final CustomHeartData NONE = new CustomHeartData("none",1,10);

    public static final Codec<CustomHeartData> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.STRING.fieldOf("id").forGetter(CustomHeartData::id),
                    Codec.INT.fieldOf("minimumCharge").forGetter(CustomHeartData::minimumCharge),
                    Codec.INT.fieldOf("cooldown").forGetter(CustomHeartData::cooldown)
            ).apply(instance,CustomHeartData::new)
    );

    @Override
    public String getSerializedName() {
        return this.id();
    }
}
