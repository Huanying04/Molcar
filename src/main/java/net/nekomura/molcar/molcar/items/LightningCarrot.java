package net.nekomura.molcar.molcar.items;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LightningCarrot extends Item {
    public LightningCarrot(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        user.applyStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 60 * 20, 2));
        return super.finishUsing(stack, world, user);
    }
}
