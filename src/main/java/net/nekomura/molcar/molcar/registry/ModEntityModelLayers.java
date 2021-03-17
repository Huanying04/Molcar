package net.nekomura.molcar.molcar.registry;

import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.util.Identifier;
import net.nekomura.molcar.molcar.Molcar;

public class ModEntityModelLayers {
    public static EntityModelLayer MOLCAR_ENTITY_MODEL_LAYER = new EntityModelLayer(new Identifier(Molcar.MOD_ID, "molcar"), "main");

    public static void register() {
    }
}

