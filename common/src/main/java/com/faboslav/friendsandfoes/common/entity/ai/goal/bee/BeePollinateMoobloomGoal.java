package com.faboslav.friendsandfoes.common.entity.ai.goal.bee;

import com.faboslav.friendsandfoes.common.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.common.mixin.BeeEntityAccessor;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;
import java.util.List;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.phys.Vec3;

public final class BeePollinateMoobloomGoal extends Goal
{
	private final TargetingConditions VALID_MOOBLOOM_PREDICATE = TargetingConditions.forNonCombat().ignoreInvisibilityTesting();
	private final Bee beeEntity;
	private final BeeEntityAccessor beeEntityAccessor;
	private MoobloomEntity moobloom;

	private boolean running;
	private int ticks = 0;
	private int pollinationTicks = 0;
	private int lastPollinationTick = 0;

	public BeePollinateMoobloomGoal(
		Bee beeEntity,
		BeeEntityAccessor beeEntityAccessor
	) {
		this.beeEntity = beeEntity;
		this.beeEntityAccessor = beeEntityAccessor;

		this.setFlags(EnumSet.of(Flag.MOVE));
	}

	public boolean canUse() {
		if (this.beeEntityAccessor.getTicksUntilCanPollinate() > 0) {
			return false;
		} else if (this.beeEntity.isAngry()) {
			return false;
		} else if (this.beeEntity.hasNectar()) {
			return false;
		} else if (this.beeEntity.level().isRaining()) {
			return false;
		} else if (this.beeEntity.getRandom().nextFloat() < 0.5F) {
			return false;
		} else if (this.beeEntity.beePollinateGoal.isPollinating()) {
			return false;
		}

		MoobloomEntity moobloom = this.findMoobloom();

		if (moobloom == null) {
			return false;
		}

		this.moobloom = moobloom;
		Vec3 moobloomPollinationPos = this.getMoobloomPollinationPos();
		this.beeEntity.getNavigation().moveTo(
			moobloomPollinationPos.x(),
			moobloomPollinationPos.y(),
			moobloomPollinationPos.z(),
			1.2000000476837158D
		);

		return true;
	}

	public boolean canContinueToUse() {
		if (!this.isRunning()) {
			return false;
		} else if (this.beeEntity.isAngry()) {
			return false;
		} else if (this.beeEntity.getCommandSenderWorld().isRaining()) {
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
		this.beeEntity.resetTicksWithoutNectarSinceExitingHive();
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

		Vec3 moobloomPollinationPos = this.getMoobloomPollinationPos();
		double dinstanceToMoobloom = this.beeEntity.position().distanceTo(moobloomPollinationPos);

		if (dinstanceToMoobloom >= 0.5) {
			this.beeEntity.getMoveControl().setWantedPosition(
				moobloomPollinationPos.x(),
				moobloomPollinationPos.y(),
				moobloomPollinationPos.z(),
				0.9D
			);
			this.beeEntity.getLookControl().setLookAt(
				moobloomPollinationPos.x(),
				this.getMoobloom().getY(),
				moobloomPollinationPos.z()
			);
		}

		if (dinstanceToMoobloom <= 1.5D) {
			++this.pollinationTicks;
			if (
				this.beeEntity.getRandom().nextFloat() < 0.05F &&
				this.pollinationTicks > this.lastPollinationTick + 60
			) {
				this.lastPollinationTick = this.pollinationTicks;
				this.beeEntity.playSound(SoundEvents.BEE_POLLINATE, 1.0F, 1.0F);
			}
		}
	}

	private void pollinate() {
		for (int i = 0; i < 7; ++i) {
			double d = this.beeEntity.getRandom().nextGaussian() * 0.02D;
			double e = this.beeEntity.getRandom().nextGaussian() * 0.02D;
			double f = this.beeEntity.getRandom().nextGaussian() * 0.02D;
			((ServerLevel) this.beeEntity.level()).sendParticles(
				ParticleTypes.HEART,
				this.beeEntity.getRandomX(1.0D),
				this.beeEntity.getRandomY() + 0.5D,
				this.beeEntity.getRandomZ(1.0D),
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
		List<MoobloomEntity> moobloomEntities = this.beeEntity.level().getNearbyEntities(
			MoobloomEntity.class,
			VALID_MOOBLOOM_PREDICATE,
			this.beeEntity,
			this.beeEntity.getBoundingBox().inflate(32.0D)
		);
		double d = 1.7976931348623157E308D;
		MoobloomEntity closestMoobloomEntity = null;

		for (MoobloomEntity moobloomEntity : moobloomEntities) {
			if (!moobloomEntity.isBaby() && this.beeEntity.distanceToSqr(moobloomEntity) < d) {
				closestMoobloomEntity = moobloomEntity;
				d = this.beeEntity.distanceToSqr(moobloomEntity);
			}
		}

		return closestMoobloomEntity;
	}

	private Vec3 getMoobloomPollinationPos() {
		double moobloomPollinationYPos = this.getMoobloom().getY() + this.getMoobloom().getBbHeight() * 1.5;
		return new Vec3(
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