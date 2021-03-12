package net.nekomura.molcar.molcar.mixin;

import it.unimi.dsi.fastutil.objects.Object2FloatMap;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.ItemConvertible;
import net.nekomura.molcar.molcar.registry.ModItems;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ComposterBlock.class)
public abstract class ComposterBlockMixin {
    @Shadow @Final public static Object2FloatMap<ItemConvertible> ITEM_TO_LEVEL_INCREASE_CHANCE;

    @Inject(at = @At("TAIL"), method = "registerDefaultCompostableItems")
    private static void addLettuceToDefaultCompostableItems(CallbackInfo ci) {
        ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.LETTUCE_SEEDS, 0.3F);
        ITEM_TO_LEVEL_INCREASE_CHANCE.put(ModItems.LETTUCE_LEAF, 0.65F);
    }
}
