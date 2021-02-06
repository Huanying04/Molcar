package net.nekomura.molcar.molcar.registry;

import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import net.nekomura.molcar.molcar.Molcar;

public class ModSoundEvents {

    public static final SoundEvent MOLCAR_HAPPY = new SoundEvent(new Identifier(Molcar.MOD_ID, "molcar_happy"));
    public static final SoundEvent MOLCAR_FEED = new SoundEvent(new Identifier(Molcar.MOD_ID, "molcar_feed"));
    public static final SoundEvent MOLCAR_HURT = new SoundEvent(new Identifier(Molcar.MOD_ID, "molcar_hurt"));
    public static final SoundEvent MOLCAR_DIE = new SoundEvent(new Identifier(Molcar.MOD_ID, "molcar_die"));
    public static final SoundEvent MOLCAR_EAT = new SoundEvent(new Identifier(Molcar.MOD_ID, "molcar_eat"));
    public static final SoundEvent MOLCAR_STEP = new SoundEvent(new Identifier(Molcar.MOD_ID, "molcar_step"));

    public static void register() {
        Registry.register(Registry.SOUND_EVENT, new Identifier(Molcar.MOD_ID, "molcar_happy"), MOLCAR_HAPPY);
        Registry.register(Registry.SOUND_EVENT, new Identifier(Molcar.MOD_ID, "molcar_feed"), MOLCAR_FEED);
        Registry.register(Registry.SOUND_EVENT, new Identifier(Molcar.MOD_ID, "molcar_hurt"), MOLCAR_HURT);
        Registry.register(Registry.SOUND_EVENT, new Identifier(Molcar.MOD_ID, "molcar_die"), MOLCAR_DIE);
        Registry.register(Registry.SOUND_EVENT, new Identifier(Molcar.MOD_ID, "molcar_eat"), MOLCAR_EAT);
        Registry.register(Registry.SOUND_EVENT, new Identifier(Molcar.MOD_ID, "molcar_step"), MOLCAR_STEP);
    }
}
