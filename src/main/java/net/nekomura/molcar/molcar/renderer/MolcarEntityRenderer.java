package net.nekomura.molcar.molcar.renderer;

import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.nekomura.molcar.molcar.Molcar;
import net.nekomura.molcar.molcar.entity.MolcarEntity;
import net.nekomura.molcar.molcar.model.MolcarEntityModel;
import net.nekomura.molcar.molcar.registry.ModEntityModelLayers;

public class MolcarEntityRenderer extends MobEntityRenderer<MolcarEntity, MolcarEntityModel<MolcarEntity>> {

    public MolcarEntityRenderer(EntityRendererFactory.Context context) {
        super(context, new MolcarEntityModel<>(context.getPart(ModEntityModelLayers.MOLCAR_ENTITY_MODEL_LAYER)), 0.5f);
        this.addFeature(new MolcarHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(MolcarEntity entity) {
        return new Identifier(Molcar.MOD_ID, "textures/entity/molcar/molcar.png");
    }
}
