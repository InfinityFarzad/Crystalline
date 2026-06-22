package net.farzad.crystalline.common;

import net.fabricmc.api.ModInitializer;
import net.farzad.crystalline.common.init.CrystallineBlocks;
import net.farzad.crystalline.common.init.CrystallineParticles;
import net.farzad.crystalline.common.init.CrystallineDataComponentTypes;
import net.farzad.crystalline.common.init.CrystallineEntities;
import net.farzad.crystalline.common.init.CrystallineItems;
import net.farzad.crystalline.common.init.CrystallineSounds;
import net.minecraft.resources.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crystalline implements ModInitializer {
    public static final String MOD_ID = "crystalline";

    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static Identifier id(String value) {
        return Identifier.fromNamespaceAndPath(MOD_ID, value);
    }

    @Override
    public void onInitialize() {
        CrystallineItems.init();
        CrystallineBlocks.init();
        CrystallineEntities.init();
        CrystallineParticles.init();
        CrystallineDataComponentTypes.registerDataComponentTypes();
        CrystallineSounds.init();
    }
}