package com.faboslav.friendsandfoes.common.advancements.criterion;

import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.advancement.AdvancementCriterion;
import net.minecraft.advancement.criterion.AbstractCriterion;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.context.LootContext;
import net.minecraft.predicate.entity.EntityPredicate;
import net.minecraft.predicate.entity.LootContextPredicate;
import net.minecraft.predicate.entity.LootContextPredicateValidator;
import net.minecraft.predicate.item.ItemPredicate;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Optional;

public final class CompleteHideAndSeekGameCriterion extends AbstractCriterion<CompleteHideAndSeekGameCriterion.Conditions>
{
	public CompleteHideAndSeekGameCriterion() {
	}

	public Codec<CompleteHideAndSeekGameCriterion.Conditions> getConditionsCodec() {
		return CompleteHideAndSeekGameCriterion.Conditions.CODEC;
	}

	public void trigger(ServerPlayerEntity player, RascalEntity rascal, ItemStack stack) {
		LootContext lootContext = EntityPredicate.createAdvancementEntityLootContext(player, rascal);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext, stack);
		});
	}

	public record Conditions(Optional<LootContextPredicate> player, Optional<LootContextPredicate> rascal,
							 Optional<ItemPredicate> item) implements AbstractCriterion.Conditions
	{
		public static final Codec<CompleteHideAndSeekGameCriterion.Conditions> CODEC = RecordCodecBuilder.create((instance) -> {
			return instance.group(EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("player").forGetter(CompleteHideAndSeekGameCriterion.Conditions::player), EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC.optionalFieldOf("rascal").forGetter(CompleteHideAndSeekGameCriterion.Conditions::rascal), ItemPredicate.CODEC.optionalFieldOf("item").forGetter(CompleteHideAndSeekGameCriterion.Conditions::item)).apply(instance, CompleteHideAndSeekGameCriterion.Conditions::new);
		});

		public static AdvancementCriterion<Conditions> any() {
			return FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get().create(new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.empty(), Optional.empty()));
		}

		public static AdvancementCriterion<Conditions> create(EntityPredicate.Builder playerPredicate) {
			return FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get().create(new CompleteHideAndSeekGameCriterion.Conditions(Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(playerPredicate)), Optional.empty(), Optional.empty()));
		}

		public boolean matches(LootContext rascal, ItemStack stack) {
			if (this.rascal.isPresent() && !this.rascal.get().test(rascal)) {
				return false;
			} else {
				return !this.item.isPresent() || this.item.get().test(stack);
			}
		}

		public void validate(LootContextPredicateValidator validator) {
			AbstractCriterion.Conditions.super.validate(validator);
			validator.validateEntityPredicate(this.rascal, ".rascal");
		}

		public Optional<LootContextPredicate> player() {
			return this.player;
		}

		public Optional<LootContextPredicate> rascal() {
			return this.rascal;
		}

		public Optional<ItemPredicate> item() {
			return this.item;
		}
	}
}
