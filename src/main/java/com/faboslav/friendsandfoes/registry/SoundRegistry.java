package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.config.Settings;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.registry.Registry;

/**
 * @see SoundEvents
 */
public class SoundRegistry {
    public static final SoundEvent ENTITY_COPPER_GOLEM_HURT;
    public static final SoundEvent ENTITY_COPPER_GOLEM_DEATH;
    public static final SoundEvent ENTITY_COPPER_GOLEM_REPAIR;
    public static final SoundEvent ENTITY_COPPER_GOLEM_HEAD_SPIN;

    private static SoundEvent register(String type, String name) {
        String id = type + "." + name;
        return Registry.register(
                Registry.SOUND_EVENT,
                Settings.makeStringID(id),
                new SoundEvent(Settings.makeID(id))
        );
    }
    static {
        ENTITY_COPPER_GOLEM_HURT = register("entity", "copper_golem.hurt");
        ENTITY_COPPER_GOLEM_DEATH = register("entity", "copper_golem.death");
        ENTITY_COPPER_GOLEM_REPAIR = register("entity", "copper_golem.repair");
        ENTITY_COPPER_GOLEM_HEAD_SPIN = register("entity", "copper_golem.head_spin");
    }

    public static void init() {}
}
