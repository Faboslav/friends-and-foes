package com.faboslav.friendsandfoes.entity.animation;

import org.joml.Vector3f;

public record Keyframe(
	float timestamp,
	Vector3f target,
	Transformation.Interpolation interpolation
)
{
	public Keyframe(float timestamp, Vector3f target, Transformation.Interpolation interpolation) {
		this.timestamp = timestamp;
		this.target = target;
		this.interpolation = interpolation;
	}

	public float timestamp() {
		return this.timestamp;
	}

	public Vector3f target() {
		return this.target;
	}

	public Transformation.Interpolation interpolation() {
		return this.interpolation;
	}
}
