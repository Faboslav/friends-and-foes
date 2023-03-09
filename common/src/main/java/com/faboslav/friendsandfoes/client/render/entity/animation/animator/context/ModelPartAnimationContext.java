package com.faboslav.friendsandfoes.client.render.entity.animation.animator.context;

import com.faboslav.friendsandfoes.util.animation.AnimationMath;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Vector3f;

@Environment(EnvType.CLIENT)
public final class ModelPartAnimationContext
{
	private final int initialTick;
	private final int totalTicks;
	private int currentTick;

	private final Vector3f targetVector;
	private Vector3f currentVector;

	private float progress;

	private ModelPartAnimationContext(
		int initialTick,
		int totalTicks,
		float progress,
		Vector3f targetVector,
		Vector3f currentVector
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
		Vector3f targetVector,
		Vector3f currentVector
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
		Vector3f targetVector,
		Vector3f currentVector
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

	public Vector3f getCurrentVector() {
		return currentVector;
	}

	public Vector3f getTargetVector() {
		return targetVector;
	}

	public void recalculateCurrentVector() {
		this.currentVector = new Vector3f(
			recalculateCurrentX(),
			recalculateCurrentY(),
			recalculateCurrentZ()
		);
	}

	private float recalculateCurrentX() {
		return this.calculateNewValue(this.currentVector.x(), this.targetVector.x());
	}

	private float recalculateCurrentY() {
		return this.calculateNewValue(this.currentVector.y(), this.targetVector.y());
	}

	private float recalculateCurrentZ() {
		return this.calculateNewValue(this.currentVector.z(), this.targetVector.z());
	}

	private float calculateNewValue(
		double currentValue,
		double targetValue
	) {
		return AnimationMath.lerp(this.progress, currentValue, targetValue);
	}
}
