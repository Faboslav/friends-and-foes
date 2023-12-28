package com.faboslav.friendsandfoes.advancements.criterion;

import com.faboslav.friendsandfoes.entity.RascalEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesCriteria;
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
import net.minecraft.util.dynamic.Codecs;

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
			return instance.group(Codecs.createStrictOptionalFieldCodec(EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC, "player").forGetter(CompleteHideAndSeekGameCriterion.Conditions::player), Codecs.createStrictOptionalFieldCodec(EntityPredicate.LOOT_CONTEXT_PREDICATE_CODEC, "rascal").forGetter(CompleteHideAndSeekGameCriterion.Conditions::rascal), Codecs.createStrictOptionalFieldCodec(ItemPredicate.CODEC, "item").forGetter(CompleteHideAndSeekGameCriterion.Conditions::item)).apply(instance, CompleteHideAndSeekGameCriterion.Conditions::new);
		});

		public static AdvancementCriterion<Conditions> any() {
			return FriendsAndFoesCriteria.COMPLETE_HIDE_AND_SEEK_GAME.create(new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.empty(), Optional.empty()));
		}

		public static AdvancementCriterion<Conditions> create(EntityPredicate.Builder playerPredicate) {
			return FriendsAndFoesCriteria.COMPLETE_HIDE_AND_SEEK_GAME.create(new CompleteHideAndSeekGameCriterion.Conditions(Optional.of(EntityPredicate.contextPredicateFromEntityPredicate(playerPredicate)), Optional.empty(), Optional.empty()));
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
