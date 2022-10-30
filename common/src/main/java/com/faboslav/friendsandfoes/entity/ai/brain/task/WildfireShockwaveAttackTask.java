package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.mob.BlazeEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class WildfireShockwaveAttackTask extends Task<WildfireEntity>
{
	private final static int SHOCKWAVE_DURATION = 20;
	private final static int SHOCKWAVE_PHASE_ONE_END_TICK = 15;
	public final static float SHOCKWAVE_ATTACK_RANGE = 8.0F;

	private int shockwaveTicks;
	private PlayerEntity targetPlayer;

	public WildfireShockwaveAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), SHOCKWAVE_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WildfireEntity wildfire) {
		var targetPlayer = wildfire.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(null);

		if (
			targetPlayer == null
			|| wildfire.distanceTo(targetPlayer) > SHOCKWAVE_ATTACK_RANGE
			|| targetPlayer.isAlive() == false
			|| wildfire.canTarget(targetPlayer) == false
		) {
			return false;
		}

		this.targetPlayer = targetPlayer;

		return true;
	}

	@Override
	protected void run(ServerWorld world, WildfireEntity wildfire, long time) {
		wildfire.getBrain().forget(MemoryModuleType.WALK_TARGET);
		WildfireBrain.setAttackTarget(wildfire, this.targetPlayer);
		LookTargetUtil.lookAt(wildfire, this.targetPlayer);

		wildfire.getNavigation().stop();
		wildfire.playShockwaveSound();
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		return this.shockwaveTicks <= SHOCKWAVE_DURATION;
	}

	@Override
	protected void keepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		if (this.shockwaveTicks < SHOCKWAVE_PHASE_ONE_END_TICK) {
			wildfire.addVelocity(0.0F, 0.075F, 0.0F);
		} else if (this.shockwaveTicks == SHOCKWAVE_PHASE_ONE_END_TICK) {
			wildfire.setVelocity(0.0F, 0.0F, 0.0F);
		} else {
			wildfire.addVelocity(0.0F, -1.0F, 0.0F);
		}

		wildfire.move(MovementType.SELF, wildfire.getVelocity());
		wildfire.getLookControl().lookAt(this.targetPlayer);

		if (this.shockwaveTicks == SHOCKWAVE_DURATION) {
			var closeEntities = wildfire.getWorld().getOtherEntities(
				wildfire,
				wildfire.getBoundingBox().expand(SHOCKWAVE_ATTACK_RANGE),
				EntityPredicates.EXCEPT_CREATIVE_OR_SPECTATOR
			);

			for (Entity closeEntity : closeEntities) {
				if (closeEntity instanceof BlazeEntity) {
					continue;
				}

				this.spawnShockwaveParticles(wildfire);
				wildfire.tryAttack(closeEntity);
			}
		}

		this.shockwaveTicks++;
	}

	private void spawnShockwaveParticles(WildfireEntity wildfire) {
		Vec3d wildfirePosition = wildfire.getPos();
		ServerWorld serverWorld = (ServerWorld) wildfire.getWorld();

		int waveAmount = 3;
		int particleAmount = 64;

		float slice = 2.0F * (float) Math.PI / particleAmount;

		for (int radius = 1; radius < waveAmount; radius++) {
			for (int particleNumber = 0; particleNumber < particleAmount; ++particleNumber) {
				float angle = slice * particleNumber;
				int x = (int) (wildfirePosition.getX() + radius * MathHelper.cos(angle));
				int y = (int) wildfirePosition.getY();
				int z = (int) (wildfirePosition.getZ() + radius * MathHelper.sin(angle));

				serverWorld.spawnParticles(
					ParticleTypes.LARGE_SMOKE,
					x,
					y + 0.1F * radius,
					z,
					1,
					0.0D,
					0.0D,
					0.0D,
					0.0D
				);
			}
		}
	}

	@Override
	protected void finishRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		this.shockwaveTicks = 0;
		WildfireBrain.setShockwaveAttackCooldown(wildfire);
	}
}
