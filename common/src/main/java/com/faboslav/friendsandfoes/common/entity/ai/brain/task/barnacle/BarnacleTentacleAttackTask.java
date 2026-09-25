package com.faboslav.friendsandfoes.common.entity.ai.brain.task.barnacle;

import com.faboslav.friendsandfoes.common.entity.BarnacleEntity;
import com.faboslav.friendsandfoes.common.entity.BoatEntityAccess;
import com.faboslav.friendsandfoes.common.entity.ai.brain.BarnacleBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.phys.Vec3;
import java.util.Map;

public final class BarnacleTentacleAttackTask extends Behavior<BarnacleEntity>
{
	private static final int APPROACH_TIMEOUT = 200;
	private static final int MAX_DRAG_DURATION = 800;
	private static final int MAX_DURATION = APPROACH_TIMEOUT + BarnacleEntity.TENTACLE_GRAB_DURATION + MAX_DRAG_DURATION;

	private State state = State.DONE;
	private int stateTicks;
	private int outOfWaterTicks;
	private boolean hasGrabbed;
	private LivingEntity target;
	private Entity grabbedEntity;

	public BarnacleTentacleAttackTask() {
		super(Map.of(
			MemoryModuleType.ATTACK_TARGET, MemoryStatus.VALUE_PRESENT,
			MemoryModuleType.WALK_TARGET, MemoryStatus.REGISTERED,
			MemoryModuleType.LOOK_TARGET, MemoryStatus.REGISTERED,
			MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE, MemoryStatus.REGISTERED,
			MemoryModuleType.ATTACK_COOLING_DOWN, MemoryStatus.REGISTERED,
			FriendsAndFoesMemoryModuleTypes.BARNACLE_TENTACLE_ATTACK_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT
		), MAX_DURATION);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, BarnacleEntity barnacle) {
		LivingEntity attackTarget = barnacle.getBrain().getMemory(MemoryModuleType.ATTACK_TARGET).orElse(null);

		if (
			!barnacle.isInWater()
			|| !barnacle.isValidTentacleTarget(attackTarget)
			|| !barnacle.hasLineOfSight(attackTarget)
			|| isGrabbedByAnotherBarnacle(world, barnacle, attackTarget)
		) {
			return false;
		}

		this.target = attackTarget;

		return true;
	}

	@Override
	protected void start(ServerLevel world, BarnacleEntity barnacle, long time) {
		this.state = State.APPROACH;
		this.stateTicks = 0;
		this.outOfWaterTicks = 0;
		this.hasGrabbed = false;
		this.grabbedEntity = null;

		barnacle.getBrain().setMemoryWithExpiry(MemoryModuleType.ATTACK_COOLING_DOWN, true, MAX_DURATION);
		BehaviorUtils.lookAtEntity(barnacle, this.target);
		barnacle.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		this.swimTowardsTarget(barnacle);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, BarnacleEntity barnacle, long time) {
		return this.state != State.DONE
			   && this.target.isAlive()
			   && barnacle.getBrain().hasMemoryValue(MemoryModuleType.ATTACK_TARGET)
			   && !barnacle.getBrain().hasMemoryValue(FriendsAndFoesMemoryModuleTypes.BARNACLE_TENTACLE_ATTACK_COOLDOWN.get());
	}

	@Override
	protected void tick(ServerLevel world, BarnacleEntity barnacle, long time) {
		barnacle.getLookControl().setLookAt(this.target);
		this.stateTicks++;

		switch (this.state) {
			case APPROACH -> this.tickApproach(barnacle);
			case GRAB -> {
				barnacle.getNavigation().stop();
				this.tickGrab(world, barnacle);
			}
			case DRAG -> {
				barnacle.getNavigation().stop();
				this.tickDrag(world, barnacle);
			}
			case DONE -> {
			}
		}
	}

	@Override
	protected void stop(ServerLevel world, BarnacleEntity barnacle, long time) {
		if (this.grabbedEntity instanceof BoatEntityAccess boat) {
			boat.friendsandfoes$setTentaclePull(null);
		}

		barnacle.eraseTentacleTarget();
		barnacle.stopTentacleAttackAnimation();
		barnacle.getNavigation().stop();
		barnacle.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		barnacle.getBrain().eraseMemory(MemoryModuleType.ATTACK_COOLING_DOWN);

		if (!barnacle.getBrain().hasMemoryValue(FriendsAndFoesMemoryModuleTypes.BARNACLE_TENTACLE_ATTACK_COOLDOWN.get())) {
			if (this.hasGrabbed) {
				BarnacleBrain.setTentacleAttackCooldown(barnacle);
			} else {
				BarnacleBrain.setTentacleAttackRetryCooldown(barnacle);
			}
		}

		this.state = State.DONE;
		this.grabbedEntity = null;
	}

	private void tickApproach(BarnacleEntity barnacle) {
		if (!barnacle.isValidTentacleTarget(this.target)) {
			this.state = State.DONE;
			return;
		}

		Entity grabTarget = resolveGrabbedEntity(this.target);

		if (this.getDistanceFromMouth(barnacle, grabTarget) <= BarnacleEntity.TENTACLE_ATTACK_RANGE && barnacle.hasLineOfSight(this.target)) {
			this.startGrab(barnacle, grabTarget);
			return;
		}

		if (this.stateTicks >= APPROACH_TIMEOUT) {
			this.state = State.DONE;
			return;
		}

		if (this.stateTicks % 4 == 0) {
			this.swimTowardsTarget(barnacle);
		}
	}

