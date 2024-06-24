package com.faboslav.friendsandfoes.advancements.criterion;

import com.faboslav.friendsandfoes.entity.RascalEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesCriteria;
import com.google.gson.JsonObject;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public final class CompleteHideAndSeekGameCriterion extends AbstractCriterion<CompleteHideAndSeekGameCriterion.Conditions>
{
	public CompleteHideAndSeekGameCriterion() {
	}

	public CompleteHideAndSeekGameCriterion.Conditions conditionsFromJson(
		JsonObject jsonObject,
		Optional<LootContextPredicate> optional,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		Optional<LootContextPredicate> optional2 = EntityPredicate.contextPredicateFromJson(jsonObject, "entity", advancementEntityPredicateDeserializer);
		return new CompleteHideAndSeekGameCriterion.Conditions(optional, optional2);
	}

	public void trigger(ServerPlayerEntity player, RascalEntity entity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext);
		});
	}

	public static class Conditions extends AbstractCriterionConditions
	{
		private final Optional<LootContextPredicate> entity;

		public Conditions(Optional<LootContextPredicate> playerPredicate, Optional<LootContextPredicate> entity) {
			super(playerPredicate);
			this.entity = entity;
		}

		public static AdvancementCriterion<CompleteHideAndSeekGameCriterion.Conditions> any() {
			return FriendsAndFoesCriteria.COMPLETE_HIDE_AND_SEEK_GAME.create(new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.empty()));
		}

		public static AdvancementCriterion<CompleteHideAndSeekGameCriterion.Conditions> create(EntityPredicate.Builder builder) {
			return FriendsAndFoesCriteria.COMPLETE_HIDE_AND_SEEK_GAME.create(new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(builder))));
		}

		public boolean matches(LootContext entity) {
			return this.entity.isEmpty() || this.entity.get().test(entity);
		}

		public JsonObject toJson() {
			JsonObject jsonObject = super.toJson();
			this.entity.ifPresent((lootContextPredicate) -> {
				jsonObject.add("entity", lootContextPredicate.toJson());
			});
			return jsonObject;
		}
	}
}
