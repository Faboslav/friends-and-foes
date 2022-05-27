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
import net.minecraft.predicate.entity.EntityPredicate.Extended;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public final class TameGlareCriterion extends AbstractCriterion<TameGlareCriterion.Conditions>
{
	static final Identifier ID = FriendsAndFoes.makeID("tame_glare");

	public TameGlareCriterion() {
	}

	public Identifier getId() {
		return ID;
	}

	public Conditions conditionsFromJson(
		JsonObject jsonObject,
		Extended extended,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		Extended extended2 = Extended.getInJson(jsonObject, "entity", advancementEntityPredicateDeserializer);
		return new Conditions(extended, extended2);
	}

	public void trigger(ServerPlayerEntity player, GlareEntity entity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext);
		});
	}

	public static class Conditions extends AbstractCriterionConditions
	{
		private final Extended entity;

		public Conditions(Extended player, Extended entity) {
			super(TameGlareCriterion.ID, player);
			this.entity = entity;
		}

		public static Conditions any() {
			return new Conditions(Extended.EMPTY, Extended.EMPTY);
		}

		public static Conditions create(EntityPredicate entity) {
			return new Conditions(Extended.EMPTY, Extended.ofLegacy(entity));
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
