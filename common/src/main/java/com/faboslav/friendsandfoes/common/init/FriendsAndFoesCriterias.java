package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.advancements.criterion.CompleteHideAndSeekGameCriterion;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.minecraft.advancements.CriterionTrigger;
import net.minecraft.advancements.critereon.LightningStrikeTrigger;
import net.minecraft.advancements.critereon.TameAnimalTrigger;
import net.minecraft.core.registries.BuiltInRegistries;

public final class FriendsAndFoesCriterias
{
	public static final ResourcefulRegistry<CriterionTrigger<?>> CRITERIAS = ResourcefulRegistries.create(BuiltInRegistries.TRIGGER_TYPES, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<TameAnimalTrigger> TAME_GLARE = CRITERIAS.register("tame_glare", () -> new TameAnimalTrigger());
	public static final RegistryEntry<LightningStrikeTrigger> ACTIVATE_ZOMBIE_HORSE_TRAP = CRITERIAS.register("activate_zombie_horse_trap", () -> new LightningStrikeTrigger());
	public static final RegistryEntry<CompleteHideAndSeekGameCriterion> COMPLETE_HIDE_AND_SEEK_GAME = CRITERIAS.register("complete_hide_and_seek_game", () -> new CompleteHideAndSeekGameCriterion());

	private FriendsAndFoesCriterias() {
	}
}
