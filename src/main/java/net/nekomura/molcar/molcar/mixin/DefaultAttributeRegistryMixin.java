package net.nekomura.molcar.molcar.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.DefaultAttributeRegistry;
import net.nekomura.molcar.molcar.entity.MolcarEntity;
import net.nekomura.molcar.molcar.registry.ModEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(DefaultAttributeRegistry.class)
public abstract class DefaultAttributeRegistryMixin {
    @Inject(method = "get", at = @At("HEAD"), cancellable = true)
    private static void getAttribute(EntityType<? extends LivingEntity> type, CallbackInfoReturnable<DefaultAttributeContainer> cir) {
        if (type == ModEntities.MOLCAR) {
            DefaultAttributeContainer container = MolcarEntity.getAttributeContainer();
            cir.setReturnValue(container);
            cir.cancel();
        }
    }

    @Inject(method = "hasDefinitionFor", at = @At("HEAD"), cancellable = true)
    private static void hasDefinition(EntityType<?> type, CallbackInfoReturnable<Boolean> cir) {
        if (type == ModEntities.MOLCAR) {
            cir.setReturnValue(true);
            cir.cancel();
        }
    }
}
