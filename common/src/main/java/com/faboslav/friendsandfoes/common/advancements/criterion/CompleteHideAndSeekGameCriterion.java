package com.faboslav.friendsandfoes.common.advancements.criterion;

import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import java.util.Optional;
import net.minecraft.advancements.predicates.ContextAwarePredicate;
import net.minecraft.advancements.predicates.entity.EntityPredicate;
import net.minecraft.advancements.predicates.ItemPredicate;
import net.minecraft.advancements.triggers.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

//? if >= 1.21.1 {
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancements.triggers.Criterion;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
//?} else {
/*import com.google.gson.JsonObject;
import net.minecraft.advancements.critereon.AbstractCriterionTriggerInstance;
import net.minecraft.advancements.critereon.DeserializationContext;
import net.minecraft.advancements.critereon.SerializationContext;
import net.minecraft.resources.Identifier;
import com.faboslav.friendsandfoes.common.FriendsAndFoes;
*///?}

//? if >= 1.21.1 && <= 1.21.11 {
/*import net.minecraft.advancements.criterion.CriterionValidator;
*///?}

public final class CompleteHideAndSeekGameCriterion extends SimpleCriterionTrigger<CompleteHideAndSeekGameCriterion.Conditions>
{
	//? if < 1.21.1 {
	/*static final Identifier ID = FriendsAndFoes.makeID("complete_hide_and_seek_game");
	*///?}

	public CompleteHideAndSeekGameCriterion() {
	}

	//? if >= 1.21.1 {
	public Codec<CompleteHideAndSeekGameCriterion.Conditions> codec() {
		return CompleteHideAndSeekGameCriterion.Conditions.CODEC;
	}
	//?} else {
	/*public Identifier getId() {
		return ID;
	}

	public CompleteHideAndSeekGameCriterion.Conditions createInstance(JsonObject json, ContextAwarePredicate player, DeserializationContext context) {
		Optional<ContextAwarePredicate> rascal = json.has("rascal") ? Optional.of(EntityPredicate.fromJson(json, "rascal", context)) : Optional.empty();
		Optional<ItemPredicate> item = json.has("item") ? Optional.of(ItemPredicate.fromJson(json.get("item"))) : Optional.empty();
		return new CompleteHideAndSeekGameCriterion.Conditions(Optional.of(player), rascal, item);
	}
	*///?}

	public void trigger(ServerPlayer player, RascalEntity rascal, ItemStack stack) {
		LootContext lootContext = EntityPredicate.createContext(player, rascal);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext, stack);
		});
	}

	//? if >= 1.21.1 {
	public record Conditions(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> rascal,
							 Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance
	//?} else {
	/*public static class Conditions extends AbstractCriterionTriggerInstance
	*///?}
	{
		//? if < 1.21.1 {
		/*private final Optional<ContextAwarePredicate> rascal;
		private final Optional<ItemPredicate> item;

		public Conditions(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> rascal, Optional<ItemPredicate> item) {
			super(CompleteHideAndSeekGameCriterion.ID, player.orElse(ContextAwarePredicate.ANY));
			this.rascal = rascal;
			this.item = item;
		}
		*///?}

		//? if >= 1.21.1 {
		public static final Codec<CompleteHideAndSeekGameCriterion.Conditions> CODEC = RecordCodecBuilder.create((instance) -> {
			return instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CompleteHideAndSeekGameCriterion.Conditions::player), EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("rascal").forGetter(CompleteHideAndSeekGameCriterion.Conditions::rascal), ItemPredicate.CODEC.optionalFieldOf("item").forGetter(CompleteHideAndSeekGameCriterion.Conditions::item)).apply(instance, CompleteHideAndSeekGameCriterion.Conditions::new);
		});

		public static Criterion<Conditions> any() {
			return FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get().createCriterion(new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.empty(), Optional.empty()));
		}

		public static Criterion<Conditions> create(EntityPredicate.Builder playerPredicate) {
			return FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get().createCriterion(new CompleteHideAndSeekGameCriterion.Conditions(Optional.of(EntityPredicate.wrap(playerPredicate)), Optional.empty(), Optional.empty()));
		}
		//?} else {
		/*public static CompleteHideAndSeekGameCriterion.Conditions any() {
			return new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.empty(), Optional.empty());
		}

		public static CompleteHideAndSeekGameCriterion.Conditions create(EntityPredicate.Builder playerPredicate) {
			return new CompleteHideAndSeekGameCriterion.Conditions(Optional.of(EntityPredicate.wrap(playerPredicate.build())), Optional.empty(), Optional.empty());
		}
		*///?}

		public boolean matches(LootContext rascal, ItemStack stack) {
			if (this.rascal.isPresent() && !this.rascal.get().matches(rascal)) {
				return false;
			} else {
				//? if >= 1.21.1 {
				return !this.item.isPresent() || this.item.get().test(stack);
				//?} else {
				/*return !this.item.isPresent() || this.item.get().matches(stack);
				*///?}
			}
		}

		//? if >= 1.21.1 && <= 1.21.11 {
		/*public void validate(CriterionValidator validator) {
			SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
			validator.validateEntity(this.rascal, ".rascal");
		}
		*///?}

		//? if < 1.21.1 {
		/*public JsonObject serializeToJson(SerializationContext context) {
			JsonObject json = super.serializeToJson(context);
			this.rascal.ifPresent((predicate) -> json.add("rascal", predicate.toJson(context)));
			this.item.ifPresent((predicate) -> json.add("item", predicate.serializeToJson()));
			return json;
		}
		*///?}

		public Optional<ContextAwarePredicate> player() {
			//? if >= 1.21.1 {
			return this.player;
			//?} else {
			/*return Optional.of(this.getPlayerPredicate());
			*///?}
		}

		public Optional<ContextAwarePredicate> rascal() {
			return this.rascal;
		}

		public Optional<ItemPredicate> item() {
			return this.item;
		}
	}
}
