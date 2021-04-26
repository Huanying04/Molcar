package net.nekomura.molcar.molcar;

import net.fabricmc.api.ModInitializer;
import net.nekomura.molcar.molcar.registry.*;
import net.nekomura.molcar.molcar.villages.TradesOffersRewriter;

public class Molcar implements ModInitializer {

    public static final String MOD_ID = "molcar";

    @Override
    public void onInitialize() {
        ModItems.register();
        ModBlocks.register();
        ModEntities.register();
        ModSoundEvents.register();
        TradesOffersRewriter.add();
    }
}
