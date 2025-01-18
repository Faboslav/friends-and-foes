package com.faboslav.friendsandfoes.common.entity.animation.animator.loader;

import org.joml.Vector3f;

/**
 * A function for transforming vectors into values that make sense to their keyframe's target.
 *
 * Animation loading related code is based on NeoForge code
 *
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
@FunctionalInterface
public interface AnimationKeyframeTarget {
	Vector3f apply(float x, float y, float z);
}
