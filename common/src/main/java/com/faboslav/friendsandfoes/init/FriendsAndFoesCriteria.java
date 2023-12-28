package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.advancements.criterion.CompleteHideAndSeekGameCriterion;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.advancement.criterion.Criterion;
import net.minecraft.advancement.criterion.LightningStrikeCriterion;
import net.minecraft.advancement.criterion.TameAnimalCriterion;

public final class FriendsAndFoesCriteria
{
	public static final TameAnimalCriterion TAME_GLARE;
	public static final LightningStrikeCriterion ACTIVATE_ZOMBIE_HORSE_TRAP;
	public static final CompleteHideAndSeekGameCriterion COMPLETE_HIDE_AND_SEEK_GAME;

	static {
		TAME_GLARE = register("tame_glare", new TameAnimalCriterion());
		ACTIVATE_ZOMBIE_HORSE_TRAP = register("activate_zombie_horse_trap", new LightningStrikeCriterion());
		COMPLETE_HIDE_AND_SEEK_GAME = register("complete_hide_and_seek_game", new CompleteHideAndSeekGameCriterion());
	}

	private static <T extends Criterion<?>> T register(String name, T criterion) {
		return Criteria.register(FriendsAndFoes.makeStringID(name), criterion);
	}

	public static void init() {
	}

	private FriendsAndFoesCriteria() {
	}
}
