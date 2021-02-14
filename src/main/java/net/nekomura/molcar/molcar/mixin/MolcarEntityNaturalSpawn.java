package net.nekomura.molcar.molcar.mixin;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraft.world.gen.feature.DefaultBiomeFeatures;
import net.nekomura.molcar.molcar.entity.MolcarEntity;
import net.nekomura.molcar.molcar.registry.ModEntities;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(DefaultBiomeFeatures.class)
public abstract class MolcarEntityNaturalSpawn {
    private static final EntityType<MolcarEntity> MOLCAR = ModEntities.MOLCAR;

    @Inject(method = "addFarmAnimals(Lnet/minecraft/world/biome/SpawnSettings$Builder;)V", at = @At("HEAD"))
    private static void addMolcar(SpawnSettings.Builder builder, CallbackInfo ci) {
        System.out.println("Added!");
        builder.spawn(SpawnGroup.CREATURE, new SpawnSettings.SpawnEntry(MOLCAR, 7, 1, 3));
    }
}
