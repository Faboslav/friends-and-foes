package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.registry.Registry;

public final class ModSounds
{
	public static final DeferredRegister<SoundEvent> SOUNDS = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.SOUND_EVENT_KEY);

	public static final RegistrySupplier<SoundEvent> ENTITY_COPPER_GOLEM_DEATH;
	public static final RegistrySupplier<SoundEvent> ENTITY_COPPER_GOLEM_HEAD_SPIN;
	public static final RegistrySupplier<SoundEvent> ENTITY_COPPER_GOLEM_HURT;
	public static final RegistrySupplier<SoundEvent> ENTITY_COPPER_GOLEM_REPAIR;
	public static final RegistrySupplier<SoundEvent> ENTITY_COPPER_GOLEM_STEP;
	public static final RegistrySupplier<SoundEvent> ENTITY_GLARE_AMBIENT;
	public static final RegistrySupplier<SoundEvent> ENTITY_GLARE_DEATH;
	public static final RegistrySupplier<SoundEvent> ENTITY_GLARE_EAT;
	public static final RegistrySupplier<SoundEvent> ENTITY_GLARE_GRUMPINESS;
	public static final RegistrySupplier<SoundEvent> ENTITY_GLARE_GRUMPINESS_SHORT;
	public static final RegistrySupplier<SoundEvent> ENTITY_GLARE_HURT;
	public static final RegistrySupplier<SoundEvent> ENTITY_GLARE_RUSTLE;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICE_CHUNK_AMBIENT;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICE_CHUNK_HIT;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICE_CHUNK_SUMMON;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICEOLOGER_AMBIENT;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICEOLOGER_CAST_SPELL;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICEOLOGER_DEATH;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICEOLOGER_HURT;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICEOLOGER_PREPARES_SLOWNESS;
	public static final RegistrySupplier<SoundEvent> ENTITY_ICEOLOGER_PREPARES_SUMMON;

	static {
		ENTITY_COPPER_GOLEM_DEATH = register("entity", "copper_golem.death");
		ENTITY_COPPER_GOLEM_HEAD_SPIN = register("entity", "copper_golem.head_spin");
		ENTITY_COPPER_GOLEM_HURT = register("entity", "copper_golem.hurt");
		ENTITY_COPPER_GOLEM_REPAIR = register("entity", "copper_golem.repair");
		ENTITY_COPPER_GOLEM_STEP = register("entity", "copper_golem.step");
		ENTITY_GLARE_AMBIENT = register("entity", "glare.ambient");
		ENTITY_GLARE_DEATH = register("entity", "glare.death");
		ENTITY_GLARE_EAT = register("entity", "glare.eat");
		ENTITY_GLARE_GRUMPINESS = register("entity", "glare.grumpiness");
		ENTITY_GLARE_GRUMPINESS_SHORT = register("entity", "glare.grumpiness_short");
		ENTITY_GLARE_HURT = register("entity", "glare.hurt");
		ENTITY_GLARE_RUSTLE = register("entity", "glare.rustle");
		ENTITY_ICE_CHUNK_AMBIENT = register("entity", "ice_chunk.ambient");
		ENTITY_ICE_CHUNK_HIT = register("entity", "ice_chunk.hit");
		ENTITY_ICE_CHUNK_SUMMON = register("entity", "ice_chunk.summon");
		ENTITY_ICEOLOGER_AMBIENT = register("entity", "iceologer.ambient");
		ENTITY_ICEOLOGER_CAST_SPELL = register("entity", "iceologer.cast_spell");
		ENTITY_ICEOLOGER_DEATH = register("entity", "iceologer.death");
		ENTITY_ICEOLOGER_HURT = register("entity", "iceologer.hurt");
		ENTITY_ICEOLOGER_PREPARES_SLOWNESS = register("entity", "iceologer.prepares_slowness");
		ENTITY_ICEOLOGER_PREPARES_SUMMON = register("entity", "iceologer.prepares_summon");
	}

	private static RegistrySupplier<SoundEvent> register(String type, String name) {
		String id = type + "." + name;
		var soundEvent = new SoundEvent(FriendsAndFoes.makeID(id));

		return SOUNDS.register(id, () -> soundEvent);
	}

	public static void initRegister() {
		SOUNDS.register();
	}

	public static void init() {
	}

	private ModSounds() {
	}
}
