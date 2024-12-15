package com.faboslav.friendsandfoes.common.entity.animation.loader;

import org.joml.Vector3f;

/**
 * A function for transforming vectors into values that make sense to their keyframe's target.
 */
@FunctionalInterface
public interface AnimationKeyframeTarget {
	Vector3f apply(float x, float y, float z);
}
