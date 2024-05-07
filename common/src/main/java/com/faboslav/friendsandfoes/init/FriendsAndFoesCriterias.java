package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.advancements.criterion.ActivateZombieHorseTrapCriterion;
import com.faboslav.friendsandfoes.advancements.criterion.CompleteHideAndSeekGameCriterion;
import com.faboslav.friendsandfoes.advancements.criterion.TameGlareCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

public final class FriendsAndFoesCriterias
{
	public static final TameGlareCriterion TAME_GLARE = register(new TameGlareCriterion());
	public static final ActivateZombieHorseTrapCriterion ACTIVATE_ZOMBIE_HORSE_TRAP = register(new ActivateZombieHorseTrapCriterion());
	public static final CompleteHideAndSeekGameCriterion COMPLETE_HIDE_AND_SEEK_GAME = register(new CompleteHideAndSeekGameCriterion());

	private static <T extends Criterion<?>> T register(T criterion) {
		return Criteria.register(criterion);
	}

	public static void init() {
	}

	private FriendsAndFoesCriterias() {
	}
}
