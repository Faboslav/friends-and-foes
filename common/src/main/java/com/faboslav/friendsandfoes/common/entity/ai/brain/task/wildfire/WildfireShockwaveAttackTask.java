package com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire;

import com.faboslav.friendsandfoes.common.entity.animation.WildfireAnimations;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.common.entity.pose.WildfireEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.common.collect.ImmutableMap;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class WildfireShockwaveAttackTask extends Behavior<WildfireEntity>
{
	private final static int SHOCKWAVE_DURATION = WildfireAnimations.SHOCKWAVE.get().lengthInTicks();
	public final static float SHOCKWAVE_ATTACK_RANGE = 6.0F;

	private int shockwaveTicks;
	private LivingEntity attackTarget;

	public WildfireShockwaveAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER, MemoryStatus.VALUE_PRESENT,
			MemoryModuleType.ATTACK_TARGET, MemoryStatus.REGISTERED,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
		), SHOCKWAVE_DURATION);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, WildfireEntity wildfire) {
		LivingEntity nearestTarget = wildfire.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER).orElse(null);

		if (nearestTarget == null) {
			nearestTarget = wildfire.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);
		}
		if (
			nearestTarget == null
			|| wildfire.distanceTo(nearestTarget) > SHOCKWAVE_ATTACK_RANGE
			|| !nearestTarget.isAlive()
			|| (
				nearestTarget instanceof Player
				&& (
					nearestTarget.isSpectator()
					|| ((Player) nearestTarget).isCreative()
				)
			)
		) {
			return false;
		}

		this.attackTarget = nearestTarget;

		return true;
	}

	@Override
	protected void start(ServerLevel world, WildfireEntity wildfire, long time) {
		wildfire.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		wildfire.getNavigation().stop();

		BehaviorUtils.lookAtEntity(wildfire, this.attackTarget);
		wildfire.getLookControl().setLookAt(this.attackTarget);
		WildfireBrain.setAttackTarget(wildfire, this.attackTarget);
		wildfire.startShockwaveAnimation();

		this.shockwaveTicks = 0;
	}

	@Override
	protected boolean canStillUse(ServerLevel world, WildfireEntity wildfire, long time) {
		return this.shockwaveTicks <= SHOCKWAVE_DURATION;
	}

	@Override
	protected void tick(ServerLevel serverLevel, WildfireEntity wildfire, long time) {
		wildfire.getLookControl().setLookAt(this.attackTarget);

		if (this.shockwaveTicks == 20) {
			var closeEntities = wildfire.level().getEntities(
				wildfire,
				wildfire.getBoundingBox().inflate(SHOCKWAVE_ATTACK_RANGE),
				EntitySelector.NO_CREATIVE_OR_SPECTATOR
			);

			for (Entity closeEntity : closeEntities) {
				if (closeEntity.getType().is(FriendsAndFoesTags.WILDFIRE_ALLIES)) {
					continue;
				}

				this.spawnShockwaveParticles(wildfire);
				wildfire.doHurtTarget(/*? if >=1.21.3 {*/serverLevel, /*?}*/closeEntity);
			}
		}

		this.shockwaveTicks++;
	}

	private void spawnShockwaveParticles(WildfireEntity wildfire) {
		Vec3 wildfirePosition = wildfire.position();
		ServerLevel serverWorld = (ServerLevel) wildfire.level();

		int radius = 1;
		int waveAmount = (int) SHOCKWAVE_ATTACK_RANGE;
		int particleAmount = 48;

		float slice = 2.0F * (float) Math.PI / particleAmount;

		for (int currentWave = 1; currentWave < waveAmount; currentWave++) {
			for (int particleNumber = 0; particleNumber < particleAmount; ++particleNumber) {
				float angle = slice * particleNumber;
				int x = (int) (wildfirePosition.x() + radius * Mth.cos(angle));
				int y = (int) wildfirePosition.y();
				int z = (int) (wildfirePosition.z() + radius * Mth.sin(angle));

				serverWorld.sendParticles(
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

			radius++;
		}
	}

	@Override
	protected void stop(ServerLevel world, WildfireEntity wildfire, long time) {
		wildfire.setPose(WildfireEntityPose.IDLE);
		WildfireBrain.setShockwaveAttackCooldown(wildfire);
	}
}
