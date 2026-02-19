package net.farzad.crystalline.common;

import net.fabricmc.api.ModInitializer;
import net.farzad.crystalline.block.ModBlocks;
import net.farzad.crystalline.common.sound.ModSounds;
import net.farzad.crystalline.common.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.common.entity.ModEntities;
import net.farzad.crystalline.common.item.ModItemGroups;
import net.farzad.crystalline.common.item.ModItems;
import net.farzad.crystalline.client.particle.ModParticles;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crystalline implements ModInitializer {
    public static final String MOD_ID = "crystalline";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String value) {
        return Identifier.of(MOD_ID, value);
    }

    @Override
    public void onInitialize() {
        ModItems.init();
        ModBlocks.init();
        ModItemGroups.init();
        ModEntities.init();
        ModParticles.init();
        ModDataComponentTypes.registerDataComponentTypes();
        ModSounds.init();
        ModItemGroups.init();
    }
}