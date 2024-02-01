/*
 * Decompiled with CFR 0.2.1 (FabricMC 53fa44c9).
 */
package com.faboslav.friendsandfoes.entity.ai.brain.sensor;

import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.google.common.collect.ImmutableSet;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.brain.LivingTargetCache;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.sensor.Sensor;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Hand;
import net.minecraft.util.TimeHelper;
import net.minecraft.util.math.intprovider.UniformIntProvider;

import java.util.Set;
import java.util.function.Predicate;

public class GlareSpecificSensor extends Sensor<GlareEntity>
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
	protected void sense(ServerWorld world, GlareEntity glare) {
		Brain<?> brain = glare.getBrain();
		LivingTargetCache livingTargetCache = brain.getOptionalMemory(MemoryModuleType.VISIBLE_MOBS).orElse(LivingTargetCache.empty());
		LivingEntity firstHostileEntity = livingTargetCache.findFirst(livingEntity -> livingEntity instanceof HostileEntity).orElse(null);

		if (firstHostileEntity == null || glare.isTamed()) {
			return;
		}

		brain.remember(MemoryModuleType.AVOID_TARGET, firstHostileEntity, AVOID_MEMORY_DURATION.get(glare.getRandom()));
	}
}

