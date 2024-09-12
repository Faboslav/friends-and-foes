package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.advancements.criterion.ActivateZombieHorseTrapCriterion;
import com.faboslav.friendsandfoes.common.advancements.criterion.CompleteHideAndSeekGameCriterion;
import com.faboslav.friendsandfoes.common.advancements.criterion.TameGlareCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

public final class FriendsAndFoesCriterias
{
	public static final TameGlareCriterion TAME_GLARE = register("tame_glare", new TameGlareCriterion());
	public static final ActivateZombieHorseTrapCriterion ACTIVATE_ZOMBIE_HORSE_TRAP = register("activate_zombie_horse_trap", new ActivateZombieHorseTrapCriterion());
	public static final CompleteHideAndSeekGameCriterion COMPLETE_HIDE_AND_SEEK_GAME = register("complete_hide_and_seek_game", new CompleteHideAndSeekGameCriterion());

	private static <T extends Criterion<?>> T register(String name, T criterion) {
		return Criteria.register(FriendsAndFoes.makeStringID(name), criterion);
	}

	public static void init() {
	}

	private FriendsAndFoesCriterias() {
	}
}
