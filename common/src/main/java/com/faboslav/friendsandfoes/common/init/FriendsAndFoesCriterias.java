package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.advancements.criterion.CompleteHideAndSeekGameCriterion;
import net.minecraft.advancements.triggers.TameAnimalTrigger;
import net.minecraft.advancements.triggers.LightningStrikeTrigger;

//? if >= 1.21.1 {
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.advancements.triggers.CriterionTrigger;
import net.minecraft.core.registries.BuiltInRegistries;
//?} else {
/*import net.minecraft.advancements.triggers.CriteriaTriggers;
import net.minecraft.resources.Identifier;
*///?}

public final class FriendsAndFoesCriterias
{
	//? if >= 1.21.1 {
	public static final ResourcefulRegistry<CriterionTrigger<?>> CRITERIAS = ResourcefulRegistries.create(BuiltInRegistries.TRIGGER_TYPES, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<TameAnimalTrigger> TAME_GLARE = CRITERIAS.register("tame_glare", () -> new TameAnimalTrigger());
	public static final RegistryEntry<LightningStrikeTrigger> ACTIVATE_ZOMBIE_HORSE_TRAP = CRITERIAS.register("activate_zombie_horse_trap", () -> new LightningStrikeTrigger());
	public static final RegistryEntry<CompleteHideAndSeekGameCriterion> COMPLETE_HIDE_AND_SEEK_GAME = CRITERIAS.register("complete_hide_and_seek_game", () -> new CompleteHideAndSeekGameCriterion());
	//?} else {
	/*public static final TameAnimalTrigger TAME_GLARE = CriteriaTriggers.register(new TameGlareTrigger());
	public static final LightningStrikeTrigger ACTIVATE_ZOMBIE_HORSE_TRAP = CriteriaTriggers.register(new ActivateZombieHorseTrapTrigger());
	public static final CompleteHideAndSeekGameCriterion COMPLETE_HIDE_AND_SEEK_GAME = CriteriaTriggers.register(new CompleteHideAndSeekGameCriterion());

	public static void init() {
	}

	private static final class TameGlareTrigger extends TameAnimalTrigger {
		private static final Identifier ID = FriendsAndFoes.makeID("tame_glare");

		@Override
		public Identifier getId() {
			return ID;
		}
	}

	private static final class ActivateZombieHorseTrapTrigger extends LightningStrikeTrigger {
		private static final Identifier ID = FriendsAndFoes.makeID("activate_zombie_horse_trap");

		@Override
		public Identifier getId() {
			return ID;
		}
	}
	*///?}

	private FriendsAndFoesCriterias() {
	}
}
