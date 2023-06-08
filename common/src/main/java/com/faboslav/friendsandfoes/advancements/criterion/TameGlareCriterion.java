package com.faboslav.friendsandfoes.advancements.criterion;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.GlareEntity;
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

public class TameGlareCriterion extends AbstractCriterion<TameGlareCriterion.Conditions> {
	static final Identifier ID = FriendsAndFoes.makeID("tame_glare");

	public TameGlareCriterion() {
	}

	public Identifier getId() {
		return ID;
	}

	public TameGlareCriterion.Conditions conditionsFromJson(JsonObject jsonObject, LootContextPredicate lootContextPredicate, AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer) {
		LootContextPredicate lootContextPredicate2 = EntityPredicate.contextPredicateFromJson(jsonObject, "entity", advancementEntityPredicateDeserializer);
		return new TameGlareCriterion.Conditions(lootContextPredicate, lootContextPredicate2);
	}

	public void trigger(ServerPlayerEntity player, GlareEntity entity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext);
		});
	}

	public static class Conditions extends AbstractCriterionConditions {
		private final LootContextPredicate entity;

		public Conditions(LootContextPredicate player, LootContextPredicate entity) {
			super(TameGlareCriterion.ID, player);
			this.entity = entity;
		}

		public static TameGlareCriterion.Conditions any() {
			return new TameGlareCriterion.Conditions(LootContextPredicate.EMPTY, LootContextPredicate.EMPTY);
		}

		public static TameGlareCriterion.Conditions create(EntityPredicate entity) {
			return new TameGlareCriterion.Conditions(LootContextPredicate.EMPTY, EntityPredicate.asLootContextPredicate(entity));
		}

		public boolean matches(LootContext tamedEntityContext) {
			return this.entity.test(tamedEntityContext);
		}

		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("entity", this.entity.toJson(predicateSerializer));
			return jsonObject;
		}
	}
}
