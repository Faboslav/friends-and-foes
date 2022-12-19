package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.advancements.criterion.ActivateZombieHorseTrapCriterion;
import com.faboslav.friendsandfoes.advancements.criterion.TameGlareCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

public final class FriendsAndFoesCriteria
{
	public static final TameGlareCriterion TAME_GLARE;
	public static final ActivateZombieHorseTrapCriterion ACTIVATE_ZOMBIE_HORSE_TRAP;

	static {
		TAME_GLARE = register(new TameGlareCriterion());
		ACTIVATE_ZOMBIE_HORSE_TRAP = register(new ActivateZombieHorseTrapCriterion());
	}

	private static <T extends Criterion<?>> T register(T criterion) {
		return Criteria.register(criterion);
	}

	public static void init() {
	}

	private FriendsAndFoesCriteria() {
	}
}
