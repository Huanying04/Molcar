package net.nekomura.molcar.molcar.renderer;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.nekomura.molcar.molcar.entity.MolcarEntity;
import net.nekomura.molcar.molcar.model.MolcarEntityModel;

public class MolcarHeldItemFeatureRenderer extends FeatureRenderer<MolcarEntity, MolcarEntityModel<MolcarEntity>> {
    public MolcarHeldItemFeatureRenderer(FeatureRendererContext<MolcarEntity, MolcarEntityModel<MolcarEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, MolcarEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrixStack.push();
        ItemStack itemStack = entity.getEquippedStack(EquipmentSlot.MAINHAND);

        matrixStack.scale(3.25F, 3.25F, 3.25F);

        matrixStack.translate(0.0D, 0.34222222D, -0.45D);

        matrixStack.multiply(Vector3f.POSITIVE_Z.getRadialQuaternion(entity.getRoll()));
        matrixStack.multiply(Vector3f.POSITIVE_Y.getDegreesQuaternion(headYaw));
        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(headPitch));

        matrixStack.multiply(Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F));
        MinecraftClient.getInstance().getHeldItemRenderer().renderItem(entity, itemStack, ModelTransformation.Mode.GROUND, false, matrixStack, vertexConsumerProvider, light);
        matrixStack.pop();
    }
}
