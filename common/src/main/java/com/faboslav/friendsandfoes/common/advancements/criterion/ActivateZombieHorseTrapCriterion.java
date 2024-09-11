package com.faboslav.friendsandfoes.common.advancements.criterion;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.LightningEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public final class ActivateZombieHorseTrapCriterion extends AbstractCriterion<ActivateZombieHorseTrapCriterion.Conditions>
{
	static final Identifier ID = FriendsAndFoes.makeID("activate_zombie_horse_trap");

	public ActivateZombieHorseTrapCriterion() {
	}

	public Identifier getId() {
		return ID;
	}

	public ActivateZombieHorseTrapCriterion.Conditions conditionsFromJson(
		JsonObject jsonObject,
		LootContextPredicate extended,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		LootContextPredicate extended2 = EntityPredicate.contextPredicateFromJson(jsonObject, "lightning", advancementEntityPredicateDeserializer);

		return new ActivateZombieHorseTrapCriterion.Conditions(extended, extended2);
	}

	public void trigger(ServerPlayerEntity player, LightningEntity lightning) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, lightning);

		this.trigger(player, (conditions) -> {
			return conditions.test(lootContext);
		});
	}

	public static class Conditions extends AbstractCriterionConditions
	{
		private final LootContextPredicate lightning;

		public Conditions(LootContextPredicate player, LootContextPredicate lightning) {
			super(ActivateZombieHorseTrapCriterion.ID, player);
			this.lightning = lightning;
		}

		public static ActivateZombieHorseTrapCriterion.Conditions create(EntityPredicate lightning) {
			return new ActivateZombieHorseTrapCriterion.Conditions(LootContextPredicate.EMPTY, EntityPredicate.asLootContextPredicate(lightning));
		}

		public boolean test(LootContext lightning) {
			return this.lightning.test(lightning) != false;
		}

		public JsonObject toJson(AdvancementEntityPredicateSerializer predicateSerializer) {
			JsonObject jsonObject = super.toJson(predicateSerializer);
			jsonObject.add("lightning", this.lightning.toJson(predicateSerializer));

			return jsonObject;
		}
	}
}
