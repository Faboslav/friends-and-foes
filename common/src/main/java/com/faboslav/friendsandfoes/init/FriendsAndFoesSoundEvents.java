package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

/**
 * @see SoundEvents
 */
public final class FriendsAndFoesSoundEvents
{
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_DEATH;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_HEAD_SPIN;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_HURT;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_REPAIR;
	public static final Supplier<SoundEvent> ENTITY_COPPER_GOLEM_STEP;
	public static final Supplier<SoundEvent> ENTITY_GLARE_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_DEATH;
	public static final Supplier<SoundEvent> ENTITY_GLARE_EAT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_GRUMPINESS;
	public static final Supplier<SoundEvent> ENTITY_GLARE_GRUMPINESS_SHORT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_HURT;
	public static final Supplier<SoundEvent> ENTITY_GLARE_RUSTLE;
	public static final Supplier<SoundEvent> ENTITY_ICE_CHUNK_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_ICE_CHUNK_HIT;
	public static final Supplier<SoundEvent> ENTITY_ICE_CHUNK_SUMMON;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_CAST_SPELL;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_DEATH;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_HURT;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_PREPARE_SLOWNESS;
	public static final Supplier<SoundEvent> ENTITY_ICEOLOGER_PREPARE_SUMMON;
	public static final Supplier<SoundEvent> ENTITY_MAULER_BITE;
	public static final Supplier<SoundEvent> ENTITY_MAULER_DEATH;
	public static final Supplier<SoundEvent> ENTITY_MAULER_GROWL;
	public static final Supplier<SoundEvent> ENTITY_MAULER_HURT;
	public static final Supplier<SoundEvent> ENTITY_MOOBLOOM_CONVERT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHIELD_DEBRIS_IMPACT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_AMBIENT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_DEATH;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_HURT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHIELD_BREAK;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHOCKWAVE;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SHOOT;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_STEP;
	public static final Supplier<SoundEvent> ENTITY_WILDFIRE_SUMMON_BLAZE;

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
		ENTITY_ICEOLOGER_PREPARE_SLOWNESS = register("entity", "iceologer.prepare_slowness");
		ENTITY_ICEOLOGER_PREPARE_SUMMON = register("entity", "iceologer.prepare_summon");
		ENTITY_MAULER_BITE = register("entity", "mauler.bite");
		ENTITY_MAULER_DEATH = register("entity", "mauler.death");
		ENTITY_MAULER_GROWL = register("entity", "mauler.growl");
		ENTITY_MAULER_HURT = register("entity", "mauler.hurt");
		ENTITY_MOOBLOOM_CONVERT = register("entity", "moobloom.convert");
		ENTITY_WILDFIRE_SHIELD_DEBRIS_IMPACT = register("entity", "shield_debris.impact");
		ENTITY_WILDFIRE_AMBIENT = register("entity", "wildfire.ambient");
		ENTITY_WILDFIRE_DEATH = register("entity", "wildfire.death");
		ENTITY_WILDFIRE_HURT = register("entity", "wildfire.hurt");
		ENTITY_WILDFIRE_SHIELD_BREAK = register("entity", "wildfire.shield_break");
		ENTITY_WILDFIRE_SHOCKWAVE = register("entity", "wildfire.shockwave");
		ENTITY_WILDFIRE_SHOOT = register("entity", "wildfire.shoot");
		ENTITY_WILDFIRE_STEP = register("entity", "wildfire.step");
		ENTITY_WILDFIRE_SUMMON_BLAZE = register("entity", "wildfire.summon_blaze");
	}

	private static Supplier<SoundEvent> register(String type, String name) {
		String id = type + "." + name;
		var soundEvent = SoundEvent.of(FriendsAndFoes.makeID(id));

		return RegistryHelper.registerSoundEvent(id, () -> soundEvent);
	}

	public static void init() {
	}

	private FriendsAndFoesSoundEvents() {
	}
}
