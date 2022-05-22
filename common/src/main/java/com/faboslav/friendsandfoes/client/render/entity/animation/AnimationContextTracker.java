package com.faboslav.friendsandfoes.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.HashMap;
import java.util.Map;

@Environment(EnvType.CLIENT)
public final class AnimationContextTracker
{
	private final Map<String, ModelPartAnimationContext> animationPositionContext = new HashMap<>();
	private final Map<String, ModelPartAnimationContext> animationRotationContext = new HashMap<>();

	public boolean contains(String modelPartName, ModelPartAnimationType type) {
		if (type == ModelPartAnimationType.POSITION) {
			return this.animationPositionContext.containsKey(modelPartName);
		} else if (type == ModelPartAnimationType.ROTATION) {
			return this.animationRotationContext.containsKey(modelPartName);
		} else {
			throw new RuntimeException(String.format("Invalid animation type '%s.'", type));
		}
	}

	public ModelPartAnimationContext get(String modelPartName, ModelPartAnimationType type) {
		if (type == ModelPartAnimationType.POSITION) {
			return this.animationPositionContext.get(modelPartName);
		} else if (type == ModelPartAnimationType.ROTATION) {
			return this.animationRotationContext.get(modelPartName);
		} else {
			throw new RuntimeException(String.format("Invalid animation type '%s.'", type));
		}
	}

	public void add(String modelPartName, ModelPartAnimationType type, ModelPartAnimationContext animationContext) {
		if (type == ModelPartAnimationType.POSITION) {
			this.animationPositionContext.put(modelPartName, animationContext);
		} else if (type == ModelPartAnimationType.ROTATION) {
			this.animationRotationContext.put(modelPartName, animationContext);
		} else {
			throw new RuntimeException(String.format("Invalid animation type '%s.'", type));
		}
	}

	public void remove(String modelPartName, ModelPartAnimationType type) {
		if (type == ModelPartAnimationType.POSITION) {
			this.animationPositionContext.remove(modelPartName);
		} else if (type == ModelPartAnimationType.ROTATION) {
			this.animationRotationContext.remove(modelPartName);
		} else {
			throw new RuntimeException(String.format("Invalid animation type '%s.'", type));
		}
	}
}
