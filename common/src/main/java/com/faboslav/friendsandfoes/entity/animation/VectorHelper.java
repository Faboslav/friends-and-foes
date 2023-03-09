package com.faboslav.friendsandfoes.entity.animation;

import net.minecraft.util.math.Vec3f;

public final class VectorHelper
{
	public static Vec3f createTranslationalVector(float f, float g, float h) {
		return new Vec3f(f, -g, h);
	}

	public static Vec3f createRotationalVector(float f, float g, float h) {
		return new Vec3f(f * 0.017453292F, g * 0.017453292F, h * 0.017453292F);
	}

	public static Vec3f createScalingVector(double d, double e, double f) {
		return new Vec3f((float) (d - 1.0), (float) (e - 1.0), (float) (f - 1.0));
	}
}
