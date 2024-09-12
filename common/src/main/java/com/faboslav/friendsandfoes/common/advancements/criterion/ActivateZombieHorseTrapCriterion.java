package com.faboslav.friendsandfoes.common.advancements.criterion;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.google.gson.JsonObject;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.LightningEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public final class ActivateZombieHorseTrapCriterion extends AbstractCriterion<ActivateZombieHorseTrapCriterion.Conditions>
{
	public ActivateZombieHorseTrapCriterion() {
	}

	public ActivateZombieHorseTrapCriterion.Conditions conditionsFromJson(
		JsonObject jsonObject,
		Optional<LootContextPredicate> optional,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		Optional<LootContextPredicate> optional2 = EntityPredicate.contextPredicateFromJson(jsonObject, "lightning", advancementEntityPredicateDeserializer);
		return new ActivateZombieHorseTrapCriterion.Conditions(optional, optional2);
	}

	public void trigger(ServerPlayerEntity player, LightningEntity lightning) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, lightning);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext);
		});
	}

	public static class Conditions extends AbstractCriterionConditions
	{
		private final Optional<LootContextPredicate> lightning;

		public Conditions(Optional<LootContextPredicate> playerPredicate, Optional<LootContextPredicate> entity) {
			super(playerPredicate);
			this.lightning = entity;
		}

		public static AdvancementCriterion<ActivateZombieHorseTrapCriterion.Conditions> any() {
			return FriendsAndFoesCriterias.ACTIVATE_ZOMBIE_HORSE_TRAP.create(new ActivateZombieHorseTrapCriterion.Conditions(Optional.empty(), Optional.empty()));
		}

		public static AdvancementCriterion<ActivateZombieHorseTrapCriterion.Conditions> create(EntityPredicate.Builder builder) {
			return FriendsAndFoesCriterias.ACTIVATE_ZOMBIE_HORSE_TRAP.create(new ActivateZombieHorseTrapCriterion.Conditions(Optional.empty(), Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(builder))));
		}

		public boolean matches(LootContext entity) {
			return this.lightning.isEmpty() || this.lightning.get().test(entity);
		}

		public JsonObject toJson() {
			JsonObject jsonObject = super.toJson();
			this.lightning.ifPresent((lootContextPredicate) -> {
				jsonObject.add("lightning", lootContextPredicate.toJson());
			});
			return jsonObject;
		}
	}
}
