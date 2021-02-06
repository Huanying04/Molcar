package net.nekomura.molcar.molcar.model;

import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.math.MathHelper;
import net.nekomura.molcar.molcar.entity.MolcarEntity;

public class MolcarEntityModel<T extends MobEntity> extends EntityModel<MolcarEntity> {

    public final ModelPart body;
    public final ModelPart front_leg_left;
    public final ModelPart front_leg_right;
    public final ModelPart back_leg_left;
    public final ModelPart back_leg_right;

    public MolcarEntityModel() {
        textureWidth = 256;
        textureHeight = 256;

        body = new ModelPart(this);
        body.setPivot(0.0F, 24.0F, 0.0F);
        body.setTextureOffset(119, 53).addCuboid(-13.0F, -28.0F, -15.0F, 26.0F, 1.0F, 33.0F, 0.0F, false);
        body.setTextureOffset(0, 0).addCuboid(-14.0F, -28.0F, -16.0F, 28.0F, 12.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(0, 13).addCuboid(-15.0F, -16.0F, -19.0F, 30.0F, 13.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(119, 40).addCuboid(-14.0F, -28.0F, 18.0F, 28.0F, 12.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(41, 40).addCuboid(-15.0F, -16.0F, 19.0F, 30.0F, 13.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(41, 0).addCuboid(-16.0F, -3.0F, -19.0F, 32.0F, 1.0F, 39.0F, 0.0F, false);
        body.setTextureOffset(84, 40).addCuboid(13.0F, -28.0F, -15.0F, 1.0F, 11.0F, 33.0F, 0.0F, false);
        body.setTextureOffset(84, 40).addCuboid(-14.0F, -28.0F, -15.0F, 1.0F, 11.0F, 33.0F, 0.0F, true);
        body.setTextureOffset(0, 31).addCuboid(15.0F, -16.0F, -19.0F, 1.0F, 13.0F, 39.0F, 0.0F, true);
        body.setTextureOffset(0, 31).addCuboid(-16.0F, -16.0F, -19.0F, 1.0F, 13.0F, 39.0F, 0.0F, false);
        body.setTextureOffset(41, 56).addCuboid(-16.0F, -17.0F, -19.0F, 2.0F, 1.0F, 39.0F, 0.0F, true);
        body.setTextureOffset(41, 56).addCuboid(14.0F, -17.0F, -19.0F, 2.0F, 1.0F, 39.0F, 0.0F, false);
        body.setTextureOffset(12, 31).addCuboid(-22.0F, -27.0F, -13.0F, 8.0F, 4.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(12, 31).addCuboid(14.0F, -27.0F, -13.0F, 8.0F, 4.0F, 1.0F, 0.0F, false);
        body.setTextureOffset(0, 27).addCuboid(-14.0F, -17.0F, -19.0F, 28.0F, 1.0F, 3.0F, 0.0F, false);
        body.setTextureOffset(41, 54).addCuboid(-14.0F, -17.0F, 19.0F, 28.0F, 1.0F, 1.0F, 0.0F, false);

        front_leg_left = new ModelPart(this);
        front_leg_left.setPivot(16.0F, 18.5F, -12.5F);
        front_leg_left.setTextureOffset(0, 31).addCuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F, 0.0F, false);

        front_leg_right = new ModelPart(this);
        front_leg_right.setPivot(-16.0F, 18.5F, -12.5F);
        front_leg_right.setTextureOffset(0, 31).addCuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F, 0.0F, false);

        back_leg_left = new ModelPart(this);
        back_leg_left.setPivot(16.0F, 18.5F, 13.5F);
        back_leg_left.setTextureOffset(0, 31).addCuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F, 0.0F, false);

        back_leg_right = new ModelPart(this);
        back_leg_right.setPivot(-16.0F, 18.5F, 13.5F);
        back_leg_right.setTextureOffset(0, 31).addCuboid(-1.0F, -1.5F, -3.5F, 2.0F, 7.0F, 7.0F, 0.0F, false);
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
