package net.nekomura.molcar.molcar.mixin;

import com.google.common.collect.ImmutableMap;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.impl.client.renderer.registry.EntityModelLayerImpl;
import net.minecraft.client.model.Dilation;
import net.minecraft.client.model.TexturedModelData;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModels;
import net.nekomura.molcar.molcar.model.MolcarEntityModel;
import net.nekomura.molcar.molcar.registry.ModEntityModelLayers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Map;

@Mixin(EntityModels.class)
public abstract class EntityModelsMixin {
    @Inject(method = "getModels", at = @At(value = "INVOKE", target = "Lcom/google/common/collect/ImmutableMap$Builder;build()Lcom/google/common/collect/ImmutableMap;"), locals = LocalCapture.CAPTURE_FAILEXCEPTION)
    private static void registerExtraModelData(CallbackInfoReturnable<Map<EntityModelLayer, TexturedModelData>> info, ImmutableMap.Builder<EntityModelLayer, TexturedModelData> builder) {
        builder.put(ModEntityModelLayers.MOLCAR_ENTITY_MODEL_LAYER, MolcarEntityModel.getTexturedModelData());
    }
}
