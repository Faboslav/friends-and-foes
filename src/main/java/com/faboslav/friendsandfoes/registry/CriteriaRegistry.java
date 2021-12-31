package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.advancements.criterion.TameGlareCriterion;
import net.fabricmc.fabric.api.object.builder.v1.advancement.CriterionRegistry;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;

/**
 * @see Criteria
 */
public class CriteriaRegistry
{
	public static final TameGlareCriterion TAME_GLARE = register(new TameGlareCriterion());

	private static <T extends Criterion<?>> T register(T criterion) {
		return CriterionRegistry.register(criterion);
	}

	public static void init() {
	}
}
