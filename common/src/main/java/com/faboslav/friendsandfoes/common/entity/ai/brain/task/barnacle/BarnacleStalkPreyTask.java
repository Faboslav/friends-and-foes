package com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.task.Task;

public final class BarnacleStalkPreyTask extends Task<BarnacleEntity>
{
	private LivingEntity prey;

	public BarnacleStalkPreyTask() {
		super(ImmutableMap.of());
	}

	/*
	@Override
	protected boolean shouldRun(ServerWorld world, GlareEntity glare) {
		//LivingEntity prey = barnacle.getOwner();

		return true;
	}

	@Override
	protected void run(ServerWorld world, GlareEntity glare, long time) {
		this.owner = glare.getOwner();
		this.tryTeleport(glare);
	} */
}
