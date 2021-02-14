package net.nekomura.molcar.molcar.registry;

import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nekomura.molcar.molcar.Molcar;
import net.nekomura.molcar.molcar.entity.MolcarEntity;
import net.nekomura.molcar.molcar.renderer.MolcarEntityRenderer;

public class ModEntities {

    public static final EntityType<MolcarEntity> MOLCAR = Registry.register(
            Registry.ENTITY_TYPE,
            new Identifier(Molcar.MOD_ID, "molcar"),
            FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, MolcarEntity::new)
                    .dimensions(EntityDimensions.fixed(1.75f, 1.725f))  //碰撞箱
                    .build()
    );

    public static void register() {
        FabricDefaultAttributeRegistry.register(MOLCAR, MolcarEntity.createMobAttributes());

        EntityRendererRegistry.INSTANCE.register(
                MOLCAR,
                (dispatcher, context) -> new MolcarEntityRenderer(dispatcher)
        );
    }

}
