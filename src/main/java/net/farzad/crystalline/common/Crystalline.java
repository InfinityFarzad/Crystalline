package net.farzad.crystalline.common;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.farzad.crystalline.common.init.*;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.gamerules.GameRule;
import net.minecraft.world.level.gamerules.GameRuleCategory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crystalline implements ModInitializer {
    public static final String MOD_ID = "crystalline";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static final GameRule<Integer> MAXIMUM_DIVIDER_CHARGE = GameRuleBuilder.forInteger(16).category(GameRuleCategory.DROPS).buildAndRegister(id("require_weapon_to_drop_daggerlance"));

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
        LOGGER.info("registering everything for crystalline");
    }
}