package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.advancements.criterion.CompleteHideAndSeekGameCriterion;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.LightningStrikeCriterion;
import net.minecraft.advancement.criterion.TameAnimalCriterion;
import net.minecraft.registry.Registries;

public final class FriendsAndFoesCriterias
{
	public static final ResourcefulRegistry<Criterion<?>> CRITERIAS = ResourcefulRegistries.create(Registries.CRITERION, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<TameAnimalCriterion> TAME_GLARE = CRITERIAS.register("tame_glare", () -> new TameAnimalCriterion());
	public static final RegistryEntry<LightningStrikeCriterion> ACTIVATE_ZOMBIE_HORSE_TRAP = CRITERIAS.register("activate_zombie_horse_trap", () -> new LightningStrikeCriterion());
	public static final RegistryEntry<CompleteHideAndSeekGameCriterion> COMPLETE_HIDE_AND_SEEK_GAME = CRITERIAS.register("complete_hide_and_seek_game", () -> new CompleteHideAndSeekGameCriterion());

	private FriendsAndFoesCriterias() {
	}
}
