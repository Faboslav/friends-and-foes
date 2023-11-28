/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package com.faboslav.friendsandfoes.entity.ai.brain.sensor;

import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class CopperGolemSpecificSensor extends Sensor<CopperGolemEntity>
{
	private static final Predicate<PlayerEntity> NOTICEABLE_PLAYER_FILTER;
	private static final UniformIntProvider AVOID_MEMORY_DURATION;

	static {
		NOTICEABLE_PLAYER_FILTER = (player) -> {
			ItemStack itemStack = player.getStackInHand(Hand.MAIN_HAND);
			Item itemInHand = itemStack.getItem();

			return itemInHand instanceof AxeItem && EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR.test(player);
		};
		AVOID_MEMORY_DURATION = TimeHelper.betweenSeconds(5, 10);
	}

	@Override
	public Set<MemoryModuleType<?>> getOutputMemoryModules() {
		return ImmutableSet.of(MemoryModuleType.AVOID_TARGET);
	}

	@Override
	protected void sense(ServerWorld world, CopperGolemEntity copperGolem) {
		Brain<?> brain = copperGolem.getBrain();

		if (brain.isMemoryInState(MemoryModuleType.AVOID_TARGET, MemoryModuleState.VALUE_PRESENT)) {
			return;
		}

		List players = world.getPlayers().stream().filter(NOTICEABLE_PLAYER_FILTER).filter(player -> copperGolem.isInRange(player, 16.0)).sorted(Comparator.comparingDouble(copperGolem::squaredDistanceTo)).toList();

		if (players.isEmpty() || copperGolem.isWaxed() == false) {
			return;
		}

		brain.forget(MemoryModuleType.ATTACK_TARGET);
		brain.forget(MemoryModuleType.WALK_TARGET);
		brain.forget(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get());
		brain.remember(MemoryModuleType.AVOID_TARGET, (LivingEntity) players.get(0), AVOID_MEMORY_DURATION.get(copperGolem.getRandom()));
	}
}

