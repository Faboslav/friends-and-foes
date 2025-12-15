package com.faboslav.friendsandfoes.common.entity.animation.animator;

import com.faboslav.friendsandfoes.common.entity.animation.AnimationChannel;
import org.joml.Vector3fc;

public record Keyframe(float timestamp, Vector3fc preTarget, Vector3fc postTarget, AnimationChannel.Interpolation interpolation) {
	public Keyframe(float f, Vector3fc vector3fc, AnimationChannel.Interpolation interpolation) {
		this(f, vector3fc, vector3fc, interpolation);
	}
}