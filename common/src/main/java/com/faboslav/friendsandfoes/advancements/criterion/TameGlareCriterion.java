package com.faboslav.friendsandfoes.advancements.criterion;

import com.faboslav.friendsandfoes.entity.GlareEntity;
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

public class TameGlareCriterion extends AbstractCriterion<TameGlareCriterion.Conditions>
{
	public TameGlareCriterion() {
	}

	public TameGlareCriterion.Conditions conditionsFromJson(
		JsonObject jsonObject,
		Optional<LootContextPredicate> optional,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		Optional<LootContextPredicate> optional2 = EntityPredicate.contextPredicateFromJson(jsonObject, "entity", advancementEntityPredicateDeserializer);
		return new TameGlareCriterion.Conditions(optional, optional2);
	}

	public void trigger(ServerPlayerEntity player, GlareEntity entity) {
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

		public static AdvancementCriterion<TameGlareCriterion.Conditions> any() {
			return FriendsAndFoesCriteria.TAME_GLARE.create(new TameGlareCriterion.Conditions(Optional.empty(), Optional.empty()));
		}

		public static AdvancementCriterion<TameGlareCriterion.Conditions> create(EntityPredicate.Builder builder) {
			return FriendsAndFoesCriteria.TAME_GLARE.create(new TameGlareCriterion.Conditions(Optional.empty(), Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(builder))));
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
