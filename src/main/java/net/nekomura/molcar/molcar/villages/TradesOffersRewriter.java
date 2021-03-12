package net.nekomura.molcar.molcar.villages;

import com.google.common.collect.ImmutableMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.block.Blocks;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Items;
import net.minecraft.village.TradeOffers;
import net.minecraft.village.VillagerProfession;
import net.nekomura.molcar.molcar.copies.CopyOfTradeOffers;
import net.nekomura.molcar.molcar.registry.ModItems;

public class TradesOffersRewriter {
    public static void add() {
        TradeOffers.PROFESSION_TO_LEVELED_TRADE.put(VillagerProfession.FARMER, new Int2ObjectOpenHashMap(ImmutableMap.of(1, new TradeOffers.Factory[]{new CopyOfTradeOffers.BuyForOneEmeraldFactory(Items.WHEAT, 20, 16, 2), new CopyOfTradeOffers.BuyForOneEmeraldFactory(Items.POTATO, 26, 16, 2), new CopyOfTradeOffers.BuyForOneEmeraldFactory(Items.CARROT, 22, 16, 2), new CopyOfTradeOffers.BuyForOneEmeraldFactory(Items.BEETROOT, 15, 16, 2), new CopyOfTradeOffers.SellItemFactory(Items.BREAD, 1, 6, 16, 1), new CopyOfTradeOffers.BuyForOneEmeraldFactory(ModItems.LETTUCE_LEAF, 18, 16, 2)}, 2, new TradeOffers.Factory[]{new CopyOfTradeOffers.BuyForOneEmeraldFactory(Blocks.PUMPKIN, 6, 12, 10), new CopyOfTradeOffers.SellItemFactory(Items.PUMPKIN_PIE, 1, 4, 5), new CopyOfTradeOffers.SellItemFactory(Items.APPLE, 1, 4, 16, 5)}, 3, new TradeOffers.Factory[]{new CopyOfTradeOffers.SellItemFactory(Items.COOKIE, 3, 18, 10), new CopyOfTradeOffers.BuyForOneEmeraldFactory(Blocks.MELON, 4, 12, 20)}, 4, new TradeOffers.Factory[]{new CopyOfTradeOffers.SellItemFactory(Blocks.CAKE, 1, 1, 12, 15), new CopyOfTradeOffers.SellSuspiciousStewFactory(StatusEffects.NIGHT_VISION, 100, 15), new CopyOfTradeOffers.SellSuspiciousStewFactory(StatusEffects.JUMP_BOOST, 160, 15), new CopyOfTradeOffers.SellSuspiciousStewFactory(StatusEffects.WEAKNESS, 140, 15), new CopyOfTradeOffers.SellSuspiciousStewFactory(StatusEffects.BLINDNESS, 120, 15), new CopyOfTradeOffers.SellSuspiciousStewFactory(StatusEffects.POISON, 280, 15), new CopyOfTradeOffers.SellSuspiciousStewFactory(StatusEffects.SATURATION, 7, 15)}, 5, new TradeOffers.Factory[]{new CopyOfTradeOffers.SellItemFactory(Items.GOLDEN_CARROT, 3, 3, 30), new CopyOfTradeOffers.SellItemFactory(Items.GLISTERING_MELON_SLICE, 4, 3, 30)})));
        TradeOffers.WANDERING_TRADER_TRADES.put(1, new TradeOffers.Factory[]{
                new CopyOfTradeOffers.SellItemFactory(Items.SEA_PICKLE, 2, 1, 5, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.SLIME_BALL, 4, 1, 5, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.GLOWSTONE, 2, 1, 5, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.NAUTILUS_SHELL, 5, 1, 5, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.FERN, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.SUGAR_CANE, 1, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.PUMPKIN, 1, 1, 4, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.KELP, 3, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.CACTUS, 3, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.DANDELION, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.POPPY, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BLUE_ORCHID, 1, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.ALLIUM, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.AZURE_BLUET, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.RED_TULIP, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.ORANGE_TULIP, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.WHITE_TULIP, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.PINK_TULIP, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.OXEYE_DAISY, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.CORNFLOWER, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.LILY_OF_THE_VALLEY, 1, 1, 7, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.WHEAT_SEEDS, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BEETROOT_SEEDS, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.PUMPKIN_SEEDS, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.MELON_SEEDS, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.ACACIA_SAPLING, 5, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BIRCH_SAPLING, 5, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.DARK_OAK_SAPLING, 5, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.JUNGLE_SAPLING, 5, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.OAK_SAPLING, 5, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.SPRUCE_SAPLING, 5, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.RED_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.WHITE_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BLUE_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.PINK_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BLACK_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.GREEN_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.LIGHT_GRAY_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.MAGENTA_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.YELLOW_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.GRAY_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.PURPLE_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.LIGHT_BLUE_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.LIME_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.ORANGE_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BROWN_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.CYAN_DYE, 1, 3, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BRAIN_CORAL_BLOCK, 3, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BUBBLE_CORAL_BLOCK, 3, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.FIRE_CORAL_BLOCK, 3, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.HORN_CORAL_BLOCK, 3, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.TUBE_CORAL_BLOCK, 3, 1, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.VINE, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.BROWN_MUSHROOM, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.RED_MUSHROOM, 1, 1, 12, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.LILY_PAD, 1, 2, 5, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.SAND, 1, 8, 8, 1),
                new CopyOfTradeOffers.SellItemFactory(Items.RED_SAND, 1, 4, 6, 1),
                new CopyOfTradeOffers.SellItemFactory(ModItems.LETTUCE_SEEDS, 1, 1, 12, 1)
        });
    }
}
