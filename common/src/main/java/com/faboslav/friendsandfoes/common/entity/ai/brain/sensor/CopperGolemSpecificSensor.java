/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package com.faboslav.friendsandfoes.common.entity.ai.brain.sensor;

import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableSet;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.TimeUtil;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.Brain;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.ai.sensing.Sensor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class CopperGolemSpecificSensor extends Sensor<CopperGolemEntity>
{
	private static final Predicate<Player> NOTICEABLE_PLAYER_FILTER;
	private static final UniformInt AVOID_MEMORY_DURATION;

	static {
		NOTICEABLE_PLAYER_FILTER = (player) -> {
			ItemStack itemStack = player.getItemInHand(InteractionHand.MAIN_HAND);
			Item itemInHand = itemStack.getItem();

			return itemInHand instanceof AxeItem && EntitySelector.NO_CREATIVE_OR_SPECTATOR.test(player);
		};
		AVOID_MEMORY_DURATION = TimeUtil.rangeOfSeconds(5, 10);
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return ImmutableSet.of(MemoryModuleType.AVOID_TARGET);
	}

	@Override
	protected void doTick(ServerLevel world, CopperGolemEntity copperGolem) {
		Brain<?> brain = copperGolem.getBrain();

		if (brain.checkMemory(MemoryModuleType.AVOID_TARGET, MemoryStatus.VALUE_PRESENT)) {
			return;
		}

		List players = world.players().stream().filter(NOTICEABLE_PLAYER_FILTER).filter(player -> copperGolem.closerThan(player, 16.0)).sorted(Comparator.comparingDouble(copperGolem::distanceToSqr)).toList();

		if (players.isEmpty() || !copperGolem.isWaxed()) {
			return;
		}

		brain.eraseMemory(MemoryModuleType.ATTACK_TARGET);
		brain.eraseMemory(MemoryModuleType.WALK_TARGET);
		brain.eraseMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get());
		brain.setMemoryWithExpiry(MemoryModuleType.AVOID_TARGET, (LivingEntity) players.get(0), AVOID_MEMORY_DURATION.sample(copperGolem.getRandom()));
	}
}

