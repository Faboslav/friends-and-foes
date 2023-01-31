package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.TuffGolemEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;

import java.util.Map;

public class TuffGolemGoToSleepPositionTask extends Task<TuffGolemEntity>
{
	public TuffGolemGoToSleepPositionTask(Map<MemoryModuleType<?>, MemoryModuleState> requiredMemoryState) {
		super(requiredMemoryState);
	}
}
