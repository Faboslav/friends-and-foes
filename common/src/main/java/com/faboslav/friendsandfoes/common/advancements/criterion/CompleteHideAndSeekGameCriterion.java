package com.faboslav.friendsandfoes.common.advancements.criterion;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public final class CompleteHideAndSeekGameCriterion extends AbstractCriterion<CompleteHideAndSeekGameCriterion.Conditions>
{
	static final Identifier ID = FriendsAndFoes.makeID("complete_hide_and_seek_game");

	public CompleteHideAndSeekGameCriterion() {
	}

	public Identifier getId() {
		return ID;
	}

	public CompleteHideAndSeekGameCriterion.Conditions conditionsFromJson(
		JsonObject jsonObject,
		LootContextPredicate extended,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		LootContextPredicate extended2 = EntityPredicate.contextPredicateFromJson(jsonObject, "rascal", advancementEntityPredicateDeserializer);

		return new CompleteHideAndSeekGameCriterion.Conditions(extended, extended2);
	}

	public void trigger(ServerPlayerEntity player, RascalEntity entity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext);
		});
	}

	public static class Conditions extends AbstractCriterionConditions
	{
		private final LootContextPredicate entity;

		public Conditions(LootContextPredicate player, LootContextPredicate entity) {
			super(CompleteHideAndSeekGameCriterion.ID, player);
			this.entity = entity;
		}

		public static CompleteHideAndSeekGameCriterion.Conditions any() {
			return new CompleteHideAndSeekGameCriterion.Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY);
		}

		public static CompleteHideAndSeekGameCriterion.Conditions create(EntityPredicate entity) {
			return new CompleteHideAndSeekGameCriterion.Conditions(LootContextPredicate.EMPTY, EntityPredicate.asLootContextPredicate(entity));
		}

		public boolean matches(LootContext entityContext) {
			return this.entity.test(entityContext);
		}

		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("entity", this.entity.toJson(predicateSerializer));
			return jsonObject;
		}
	}
}
