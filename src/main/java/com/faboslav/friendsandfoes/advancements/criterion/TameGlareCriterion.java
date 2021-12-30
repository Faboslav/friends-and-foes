package com.faboslav.friendsandfoes.advancements.criterion;

import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
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

public class TameGlareCriterion extends AbstractCriterion<TameGlareCriterion.Conditions>
{
	static final Identifier ID = Settings.makeID("tame_glare");

	public TameGlareCriterion() {
	}

	public Identifier getId() {
		return ID;
	}

	public TameGlareCriterion.Conditions conditionsFromJson(
		JsonObject jsonObject,
		Extended extended,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		Extended extended2 = Extended.getInJson(jsonObject, "entity", advancementEntityPredicateDeserializer);
		return new TameGlareCriterion.Conditions(extended, extended2);
	}

	public void trigger(ServerPlayerEntity player, GlareEntity entity) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, entity);
		System.out.println("loot context");
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

		public static TameGlareCriterion.Conditions any() {
			System.out.println("any");
			return new TameGlareCriterion.Conditions(Extended.EMPTY, Extended.EMPTY);
		}

		public static TameGlareCriterion.Conditions create(EntityPredicate entity) {
			System.out.println("Creating");
			return new TameGlareCriterion.Conditions(Extended.EMPTY, Extended.ofLegacy(entity));
		}

		public boolean matches(LootContext tamedEntityContext) {
			System.out.println("matches");
			return this.entity.test(tamedEntityContext);
		}

		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			System.out.println("json");
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("entity", this.entity.toJson(predicateSerializer));
			return jsonObject;
		}
	}
}
