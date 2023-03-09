package com.faboslav.friendsandfoes.entity.animation;

import net.minecraft.util.math.Vec3f;

public record Keyframe(
	float timestamp,
	Vec3f target,
	Transformation.Interpolation interpolation
)
{
	public Keyframe(float timestamp, Vec3f target, Transformation.Interpolation interpolation) {
		this.timestamp = timestamp;
		this.target = target;
		this.interpolation = interpolation;
	}

	public float timestamp() {
		return this.timestamp;
	}

	public Vec3f target() {
		return this.target;
	}

	public Transformation.Interpolation interpolation() {
		return this.interpolation;
	}
}
