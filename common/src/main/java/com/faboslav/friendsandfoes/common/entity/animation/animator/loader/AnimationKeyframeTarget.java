package com.faboslav.friendsandfoes.common.entity.animation.animator.loader;

import org.joml.Vector3f;

/**
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
@FunctionalInterface
public interface AnimationKeyframeTarget {
	Vector3f apply(float x, float y, float z);
}
