package com.faboslav.friendsandfoes.entity.animation;

import org.joml.Vector3f;

public final class VectorHelper
{
	public static Vector3f createTranslationalVector(float f, float g, float h) {
		return new Vector3f(f, -g, h);
	}

	public static Vector3f createRotationalVector(float f, float g, float h) {
		return new Vector3f(f * 0.017453292F, g * 0.017453292F, h * 0.017453292F);
	}

	public static Vector3f createScalingVector(double d, double e, double f) {
		return new Vector3f((float) (d - 1.0), (float) (e - 1.0), (float) (f - 1.0));
	}
}
