package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.util.animation.AnimationMath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3d;

@Environment(EnvType.CLIENT)
public final class ModelPartAnimationContext
{
	private final int initialTick;
	private final int totalTicks;
	private int currentTick;

	private final Vec3d targetVector;
	private Vec3d currentVector;

	private float progress;

	private ModelPartAnimationContext(
		int initialTick,
		int totalTicks,
		float progress,
		Vec3d targetVector,
		Vec3d currentVector
	) {
		this.initialTick = initialTick;
		this.totalTicks = totalTicks;
		this.currentTick = initialTick;
		this.progress = progress;
		this.targetVector = targetVector;
		this.currentVector = currentVector;
	}

	public static ModelPartAnimationContext createWithTicks(
		int initialTick,
		int totalTicks,
		Vec3d targetVector,
		Vec3d currentVector
	) {
		return new ModelPartAnimationContext(
			initialTick,
			totalTicks,
			0.0F,
			targetVector,
			currentVector
		);
	}

	public static ModelPartAnimationContext createWithProgress(
		float progress,
		Vec3d targetVector,
		Vec3d currentVector
	) {
		return new ModelPartAnimationContext(
			0,
			0,
			progress,
			targetVector,
			currentVector
		);
	}

	public void setCurrentTick(int currentTick) {
		this.currentTick = currentTick;
	}

	public void recalculateProgress() {
		float progress = (float) (this.currentTick - this.initialTick) / this.totalTicks;
		this.progress = Math.min(Math.max(-1.0f, progress), 1.0f);
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public Vec3d getCurrentVector() {
		return currentVector;
	}

	public Vec3d getTargetVector() {
		return targetVector;
	}

	public void recalculateCurrentVector() {
		this.currentVector = new Vec3d(
			recalculateCurrentX(),
			recalculateCurrentY(),
			recalculateCurrentZ()
		);
	}

	private float recalculateCurrentX() {
		return this.calculateNewValue(this.currentVector.getX(), this.targetVector.getX());
	}

	private float recalculateCurrentY() {
		return this.calculateNewValue(this.currentVector.getY(), this.targetVector.getY());
	}

	private float recalculateCurrentZ() {
		return this.calculateNewValue(this.currentVector.getZ(), this.targetVector.getZ());
	}

	private float calculateNewValue(
		double currentValue,
		double targetValue
	) {
		return AnimationMath.lerp(this.progress, currentValue, targetValue);
	}
}
