package net.nekomura.molcar.molcar.renderer;

import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.util.Identifier;
import net.nekomura.molcar.molcar.entity.MolcarEntity;
import net.nekomura.molcar.molcar.model.MolcarEntityModel;

public class MolcarEntityRenderer extends MobEntityRenderer<MolcarEntity, MolcarEntityModel<MolcarEntity>> {

    public MolcarEntityRenderer(EntityRenderDispatcher entityRenderDispatcher) {
        super(entityRenderDispatcher, new MolcarEntityModel<>(), 0.5f);
        this.addFeature(new MolcarHeldItemFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(MolcarEntity entity) {
        return entity.getTexture();
    }
}
