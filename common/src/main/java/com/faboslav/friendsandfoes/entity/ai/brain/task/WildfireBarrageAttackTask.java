package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.server.world.ServerWorld;

public class WildfireBarrageAttackTask extends Task<WildfireEntity>
{
	private int fireballsFired;

	private final static int BARRAGE_ATTACK_DURATION = 180;

	@Override
	protected boolean shouldRun(ServerWorld world, WildfireEntity wildfire) {
		return false;
	}

	public WildfireBarrageAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), BARRAGE_ATTACK_DURATION);
	}

	@Override
	protected void run(ServerWorld world, WildfireEntity wildfire, long time) {
		FriendsAndFoes.getLogger().info("run");
		wildfire.getNavigation().stop();
		wildfire.getBrain().forget(MemoryModuleType.WALK_TARGET);
		wildfire.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		this.fireballsFired = 0;
	}
}
