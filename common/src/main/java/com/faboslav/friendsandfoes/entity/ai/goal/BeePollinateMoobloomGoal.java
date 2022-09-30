package com.faboslav.friendsandfoes.entity.ai.goal;

import com.faboslav.friendsandfoes.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.mixin.BeeEntityAccessor;
import com.faboslav.friendsandfoes.util.RandomGenerator;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;

public final class BeePollinateMoobloomGoal extends Goal
{
	private final TargetPredicate VALID_MOOBLOOM_PREDICATE = TargetPredicate.createNonAttackable().ignoreDistanceScalingFactor();
	private final BeeEntity beeEntity;
	private final BeeEntityAccessor beeEntityAccessor;
	private MoobloomEntity moobloom;

	private boolean running;
	private int ticks = 0;
	private int pollinationTicks = 0;
	private int lastPollinationTick = 0;

	public BeePollinateMoobloomGoal(
		BeeEntity beeEntity,
		BeeEntityAccessor beeEntityAccessor
	) {
		this.beeEntity = beeEntity;
		this.beeEntityAccessor = beeEntityAccessor;

		this.setControls(EnumSet.of(Control.MOVE));
	}

	public boolean canStart() {
		if (this.beeEntityAccessor.getTicksUntilCanPollinate() > 0) {
			return false;
		} else if (this.beeEntity.hasAngerTime()) {
			return false;
		} else if (this.beeEntity.hasNectar()) {
			return false;
		} else if (this.beeEntity.world.isRaining()) {
			return false;
		} else if (RandomGenerator.generateRandomFloat() < 0.5F) {
			return false;
		} else if (this.beeEntity.pollinateGoal.isRunning()) {
			return false;
		}

		MoobloomEntity moobloom = this.findMoobloom();

		if (moobloom == null) {
			return false;
		}

		this.moobloom = moobloom;
		Vec3d moobloomPollinationPos = this.getMoobloomPollinationPos();
		this.beeEntity.getNavigation().startMovingTo(
			moobloomPollinationPos.getX(),
			moobloomPollinationPos.getY(),
			moobloomPollinationPos.getZ(),
			1.2000000476837158D
		);

		return true;
	}

	public boolean shouldContinue() {
		if (!this.isRunning()) {
			return false;
		} else if (this.beeEntity.hasAngerTime()) {
			return false;
		} else if (this.beeEntity.getEntityWorld().isRaining()) {
			return false;
		} else if (this.completedPollination()) {
			return this.beeEntity.getRandom().nextFloat() < 0.2F;
		} else if (this.getMoobloom() == null) {
			return false;
		} else {
			return this.moobloom.isAlive();
		}
	}

	public void start() {
		this.pollinationTicks = 0;
		this.ticks = 0;
		this.lastPollinationTick = 0;
		this.setIsRunning(true);
		this.beeEntity.resetPollinationTicks();
	}

	public void cancel() {
		this.setIsRunning(false);
	}

	public void stop() {
		if (this.completedPollination()) {
			this.pollinate();
			this.beeEntityAccessor.invokeSetHasNectar(true);
		}

		this.setIsRunning(false);
		this.beeEntity.getNavigation().stop();
		this.beeEntityAccessor.setTicksUntilCanPollinate(200);
	}

	public void tick() {
		++this.ticks;

		if (this.ticks > 600) {
			this.setMoobloom(null);
			return;
		}

		Vec3d moobloomPollinationPos = this.getMoobloomPollinationPos();
		double dinstanceToMoobloom = this.beeEntity.getPos().distanceTo(moobloomPollinationPos);

		if (dinstanceToMoobloom >= 0.5) {
			this.beeEntity.getMoveControl().moveTo(
				moobloomPollinationPos.getX(),
				moobloomPollinationPos.getY(),
				moobloomPollinationPos.getZ(),
				0.9D
			);
			this.beeEntity.getLookControl().lookAt(
				moobloomPollinationPos.getX(),
				this.getMoobloom().getY(),
				moobloomPollinationPos.getZ()
			);
		}

		if (dinstanceToMoobloom <= 1.5D) {
			++this.pollinationTicks;
			if (
				this.beeEntity.getRandom().nextFloat() < 0.05F &&
				this.pollinationTicks > this.lastPollinationTick + 60
			) {
				this.lastPollinationTick = this.pollinationTicks;
				this.beeEntity.playSound(SoundEvents.ENTITY_BEE_POLLINATE, 1.0F, 1.0F);
			}
		}
	}

	private void pollinate() {
		for (int i = 0; i < 7; ++i) {
			double d = this.beeEntity.getRandom().nextGaussian() * 0.02D;
			double e = this.beeEntity.getRandom().nextGaussian() * 0.02D;
			double f = this.beeEntity.getRandom().nextGaussian() * 0.02D;
			((ServerWorld) this.beeEntity.world).spawnParticles(
				ParticleTypes.HEART,
				this.beeEntity.getParticleX(1.0D),
				this.beeEntity.getRandomBodyY() + 0.5D,
				this.beeEntity.getParticleZ(1.0D),
				1,
				d,
				e,
				f,
				1
			);
		}
	}

	@Nullable
	private MoobloomEntity findMoobloom() {
		List<MoobloomEntity> moobloomEntities = this.beeEntity.world.getTargets(
			MoobloomEntity.class,
			VALID_MOOBLOOM_PREDICATE,
			this.beeEntity,
			this.beeEntity.getBoundingBox().expand(32.0D)
		);
		double d = 1.7976931348623157E308D;
		MoobloomEntity closestMoobloomEntity = null;

		for (MoobloomEntity moobloomEntity : moobloomEntities) {
			if (!moobloomEntity.isBaby() && this.beeEntity.squaredDistanceTo(moobloomEntity) < d) {
				closestMoobloomEntity = moobloomEntity;
				d = this.beeEntity.squaredDistanceTo(moobloomEntity);
			}
		}

		return closestMoobloomEntity;
	}

	private Vec3d getMoobloomPollinationPos() {
		double moobloomPollinationYPos = this.getMoobloom().getY() + this.getMoobloom().getHeight() * 1.5;
		return new Vec3d(
			this.getMoobloom().getX(),
			moobloomPollinationYPos,
			this.getMoobloom().getZ()
		);
	}

	private boolean completedPollination() {
		return this.pollinationTicks > 200;
	}

	private boolean isRunning() {
		return this.running;
	}

	private void setIsRunning(boolean isRunning) {
		this.running = isRunning;
	}

	public MoobloomEntity getMoobloom() {
		return this.moobloom;
	}

	public void setMoobloom(MoobloomEntity moobloomEntity) {
		this.moobloom = moobloomEntity;
	}
}