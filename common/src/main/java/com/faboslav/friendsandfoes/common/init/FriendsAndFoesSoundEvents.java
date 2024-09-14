package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;

/**
 * @see SoundEvents
 */
public final class FriendsAndFoesSoundEvents
{
	public static final ResourcefulRegistry<SoundEvent> SOUND_EVENTS = ResourcefulRegistries.create(Registries.SOUND_EVENT, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<SoundEvent> ENTITY_COPPER_GOLEM_DEATH = registerSoundEvent("entity.copper_golem.death");
	public static final RegistryEntry<SoundEvent> ENTITY_COPPER_GOLEM_HEAD_SPIN = registerSoundEvent("entity.copper_golem.head_spin");
	public static final RegistryEntry<SoundEvent> ENTITY_COPPER_GOLEM_HURT = registerSoundEvent("entity.copper_golem.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_COPPER_GOLEM_REPAIR = registerSoundEvent("entity.copper_golem.repair");
	public static final RegistryEntry<SoundEvent> ENTITY_COPPER_GOLEM_STEP = registerSoundEvent("entity.copper_golem.step");
	public static final RegistryEntry<SoundEvent> ENTITY_CRAB_DEATH = registerSoundEvent("entity.crab.death");
	public static final RegistryEntry<SoundEvent> ENTITY_CRAB_HURT = registerSoundEvent("entity.crab.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_CRAB_STEP = registerSoundEvent("entity.crab.step");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_AMBIENT = registerSoundEvent("entity.glare.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_DEATH = registerSoundEvent("entity.glare.death");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_EAT = registerSoundEvent("entity.glare.eat");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_GRUMPINESS = registerSoundEvent("entity.glare.grumpiness");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_GRUMPINESS_SHORT = registerSoundEvent("entity.glare.grumpiness_short");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_HURT = registerSoundEvent("entity.glare.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_RUSTLE = registerSoundEvent("entity.glare.rustle");
	public static final RegistryEntry<SoundEvent> ENTITY_GLARE_SHAKE = registerSoundEvent("entity.glare.shake");
	public static final RegistryEntry<SoundEvent> ENTITY_ICE_CHUNK_AMBIENT = registerSoundEvent("entity.ice_chunk.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_ICE_CHUNK_HIT = registerSoundEvent("entity.ice_chunk.hit");
	public static final RegistryEntry<SoundEvent> ENTITY_ICE_CHUNK_SUMMON = registerSoundEvent("entity.ice_chunk.summon");
	public static final RegistryEntry<SoundEvent> ENTITY_ICEOLOGER_AMBIENT = registerSoundEvent("entity.iceologer.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_ICEOLOGER_CAST_SPELL = registerSoundEvent("entity.iceologer.cast_spell");
	public static final RegistryEntry<SoundEvent> ENTITY_ICEOLOGER_DEATH = registerSoundEvent("entity.iceologer.death");
	public static final RegistryEntry<SoundEvent> ENTITY_ICEOLOGER_HURT = registerSoundEvent("entity.iceologer.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_ICEOLOGER_PREPARE_SLOWNESS = registerSoundEvent("entity.iceologer.prepare_slowness");
	public static final RegistryEntry<SoundEvent> ENTITY_ICEOLOGER_PREPARE_SUMMON = registerSoundEvent("entity.iceologer.prepare_summon");
	public static final RegistryEntry<SoundEvent> ENTITY_MAULER_BITE = registerSoundEvent("entity.mauler.bite");
	public static final RegistryEntry<SoundEvent> ENTITY_MAULER_DEATH = registerSoundEvent("entity.mauler.death");
	public static final RegistryEntry<SoundEvent> ENTITY_MAULER_GROWL = registerSoundEvent("entity.mauler.growl");
	public static final RegistryEntry<SoundEvent> ENTITY_MAULER_HURT = registerSoundEvent("entity.mauler.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_MOOBLOOM_CONVERT = registerSoundEvent("entity.moobloom.convert");
	public static final RegistryEntry<SoundEvent> ENTITY_PLAYER_MIRROR_MOVE = registerSoundEvent("entity.player.mirror_move");
	public static final RegistryEntry<SoundEvent> ENTITY_RASCAL_AMBIENT = registerSoundEvent("entity.rascal.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_RASCAL_DISAPPEAR = registerSoundEvent("entity.rascal.disappear");
	public static final RegistryEntry<SoundEvent> ENTITY_RASCAL_HURT = registerSoundEvent("entity.rascal.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_RASCAL_NOD = registerSoundEvent("entity.rascal.nod");
	public static final RegistryEntry<SoundEvent> ENTITY_RASCAL_REAPPEAR = registerSoundEvent("entity.rascal.reappear");
	public static final RegistryEntry<SoundEvent> ENTITY_RASCAL_REWARD = registerSoundEvent("entity.rascal.reward");
	public static final RegistryEntry<SoundEvent> ENTITY_RASCAL_REWARD_BAD = registerSoundEvent("entity.rascal.reward_bad");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_GLUE_ON = registerSoundEvent("entity.tuff_golem.glue_on");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_GLUE_OFF = registerSoundEvent("entity.tuff_golem.glue_off");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_HURT = registerSoundEvent("entity.tuff_golem.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_MOVE = registerSoundEvent("entity.tuff_golem.move");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_REPAIR = registerSoundEvent("entity.tuff_golem.repair");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_SLEEP = registerSoundEvent("entity.tuff_golem.sleep");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_STEP = registerSoundEvent("entity.tuff_golem.step");
	public static final RegistryEntry<SoundEvent> ENTITY_TUFF_GOLEM_WAKE = registerSoundEvent("entity.tuff_golem.wake");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_SHIELD_DEBRIS_IMPACT = registerSoundEvent("entity.shield_debris.impact");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_AMBIENT = registerSoundEvent("entity.wildfire.ambient");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_DEATH = registerSoundEvent("entity.wildfire.death");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_HURT = registerSoundEvent("entity.wildfire.hurt");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_SHIELD_BREAK = registerSoundEvent("entity.wildfire.shield_break");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_SHOCKWAVE = registerSoundEvent("entity.wildfire.shockwave");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_SHOOT = registerSoundEvent("entity.wildfire.shoot");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_STEP = registerSoundEvent("entity.wildfire.step");
	public static final RegistryEntry<SoundEvent> ENTITY_WILDFIRE_SUMMON_BLAZE = registerSoundEvent("entity.wildfire.summon_blaze");

	private static RegistryEntry<SoundEvent> registerSoundEvent(String path) {
			return SOUND_EVENTS.register(path, () -> SoundEvent.of(new Identifier(FriendsAndFoes.MOD_ID, path)));
	}

	private FriendsAndFoesSoundEvents() {
	}
}
