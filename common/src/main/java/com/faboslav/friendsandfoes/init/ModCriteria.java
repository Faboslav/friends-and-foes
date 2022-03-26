package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.advancements.criterion.TameGlareCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

public final class ModCriteria
{
	public static final TameGlareCriterion TAME_GLARE;

	static {
		TAME_GLARE = register(new TameGlareCriterion());
	}

	private static <T extends Criterion<?>> T register(T criterion) {
		return Criteria.register(criterion);
	}

	public static void init() {
	}

	private ModCriteria() {
	}
}
