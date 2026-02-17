package net.farzad.crystalline;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.farzad.crystalline.datagen.*;

public class CrystallineDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(ModItemTagProvider::new);
        pack.addProvider(ModBlockTagProvider::new);
        pack.addProvider(ModDamageTypeTagProvider::new);
        pack.addProvider(ModModelProvider::new);
        pack.addProvider(ModLootTableProvider::new);

    }

}
