package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.Task;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class WildfireBarrageAttackTask extends Task<WildfireEntity>
{
	private int fireballsFired;
	private int fireballCooldown;
	private LivingEntity attackTarget;
	private int attackTargetIsNotVisibleTicks;

	private final static int BARRAGE_ATTACK_DURATION = 180;
	private final static int MAX_FIREBALLS_TO_BE_FIRED = 15;

	public WildfireBarrageAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), BARRAGE_ATTACK_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WildfireEntity wildfire) {
		var attackTarget = wildfire.getBrain().getOptionalMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (
			attackTarget == null
			|| attackTarget.isAlive() == false
			|| wildfire.canTarget(attackTarget) == false
		) {
			return false;
		}

		this.attackTarget = attackTarget;

		return true;
	}

	@Override
	protected void run(ServerWorld world, WildfireEntity wildfire, long time) {
		wildfire.getNavigation().stop();
		this.fireballsFired = 0;
		this.attackTargetIsNotVisibleTicks = 0;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		if (
			attackTarget.isAlive() == false
			|| wildfire.canTarget(attackTarget) == false
		) {
			attackTarget = wildfire.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_ATTACKABLE).orElse(null);
		}

		if (
			attackTarget == null
			|| attackTarget.isAlive() == false
			|| wildfire.canTarget(attackTarget) == false
			|| fireballsFired > MAX_FIREBALLS_TO_BE_FIRED
		) {
			return false;
		}

		var nearestVisibleTargetablePlayer = wildfire.getBrain().getOptionalMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(null);

		if (
			nearestVisibleTargetablePlayer != null
			&& nearestVisibleTargetablePlayer.isAlive()
			&& wildfire.canTarget(nearestVisibleTargetablePlayer) == false
			&& wildfire.distanceTo(nearestVisibleTargetablePlayer) <= WildfireShockwaveAttackTask.SHOCKWAVE_ATTACK_RANGE
			&& wildfire.getBrain().isMemoryInState(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT)
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void keepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		boolean isAttackTargetVisible = wildfire.getVisibilityCache().canSee(this.attackTarget);

		if (isAttackTargetVisible) {
			this.attackTargetIsNotVisibleTicks = 0;
		} else {
			this.attackTargetIsNotVisibleTicks++;
		}

		double targetX = this.attackTarget.getX() - wildfire.getX();
		double targetY = this.attackTarget.getBodyY(0.5) - wildfire.getBodyY(0.5);
		double targetZ = this.attackTarget.getZ() - wildfire.getZ();

		if (this.fireballCooldown > 0) {
			this.fireballCooldown--;
			return;
		}

		if (this.attackTargetIsNotVisibleTicks > 5) {
			wildfire.getMoveControl().moveTo(
				attackTarget.getX(),
				attackTarget.getY(),
				attackTarget.getZ(),
				wildfire.getMovementSpeed()
			);
		}

		wildfire.getLookControl().lookAt(this.attackTarget);

		double distanceToAttackTarget = wildfire.squaredDistanceTo(this.attackTarget);

		double h = Math.sqrt(Math.sqrt(distanceToAttackTarget)) * 0.5;
		if (wildfire.isSilent() == false) {
			wildfire.playShootSound();
			wildfire.getWorld().syncWorldEvent(null, 1018, wildfire.getBlockPos(), 0);
		}

		Random random = wildfire.getRandom();

		for (int i = 0; i < 5; i++) {
			SmallFireballEntity smallFireballEntity = new SmallFireballEntity(
				world,
				wildfire,
				random.nextTriangular(targetX, 2.297 * h),
				targetY,
				random.nextTriangular(targetZ, 2.297 * h)
			);
			smallFireballEntity.setPosition(
				smallFireballEntity.getX(),
				wildfire.getBodyY(0.5) + 0.5,
				smallFireballEntity.getZ()
			);
			wildfire.world.spawnEntity(smallFireballEntity);
			this.fireballsFired++;
		}

		this.fireballCooldown = 10;
	}

	@Override
	protected void finishRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		this.fireballsFired = 0;
		this.attackTargetIsNotVisibleTicks = 0;
		WildfireBrain.setBarrageAttackCooldown(wildfire);
	}
}
