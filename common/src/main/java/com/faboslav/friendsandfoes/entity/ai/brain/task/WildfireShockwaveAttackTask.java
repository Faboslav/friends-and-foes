package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;

public final class WildfireShockwaveAttackTask extends Task<WildfireEntity>
{
	private final static int SHOCKWAVE_ATTACK_DURATION = 180;

	public WildfireShockwaveAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), SHOCKWAVE_ATTACK_DURATION);
	}
}
