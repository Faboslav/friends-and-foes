package com.faboslav.friendsandfoes.common.entity.animation.animator;

import com.faboslav.friendsandfoes.common.entity.animation.AnimationChannel;
import org.joml.Vector3f;

public record Keyframe(float timestamp, Vector3f target, AnimationChannel.Interpolation interpolation) {
	public Keyframe(float timestamp, Vector3f target, AnimationChannel.Interpolation interpolation) {
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

	public AnimationChannel.Interpolation interpolation() {
		return this.interpolation;
	}
}
