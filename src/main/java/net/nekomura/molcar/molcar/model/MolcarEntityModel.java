package net.nekomura.molcar.molcar.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.nekomura.molcar.molcar.entity.MolcarEntity;

@Environment(EnvType.CLIENT)
public class MolcarEntityModel<T extends MobEntity> extends EntityModel<MolcarEntity> {

    public final ModelPart body;
    public final ModelPart front_leg_left;
    public final ModelPart front_leg_right;
    public final ModelPart back_leg_left;
    public final ModelPart back_leg_right;

    public MolcarEntityModel(ModelPart root) {
        super();
        this.body = root.getChild("body");
        this.front_leg_left = root.getChild("front_leg_left");
        this.front_leg_right = root.getChild("front_leg_right");
        this.back_leg_left = root.getChild("back_leg_left");
        this.back_leg_right = root.getChild("back_leg_right");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("body", ModelPartBuilder.create()
                        .uv(119, 53).cuboid(-13.0F, -28.0F, -15.0F, 26.0F, 1.0F, 33.0F)
                        .uv(0, 0).cuboid(-14.0F, -28.0F, -16.0F, 28.0F, 12.0F, 1.0F)
                        .uv(0, 13).cuboid(-15.0F, -16.0F, -19.0F, 30.0F, 13.0F, 1.0F)
                        .uv(119, 40).cuboid(-14.0F, -28.0F, 18.0F, 28.0F, 12.0F, 1.0F)
                        .uv(41, 40).cuboid(-15.0F, -16.0F, 19.0F, 30.0F, 13.0F, 1.0F)
                        .uv(41, 0).cuboid(-16.0F, -3.0F, -19.0F, 32.0F, 1.0F, 39.0F)
                        .uv(84, 40).cuboid(13.0F, -28.0F, -15.0F, 1.0F, 11.0F, 33.0F)
                        .uv(84, 40).cuboid(-14.0F, -28.0F, -15.0F, 1.0F, 11.0F, 33.0F, true)
                        .uv(0, 31).cuboid(15.0F, -16.0F, -19.0F, 1.0F, 13.0F, 39.0F, true)
                        .uv(0, 31).cuboid(-16.0F, -16.0F, -19.0F, 1.0F, 13.0F, 39.0F)
                        .uv(41, 56).cuboid(-16.0F, -17.0F, -19.0F, 2.0F, 1.0F, 39.0F, true)
                        .uv(41, 56).cuboid(14.0F, -17.0F, -19.0F, 2.0F, 1.0F, 39.0F)
                        .uv(12, 31).cuboid(-22.0F, -27.0F, -13.0F, 8.0F, 4.0F, 1.0F)
                        .uv(12, 31).cuboid(14.0F, -27.0F, -13.0F, 8.0F, 4.0F, 1.0F)
                        .uv(0, 27).cuboid(-14.0F, -17.0F, -19.0F, 28.0F, 1.0F, 3.0F)
                        .uv(41, 54).cuboid(-14.0F, -17.0F, 19.0F, 28.0F, 1.0F, 1.0F),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        modelPartData.addChild("front_leg_left", ModelPartBuilder.create()
                    .uv(0, 31).cuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F),
                ModelTransform.pivot(16.0F, 18.5F, -12.5F));
        modelPartData.addChild("front_leg_right", ModelPartBuilder.create()
                    .uv(0, 31).cuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F),
                ModelTransform.pivot(-16.0F, 18.5F, -12.5F));
        modelPartData.addChild("back_leg_left", ModelPartBuilder.create()
                    .uv(0, 31).cuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F),
                ModelTransform.pivot(16.0F, 18.5F, 13.5F));
        modelPartData.addChild("back_leg_right", ModelPartBuilder.create()
                    .uv(0, 31).cuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F),
                ModelTransform.pivot(-16.0F, 18.5F, 13.5F));
        return TexturedModelData.of(modelData, 256, 256);
    }

    @Override
    public void setAngles(MolcarEntity entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
        if (MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance > 0) {
            this.back_leg_right.roll = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 1.2f;
            this.front_leg_left.roll = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 1.2f;

        }else {
            this.back_leg_left.roll = MathHelper.cos(limbAngle * 0.6662F) * 1.4F * limbDistance * 1.2f;
            this.front_leg_right.roll = MathHelper.cos(limbAngle * 0.6662F + 3.1415927F) * 1.4F * limbDistance * 1.2f;
        }
    }

    @Override
    public void render(MatrixStack matrixStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha){
        body.render(matrixStack, buffer, packedLight, packedOverlay);
        front_leg_left.render(matrixStack, buffer, packedLight, packedOverlay);
        front_leg_right.render(matrixStack, buffer, packedLight, packedOverlay);
        back_leg_left.render(matrixStack, buffer, packedLight, packedOverlay);
        back_leg_right.render(matrixStack, buffer, packedLight, packedOverlay);
    }
}
