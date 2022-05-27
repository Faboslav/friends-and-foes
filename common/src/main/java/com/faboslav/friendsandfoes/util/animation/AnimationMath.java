package com.faboslav.friendsandfoes.util.animation;

import net.minecraft.util.math.MathHelper;

public final class AnimationMath
{
	public static float absSin(float progress, float range, float speed) {
		return MathHelper.abs(sin(progress, range, speed));
	}

	public static float absSin(float progress, float range) {
		return absSin(progress, range, 1.0F);
	}

	public static float absSin(float progress) {
		return absSin(progress, 1.0F);
	}

	public static float sin(float progress, float range, float speed) {
		return MathHelper.sin(progress * speed) * range;
	}

	public static float sin(float progress, float range) {
		return sin(progress, range, 1.0F);
	}

	public static float sin(float progress) {
		return sin(progress, 1.0F);
	}

	public static float absCos(float progress, float range, float speed) {
		return MathHelper.cos(cos(progress, range, speed));
	}

	public static float absCos(float progress, float range) {
		return absCos(progress, range, 1.0F);
	}

	public static float absCos(float progress) {
		return absCos(progress, 1.0F);
	}

	public static float cos(float progress, float range, float speed) {
		return MathHelper.cos(progress * speed) * range;
	}

	public static float cos(float progress, float range) {
		return cos(progress, range, 1.0F);
	}

	public static float cos(float progress) {
		return cos(progress, 1.0F);
	}

	public static float lerp(float progress, float start, float end) {
		return MathHelper.lerp(progress, start, end);
	}

	public static float lerp(float progress, double start, double end) {
		return lerp(progress, (float) start, (float) end);
	}

	public static float correctProgress(float value) {
		return Math.min(Math.max(-1.0f, value), 1.0f);
	}

	public static float toRadians(double angleDegree) {
		return (float) Math.toRadians(angleDegree);
	}

	private AnimationMath() {
	}
}
