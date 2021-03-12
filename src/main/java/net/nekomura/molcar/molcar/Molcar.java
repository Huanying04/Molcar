package net.nekomura.molcar.molcar;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.fabricmc.api.ModInitializer;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.nekomura.molcar.molcar.registry.ModBlocks;
import net.nekomura.molcar.molcar.registry.ModEntities;
import net.nekomura.molcar.molcar.registry.ModItems;
import net.nekomura.molcar.molcar.registry.ModSoundEvents;
import net.nekomura.molcar.molcar.copies.CopyOfTradeOffers;
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