	private void startGrab(BarnacleEntity barnacle, Entity grabbed) {
		this.state = State.GRAB;
		this.stateTicks = 0;
		this.hasGrabbed = true;
		this.grabbedEntity = grabbed;

		barnacle.getBrain().eraseMemory(MemoryModuleType.WALK_TARGET);
		barnacle.getBrain().eraseMemory(MemoryModuleType.CANT_REACH_WALK_TARGET_SINCE);
		barnacle.getNavigation().stop();
		barnacle.setTentacleTarget(this.grabbedEntity);
		barnacle.startTentacleAttackAnimation();
	}

	private void tickGrab(ServerLevel world, BarnacleEntity barnacle) {
		if (this.stateTicks < BarnacleEntity.TENTACLE_GRAB_DURATION) {
			return;
		}

		this.state = State.DRAG;
		this.stateTicks = 0;
		barnacle.doHurtTarget(/*? if >=1.21.3 {*/world, /*?}*/this.target);
	}

	private void tickDrag(ServerLevel world, BarnacleEntity barnacle) {
		Entity grabbed = this.grabbedEntity;

		if (grabbed != this.target && (!grabbed.isAlive() || !grabbed.hasPassenger(this.target))) {
			grabbed = this.target;
			this.grabbedEntity = grabbed;
			barnacle.setTentacleTarget(grabbed);
		}

		if (
			this.target.isDeadOrDying()
			|| !barnacle.isInWater()
			|| this.stateTicks > MAX_DRAG_DURATION
			|| this.getDistanceFromMouth(barnacle, grabbed) > BarnacleEntity.MAX_TENTACLE_LENGTH
		) {
			this.state = State.DONE;
			return;
		}

		if (grabbed == this.target && !BarnacleEntity.isTouchingWater(grabbed)) {
			this.outOfWaterTicks++;
		} else {
			this.outOfWaterTicks = 0;
		}

		if (this.outOfWaterTicks > 20) {
			this.state = State.DONE;
			return;
		}

		this.pull(barnacle, grabbed);
		this.dive(world, barnacle);

		if (grabbed != this.target && this.stateTicks % 10 == 0) {
			VersionedEntity.hurt(grabbed, barnacle.damageSources().mobAttack(barnacle), 1.2F);
		}

		if (grabbed == this.target && this.stateTicks % 20 == 0) {
			VersionedEntity.hurt(this.target, barnacle.damageSources().mobAttack(barnacle), 2.0F);
		}
	}

	private void pull(BarnacleEntity barnacle, Entity grabbed) {
		Vec3 mouthPosition = barnacle.getMouthPosition();
		Vec3 grabbedPosition = BarnacleEntity.getTentacleHoldPosition(grabbed);
		Vec3 fromMouth = grabbedPosition.subtract(mouthPosition);
		Vec3 anchorPosition = mouthPosition.add(fromMouth.normalize().scale(BarnacleEntity.TENTACLE_HOLD_DISTANCE));
		Vec3 pull = anchorPosition.subtract(grabbedPosition).scale(0.3D);

		if (pull.length() > 0.5D) {
			pull = pull.normalize().scale(0.5D);
		}

		Vec3 pullMovement = pull.add(barnacle.getDeltaMovement());

		if (grabbed instanceof BoatEntityAccess boat) {
			boat.friendsandfoes$setTentaclePull(pullMovement);
			return;
		}

		grabbed.setDeltaMovement(pullMovement);
		grabbed.syncVelocity = true;
	}

	private void dive(ServerLevel world, BarnacleEntity barnacle) {
		BlockPos belowPos = barnacle.blockPosition().below();

		if (!world.getFluidState(belowPos).is(FluidTags.WATER)) {
			return;
		}

		barnacle.setDeltaMovement(barnacle.getDeltaMovement().add(0.0D, -0.02D, 0.0D));
	}

	private void swimTowardsTarget(BarnacleEntity barnacle) {
		barnacle.getNavigation().moveTo(this.target, 0.8F);
	}

	private double getDistanceFromMouth(BarnacleEntity barnacle, Entity entity) {
		return barnacle.getMouthPosition().distanceTo(BarnacleEntity.getTentacleHoldPosition(entity));
	}

	private static Entity resolveGrabbedEntity(LivingEntity target) {
		Entity vehicle = target.getVehicle();

		if (BarnacleEntity.isBoat(vehicle) && vehicle.isInWater()) {
			return vehicle;
		}

		return target;
	}

	private static boolean isGrabbedByAnotherBarnacle(ServerLevel world, BarnacleEntity barnacle, LivingEntity target) {
		return !world.getEntitiesOfClass(
			BarnacleEntity.class,
			barnacle.getBoundingBox().inflate(16.0D),
			otherBarnacle -> otherBarnacle != barnacle && otherBarnacle.getTentacleTarget() == target
		).isEmpty();
	}

	private enum State
	{
		APPROACH,
		GRAB,
		DRAG,
		DONE
	}
}
