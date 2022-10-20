package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;

public class WildfireBarrageAttackTask extends Task<WildfireEntity>
{
	private final static int BARRAGE_ATTACK_DURATION = 180;

	public WildfireBarrageAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), BARRAGE_ATTACK_DURATION);
	}
}
