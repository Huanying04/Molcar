package net.nekomura.molcar.molcar;

import net.fabricmc.api.ModInitializer;
import net.nekomura.molcar.molcar.registry.ModEntities;
import net.nekomura.molcar.molcar.registry.ModItems;
import net.nekomura.molcar.molcar.registry.ModSoundEvents;

public class Molcar implements ModInitializer {

    public static final String MOD_ID = "molcar";

    @Override
    public void onInitialize() {
        ModItems.register();
        ModEntities.register();
        ModSoundEvents.register();
    }
}
