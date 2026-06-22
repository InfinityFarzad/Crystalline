package net.farzad.crystalline;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.farzad.crystalline.datagen.*;

public class CrystallineDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(CrystallineItemTagProvider::new);
        pack.addProvider(CrystallineBlockTagProvider::new);
        pack.addProvider(CrystallineDamageTypeTagProvider::new);
        pack.addProvider(CrystallineModelProvider::new);
        pack.addProvider(CrystallineLootTableProvider::new);
        pack.addProvider(CrystallineRecipeProvider::new);

	}
}
