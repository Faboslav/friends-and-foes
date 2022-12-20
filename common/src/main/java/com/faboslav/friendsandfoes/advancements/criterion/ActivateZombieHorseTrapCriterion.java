package com.faboslav.friendsandfoes.advancements.criterion;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.google.gson.JsonObject;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.advancement.criterion.AbstractCriterionConditions;
import net.minecraft.entity.LightningEntity;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.AdvancementEntityPredicateDeserializer;
import net.minecraft.predicate.entity.AdvancementEntityPredicateSerializer;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.EntityPredicate.Extended;
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
		EntityPredicate.Extended extended,
		AdvancementEntityPredicateDeserializer advancementEntityPredicateDeserializer
	) {
		EntityPredicate.Extended extended2 = Extended.getInJson(jsonObject, "lightning", advancementEntityPredicateDeserializer);

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
		private final EntityPredicate.Extended lightning;

		public Conditions(EntityPredicate.Extended player, EntityPredicate.Extended lightning) {
			super(ActivateZombieHorseTrapCriterion.ID, player);
			this.lightning = lightning;
		}

		public static ActivateZombieHorseTrapCriterion.Conditions create(EntityPredicate lightning) {
			return new ActivateZombieHorseTrapCriterion.Conditions(Extended.EMPTY, Extended.ofLegacy(lightning));
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
