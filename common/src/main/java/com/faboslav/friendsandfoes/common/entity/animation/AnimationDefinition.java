package com.faboslav.friendsandfoes.common.entity.animation;

import com.google.common.collect.Maps;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Animation loading related code is based on NeoForge code
 *
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
public record AnimationDefinition(String name, float lengthInSeconds, boolean looping, Map<String, List<AnimationChannel>> boneAnimations) {
	public AnimationDefinition(String name, float lengthInSeconds, boolean looping, Map<String, List<AnimationChannel>> boneAnimations) {
		this.name = name;
		this.lengthInSeconds = lengthInSeconds;
		this.looping = looping;
		this.boneAnimations = boneAnimations;
	}

	public String name() {
		return this.name;
	}

	public float lengthInSeconds() {
		return this.lengthInSeconds;
	}

	public float lengthInSeconds(float speedModifier) {
		return this.lengthInSeconds * speedModifier;
	}

	public int lengthInTicks() {
		return (int) Math.ceil(this.lengthInSeconds() * 20) + 1;
	}

	public int lengthInTicks(float speedModifier) {
		return (int) Math.ceil(this.lengthInSeconds(speedModifier) * 20) + 1;
	}

	public boolean looping() {
		return this.looping;
	}

	public Map<String, List<AnimationChannel>> boneAnimations() {
		return this.boneAnimations;
	}

	public static class Builder {
		private final String name;
		private final float lengthInSeconds;
		private final Map<String, List<AnimationChannel>> animationByBone = Maps.newHashMap();
		private boolean looping;

		public Builder(String name, float lengthInSeconds) {
			this.name = name;
			this.lengthInSeconds = lengthInSeconds;
		}

		public AnimationDefinition.Builder looping() {
			this.looping = true;
			return this;
		}

		public AnimationDefinition.Builder addAnimation(String string, AnimationChannel animationChannel) {
			this.animationByBone.computeIfAbsent(string, (stringx) -> new ArrayList<>()).add(animationChannel);
			return this;
		}

		public AnimationDefinition build() {
			return new AnimationDefinition(this.name, this.lengthInSeconds, this.looping, this.animationByBone);
		}
	}
}
