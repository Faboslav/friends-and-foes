package com.faboslav.friendsandfoes.common.advancements.criterion;

import com.faboslav.friendsandfoes.common.entity.RascalEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesCriterias;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.criterion.ContextAwarePredicate;
import net.minecraft.advancements.criterion.CriterionValidator;
import net.minecraft.advancements.criterion.EntityPredicate;
import net.minecraft.advancements.criterion.ItemPredicate;
import net.minecraft.advancements.criterion.SimpleCriterionTrigger;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public final class CompleteHideAndSeekGameCriterion extends SimpleCriterionTrigger<CompleteHideAndSeekGameCriterion.Conditions>
{
	public CompleteHideAndSeekGameCriterion() {
	}

	public Codec<CompleteHideAndSeekGameCriterion.Conditions> codec() {
		return CompleteHideAndSeekGameCriterion.Conditions.CODEC;
	}

	public void trigger(ServerPlayer player, RascalEntity rascal, ItemStack stack) {
		LootContext lootContext = EntityPredicate.createContext(player, rascal);
		this.trigger(player, (conditions) -> {
			return conditions.matches(lootContext, stack);
		});
	}

	public record Conditions(Optional<ContextAwarePredicate> player, Optional<ContextAwarePredicate> rascal,
							 Optional<ItemPredicate> item) implements SimpleCriterionTrigger.SimpleInstance
	{
		public static final Codec<CompleteHideAndSeekGameCriterion.Conditions> CODEC = RecordCodecBuilder.create((instance) -> {
			return instance.group(EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("player").forGetter(CompleteHideAndSeekGameCriterion.Conditions::player), EntityPredicate.ADVANCEMENT_CODEC.optionalFieldOf("rascal").forGetter(CompleteHideAndSeekGameCriterion.Conditions::rascal), ItemPredicate.CODEC.optionalFieldOf("item").forGetter(CompleteHideAndSeekGameCriterion.Conditions::item)).apply(instance, CompleteHideAndSeekGameCriterion.Conditions::new);
		});

		public static Criterion<Conditions> any() {
			return FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get().createCriterion(new CompleteHideAndSeekGameCriterion.Conditions(Optional.empty(), Optional.empty(), Optional.empty()));
		}

		public static Criterion<Conditions> create(EntityPredicate.Builder playerPredicate) {
			return FriendsAndFoesCriterias.COMPLETE_HIDE_AND_SEEK_GAME.get().createCriterion(new CompleteHideAndSeekGameCriterion.Conditions(Optional.of(EntityPredicate.wrap(playerPredicate)), Optional.empty(), Optional.empty()));
		}

		public boolean matches(LootContext rascal, ItemStack stack) {
			if (this.rascal.isPresent() && !this.rascal.get().matches(rascal)) {
				return false;
			} else {
				return !this.item.isPresent() || this.item.get().test(stack);
			}
		}

		public void validate(CriterionValidator validator) {
			SimpleCriterionTrigger.SimpleInstance.super.validate(validator);
			validator.validateEntity(this.rascal, ".rascal");
		}

		public Optional<ContextAwarePredicate> player() {
			return this.player;
		}

		public Optional<ContextAwarePredicate> rascal() {
			return this.rascal;
		}

		public Optional<ItemPredicate> item() {
			return this.item;
		}
	}
}
