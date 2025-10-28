package com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire;

import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.entity.WildfireShieldDebrisEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.google.common.collect.ImmutableMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;

public final class WildfireBarrageAttackTask extends Behavior<WildfireEntity>
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
			MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
			FriendsAndFoesMemoryModuleTypes.WILDFIRE_BARRAGE_ATTACK_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
		), BARRAGE_ATTACK_DURATION);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, WildfireEntity wildfire) {
		var attackTarget = wildfire.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (
			attackTarget == null
			|| !attackTarget.isAlive()
			|| (
				attackTarget instanceof Player
				&& (
					attackTarget.isSpectator()
					|| ((Player) attackTarget).isCreative()
				)
			)
		) {
			return false;
		}

		this.attackTarget = attackTarget;

		return true;
	}

	@Override
	protected void start(ServerLevel world, WildfireEntity wildfire, long time) {
		wildfire.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		wildfire.getNavigation().stop();

		BehaviorUtils.lookAtEntity(wildfire, this.attackTarget);
		wildfire.getLookControl().setLookAt(this.attackTarget);

		WildfireBrain.setAttackTarget(wildfire, this.attackTarget);

		this.shieldDebrisFired = 0;
		this.attackTargetIsNotVisibleTicks = 0;
		this.canDoMeeleAttack = true;
	}

	@Override
	protected boolean canStillUse(ServerLevel world, WildfireEntity wildfire, long time) {
		if (!attackTarget.isAlive()) {
			attackTarget = wildfire.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER).orElse(null);
		}

		if (
			attackTarget == null
			|| !attackTarget.isAlive()
			|| !attackTarget.isAlive()
			|| (
				attackTarget instanceof Player
				&& (
					attackTarget.isSpectator()
					|| ((Player) attackTarget).isCreative()
				)
			)
			|| shieldDebrisFired > MAX_FIREBALLS_TO_BE_FIRED
		) {
			return false;
		}

		var nearestVisibleTargetablePlayer = wildfire.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_ATTACKABLE_PLAYER).orElse(null);

		return nearestVisibleTargetablePlayer == null
			   || !nearestVisibleTargetablePlayer.isAlive()
			   || !(wildfire.distanceTo(nearestVisibleTargetablePlayer) <= WildfireShockwaveAttackTask.SHOCKWAVE_ATTACK_RANGE)
			   || !wildfire.getBrain().checkMemory(FriendsAndFoesMemoryModuleTypes.WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT);
	}

	@Override
	protected void tick(ServerLevel serverLevel, WildfireEntity wildfire, long time) {
		wildfire.getLookControl().setLookAt(this.attackTarget);

		boolean isAttackTargetVisible = wildfire.getSensing().hasLineOfSight(this.attackTarget);

		if (isAttackTargetVisible) {
			this.attackTargetIsNotVisibleTicks = 0;
		} else {
			this.attackTargetIsNotVisibleTicks++;
		}

		double targetX = this.attackTarget.getX() - wildfire.getX();
		double targetY = this.attackTarget.getY(0.5) - wildfire.getY(0.5);
		double targetZ = this.attackTarget.getZ() - wildfire.getZ();

		if (this.shieldDebrisCooldown > 0) {
			this.shieldDebrisCooldown--;
			return;
		}

		if (this.canDoMeeleAttack && wildfire.distanceTo(attackTarget) < 3.0F) {
			wildfire.doHurtTarget(/*? if >=1.21.3 {*/serverLevel, /*?}*/attackTarget);
			this.canDoMeeleAttack = false;
		} else {
			this.canDoMeeleAttack = true;
		}

		if (this.attackTargetIsNotVisibleTicks > 5) {
			wildfire.getMoveControl().setWantedPosition(
				attackTarget.getX(),
				attackTarget.getY(),
				attackTarget.getZ(),
				wildfire.getSpeed()
			);
		}

		double distanceToAttackTarget = wildfire.distanceToSqr(this.attackTarget);

		double h = Math.sqrt(Math.sqrt(distanceToAttackTarget)) * 0.5;
		if (!wildfire.isSilent()) {
			wildfire.playShootSound();
			wildfire.level().levelEvent(null, 1018, wildfire.blockPosition(), 0);
		}

		RandomSource random = wildfire.getRandom();

		for (int i = 0; i < 8; i++) {
			WildfireShieldDebrisEntity shieldDebris = new WildfireShieldDebrisEntity(
				serverLevel,
				wildfire,
				new Vec3(
					random.triangle(targetX, 2.297 * h),
					targetY,
					random.triangle(targetZ, 2.297 * h)
				)
			);
			shieldDebris.setPos(
				shieldDebris.getX(),
				wildfire.getY(0.5) + 0.5,
				shieldDebris.getZ()
			);
			wildfire.level().addFreshEntity(shieldDebris);
			this.shieldDebrisFired++;
		}

		this.shieldDebrisCooldown = 10;
	}

	@Override
	protected void stop(ServerLevel world, WildfireEntity wildfire, long time) {
		WildfireBrain.setBarrageAttackCooldown(wildfire);
	}
}
