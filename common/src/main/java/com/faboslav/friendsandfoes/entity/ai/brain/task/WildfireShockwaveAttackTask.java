package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;

public final class WildfireShockwaveAttackTask extends Task<WildfireEntity>
{
	private final static int SHOCKWAVE_DURATION = 30;
	private final static int SHOCWAVE_PHASE_ONE_END_TICK = 20;

	private int shockwaveTicks;
	private PlayerEntity nearestVisiblePlayer;

	public WildfireShockwaveAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), SHOCKWAVE_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WildfireEntity wildfire) {
		var nearestVisiblePlayer = wildfire.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);

		if(nearestVisiblePlayer.isEmpty()) {
			return false;
		}

		this.nearestVisiblePlayer = nearestVisiblePlayer.get();

		if(wildfire.distanceTo(this.nearestVisiblePlayer) > 5.0F) {
			return false;
		}

		FriendsAndFoes.getLogger().info("player is close");
		return true;
	}

	@Override
	protected void run(ServerWorld world, WildfireEntity wildfire, long time) {
		FriendsAndFoes.getLogger().info("shockwave run");
		wildfire.getNavigation().stop();
		wildfire.getBrain().forget(MemoryModuleType.WALK_TARGET);
		wildfire.getBrain().forget(MemoryModuleType.LOOK_TARGET);
		wildfire.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER);
		wildfire.playShockwaveSound();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		return this.shockwaveTicks <= SHOCKWAVE_DURATION;
	}

	@Override
	protected void keepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		if(this.shockwaveTicks < SHOCWAVE_PHASE_ONE_END_TICK) {
			wildfire.addVelocity(0.0F, 0.075F, 0.0F);
		} else if(this.shockwaveTicks == SHOCWAVE_PHASE_ONE_END_TICK) {
			wildfire.setVelocity(0.0F, 0.0F, 0.0F);
		} else {
			wildfire.addVelocity(0.0F, -1.0F, 0.0F);
		}

		wildfire.move(MovementType.SELF, wildfire.getVelocity());
		wildfire.getLookControl().lookAt(this.nearestVisiblePlayer);

		if(this.shockwaveTicks == SHOCKWAVE_DURATION) {
			FriendsAndFoes.getLogger().info("SHOCKWAVE");
		}

		this.shockwaveTicks++;
	}

	@Override
	protected void finishRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		FriendsAndFoes.getLogger().info("finishRunning");
		this.shockwaveTicks = 0;
		WildfireBrain.setShockwaveAttackCooldown(wildfire);
	}
}
