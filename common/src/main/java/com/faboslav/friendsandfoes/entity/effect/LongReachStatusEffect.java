package com.faboslav.friendsandfoes.entity.effect;

import com.faboslav.friendsandfoes.init.FriendsAndFoesStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class LongReachStatusEffect extends StatusEffect
{
	public static final double REACH_MODIFIER = 1.0F;

	public LongReachStatusEffect(StatusEffectCategory statusEffectCategory, int color) {
		super(statusEffectCategory, color);
	}

	@Override
	public boolean canApplyUpdateEffect(int duration, int amplifier) {
		return false;
	}

	@Override
	public void applyUpdateEffect(LivingEntity entity, int amplifier) {
	}

	public static double getModifiedReachDistance(@Nullable LivingEntity entity, double currentReachDistance) {
		if (entity != null && entity.hasStatusEffect(FriendsAndFoesStatusEffects.LONG_REACH.get())) {
			StatusEffectInstance longReach = entity.getStatusEffect(FriendsAndFoesStatusEffects.LONG_REACH.get());
			return currentReachDistance + LongReachStatusEffect.REACH_MODIFIER + (float) longReach.getAmplifier();
		}

		return currentReachDistance;
	}

	public static double getModifiedSquaredReachDistance(@Nullable LivingEntity entity, double currentReachDistance) {
		double reachDistance = getModifiedReachDistance(entity, Math.sqrt(currentReachDistance));
		return reachDistance * reachDistance;
	}

	public static List<PlayerEntity> getPlayersWithinReach(
		final World world,
		final int x,
		final int y,
		final int z,
		final double baseReachDistance
	) {
		return getPlayersWithinReach(player -> true, world, x, y, z, baseReachDistance);
	}

	public static List<PlayerEntity> getPlayersWithinReach(
		final Predicate<PlayerEntity> viewerPredicate,
		final World world,
		final int x,
		final int y,
		final int z,
		final double baseReachDistance
	) {
		final List<PlayerEntity> playersWithinReach = new ArrayList<>(0);
		for (final PlayerEntity player : world.getPlayers()) {
			if (viewerPredicate.test(player)) {
				final var reach = getModifiedReachDistance(player, baseReachDistance);
				final var dx = (x + 0.5) - player.getX();
				final var dy = (y + 0.5) - player.getEyeY();
				final var dz = (z + 0.5) - player.getZ();
				if (((dx * dx) + (dy * dy) + (dz * dz)) <= (reach * reach)) {
					playersWithinReach.add(player);
				}
			}
		}
		return playersWithinReach;
	}
}