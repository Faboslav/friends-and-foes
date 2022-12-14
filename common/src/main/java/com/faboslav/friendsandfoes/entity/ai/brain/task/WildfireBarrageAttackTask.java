package com.faboslav.friendsandfoes.entity.ai.brain.task;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import com.faboslav.friendsandfoes.entity.WildfireShieldDebrisEntity;
import com.faboslav.friendsandfoes.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.MemoryModuleState;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.LookTargetUtil;
import net.minecraft.entity.ai.brain.task.MultiTickTask;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;

public class WildfireBarrageAttackTask extends MultiTickTask<WildfireEntity>
{
	private int shieldDebrisFired;
	private int shieldDebrisCooldown;
	private boolean canDoMeeleAttack;
	private LivingEntity attackTarget;
	private int attackTargetIsNotVisibleTicks;

	private final static int BARRAGE_ATTACK_DURATION = 180;
	private final static int MAX_FIREBALLS_TO_BE_FIRED = 30;

	public WildfireBarrageAttackTask() {
		super(ImmutableMap.of(
			MemoryModuleType.ATTACK_TARGET, MemoryModuleState.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT
		), BARRAGE_ATTACK_DURATION);
	}

	@Override
	protected boolean shouldRun(ServerWorld world, WildfireEntity wildfire) {
		var attackTarget = wildfire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (
			attackTarget == null
			|| attackTarget.isAlive() == false
			|| (
				attackTarget instanceof PlayerEntity
				&& (
					attackTarget.isSpectator()
					|| ((PlayerEntity) attackTarget).isCreative()
				)
			)
		) {
			return false;
		}

		this.attackTarget = attackTarget;

		return true;
	}

	@Override
	protected void run(ServerWorld world, WildfireEntity wildfire, long time) {
		wildfire.getBrain().forget(MemoryModuleType.WALK_TARGET);
		wildfire.getNavigation().stop();

		LookTargetUtil.lookAt(wildfire, this.attackTarget);
		wildfire.getLookControl().lookAt(this.attackTarget);

		WildfireBrain.setAttackTarget(wildfire, this.attackTarget);

		this.shieldDebrisFired = 0;
		this.attackTargetIsNotVisibleTicks = 0;
		this.canDoMeeleAttack = true;
	}

	@Override
	protected boolean shouldKeepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		if (attackTarget.isAlive() == false) {
			attackTarget = wildfire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(null);
		}

		if (
			attackTarget == null
			|| attackTarget.isAlive() == false
			|| attackTarget.isAlive() == false
			|| (
				attackTarget instanceof PlayerEntity
				&& (
					attackTarget.isSpectator()
					|| ((PlayerEntity) attackTarget).isCreative()
				)
			)
			|| shieldDebrisFired > MAX_FIREBALLS_TO_BE_FIRED
		) {
			return false;
		}

		var nearestVisibleTargetablePlayer = wildfire.getBrain().getOptionalRegisteredMemory(MemoryModuleType.NEAREST_VISIBLE_TARGETABLE_PLAYER).orElse(null);

		return nearestVisibleTargetablePlayer == null
			   || !nearestVisibleTargetablePlayer.isAlive()
			   || !(wildfire.distanceTo(nearestVisibleTargetablePlayer) <= WildfireShockwaveAttackTask.SHOCKWAVE_ATTACK_RANGE)
			   || !wildfire.getBrain().isMemoryInState(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryModuleState.VALUE_ABSENT);
	}

	@Override
	protected void keepRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		wildfire.getLookControl().lookAt(this.attackTarget);

		boolean isAttackTargetVisible = wildfire.getVisibilityCache().canSee(this.attackTarget);

		if (isAttackTargetVisible) {
			this.attackTargetIsNotVisibleTicks = 0;
		} else {
			this.attackTargetIsNotVisibleTicks++;
		}

		double targetX = this.attackTarget.getX() - wildfire.getX();
		double targetY = this.attackTarget.getBodyY(0.5) - wildfire.getBodyY(0.5);
		double targetZ = this.attackTarget.getZ() - wildfire.getZ();

		if (this.shieldDebrisCooldown > 0) {
			this.shieldDebrisCooldown--;
			return;
		}

		if (this.canDoMeeleAttack && wildfire.distanceTo(attackTarget) < 3.0F) {
			wildfire.tryAttack(attackTarget);
			this.canDoMeeleAttack = false;
		} else {
			this.canDoMeeleAttack = true;
		}

		if (this.attackTargetIsNotVisibleTicks > 5) {
			wildfire.getMoveControl().moveTo(
				attackTarget.getX(),
				attackTarget.getY(),
				attackTarget.getZ(),
				wildfire.getMovementSpeed()
			);
		}

		double distanceToAttackTarget = wildfire.squaredDistanceTo(this.attackTarget);

		double h = Math.sqrt(Math.sqrt(distanceToAttackTarget)) * 0.5;
		if (wildfire.isSilent() == false) {
			wildfire.playShootSound();
			wildfire.getWorld().syncWorldEvent(null, 1018, wildfire.getBlockPos(), 0);
		}

		Random random = wildfire.getRandom();

		for (int i = 0; i < 8; i++) {
			WildfireShieldDebrisEntity shieldDebris = new WildfireShieldDebrisEntity(
				world,
				wildfire,
				random.nextTriangular(targetX, 2.297 * h),
				targetY,
				random.nextTriangular(targetZ, 2.297 * h)
			);
			shieldDebris.setPosition(
				shieldDebris.getX(),
				wildfire.getBodyY(0.5) + 0.5,
				shieldDebris.getZ()
			);
			wildfire.world.spawnEntity(shieldDebris);
			this.shieldDebrisFired++;
		}

		this.shieldDebrisCooldown = 10;
	}

	@Override
	protected void finishRunning(ServerWorld world, WildfireEntity wildfire, long time) {
		WildfireBrain.setBarrageAttackCooldown(wildfire);
	}
}
