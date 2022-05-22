package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.util.animation.AnimationMath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.math.Vec3f;

@Environment(EnvType.CLIENT)
public final class ModelPartAnimationContext
{
	private final int initialTick;
	private final int totalTicks;
	private int currentTick;
	private float delta;
	private float progress;

	private final Vec3f targetVector;
	private final Vec3f currentVector;

	private ModelPartAnimationContext(
		int initialTick,
		int totalTicks,
		float progress,
		float delta,
		Vec3f targetVector,
		Vec3f currentVector
	) {
		this.initialTick = initialTick;
		this.totalTicks = totalTicks;
		this.currentTick = initialTick;
		this.progress = progress;
		this.delta = delta;
		this.targetVector = targetVector;
		this.currentVector = currentVector;
	}

	public static ModelPartAnimationContext createWithTicks(
		int initialTick,
		int totalTicks,
		Vec3f targetVector,
		Vec3f currentVector
	) {
		return new ModelPartAnimationContext(
			initialTick,
			totalTicks,
			0.0F,
			0.0F,
			targetVector,
			currentVector
		);
	}

	public static ModelPartAnimationContext createWithDelta(
		float delta,
		Vec3f targetVector,
		Vec3f currentVector
	) {
		return new ModelPartAnimationContext(
			0,
			0,
			0.0F,
			delta,
			targetVector,
			currentVector
		);
	}

	public static ModelPartAnimationContext createWithProgress(
		float progress,
		Vec3f targetVector,
		Vec3f currentVector
	) {
		return new ModelPartAnimationContext(
			0,
			0,
			progress,
			0.0F,
			targetVector,
			currentVector
		);
	}

	public void setCurrentTick(int currentTick) {
		this.currentTick = currentTick;
	}

	public void setDelta(float delta) {
		this.delta = delta;
	}

	public void setProgress(float progress) {
		this.progress = progress;
	}

	public Vec3f getCurrentVector() {
		return currentVector;
	}

	public Vec3f getTargetVector() {
		return targetVector;
	}

	public void calculateAndSetProgressFromCurrentTick() {
		float progress = (float) (this.currentTick - this.initialTick) / this.totalTicks;
		this.progress = Math.min(Math.max(-1.0f, progress), 1.0f);
	}

	public void recalculateCurrentVectorWithProgress() {

	}

	public void ForDelta(float delta) {
		this.delta = delta;

		this.currentVector.set(
			calculateNewValueForProgress(this.currentVector.getX(), this.targetVector.getX()),
			calculateNewValueForProgress(this.currentVector.getY(), this.targetVector.getY()),
			calculateNewValueForProgress(this.currentVector.getZ(), this.targetVector.getZ())
		);
	}

	public void updateForProgress(float progress) {
		this.progress = progress;

		this.currentVector.set(
			calculateNewValueForProgress(this.currentVector.getX(), this.targetVector.getX()),
			calculateNewValueForProgress(this.currentVector.getY(), this.targetVector.getY()),
			calculateNewValueForProgress(this.currentVector.getZ(), this.targetVector.getZ())
		);
	}

	private float calculateNewValueForProgress(
		float currentValue,
		float targetValue
	) {
		return AnimationMath.lerp(this.progress, currentValue, targetValue);
	}

	private float calculateNewValueForDelta(
		float currentValue,
		float targetValue
	) {
		return AnimationMath.lerp(this.delta, currentValue, targetValue);
	}
}
