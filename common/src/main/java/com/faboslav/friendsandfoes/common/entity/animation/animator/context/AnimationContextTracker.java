package com.faboslav.friendsandfoes.common.entity.animation.animator.context;

import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.ModelPartAnimationType;
import com.faboslav.friendsandfoes.common.client.render.entity.animation.animator.context.ModelPartAnimationContext;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;

import java.util.HashMap;
import java.util.Map;

public final class AnimationContextTracker
{
	private final Map<String, KeyframeAnimationContext> animationKeyframeContext = new HashMap<>();
	private final Map<String, ModelPartAnimationContext> animationPositionContext = new HashMap<>();
	private final Map<String, ModelPartAnimationContext> animationRotationContext = new HashMap<>();

	public KeyframeAnimationContext get(AnimationHolder animationHolder) {
		var animationName = animationHolder.get().name();
		KeyframeAnimationContext keyframeAnimationContext = this.animationKeyframeContext.get(animationName);

		if (keyframeAnimationContext == null) {
			throw new RuntimeException(String.format("Keyframe animation '%s' is not added.", animationName));
		}

		return keyframeAnimationContext;
	}

	public void add(AnimationHolder animationHolder) {
		this.animationKeyframeContext.put(animationHolder.get().name(), new KeyframeAnimationContext());
	}

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