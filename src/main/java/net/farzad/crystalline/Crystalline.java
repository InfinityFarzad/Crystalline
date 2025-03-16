package net.farzad.crystalline;

import net.fabricmc.api.ModInitializer;
import net.farzad.crystalline.block.ModBlocks;
import net.farzad.crystalline.dataComponents.ModDataComponentTypes;
import net.farzad.crystalline.enchantments.ModEnchantmentEffects;
import net.farzad.crystalline.entity.ModEntities;
import net.farzad.crystalline.items.ModItems;
import net.farzad.crystalline.particle.ModParticles;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Crystalline implements ModInitializer {
	public static final String MOD_ID = "crystalline";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	public static Identifier id(String value) {
		return Identifier.of(MOD_ID, value);
	}
	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
/*		ModItemGroups.registerItemGroups();*/
		ModEntities.registerModEntities();
		ModEnchantmentEffects.registerEnchantmentEffects();
		ModDataComponentTypes.registerDataComponentTypes();
		ModParticles.registerParticles();
		LOGGER.info("Hello Fabric world!");
	}
}