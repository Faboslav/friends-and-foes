package com.faboslav.friendsandfoes.common.entity.animation;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.model.HierarchicalModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

public class KeyframeAnimations {
	public KeyframeAnimations() {
	}

	public static void animate(
		HierarchicalModel<?> hierarchicalModel,
		AnimationDefinition animationDefinition,
		long runningTime,
		float scale,
		Vector3f vector3f,
		float speedModifier
	) {
		float g = getElapsedSeconds(animationDefinition, runningTime);

		for (Map.Entry<String, List<AnimationChannel>> entry : animationDefinition.boneAnimations().entrySet()) {
			Optional<ModelPart> optional = hierarchicalModel.getAnyDescendantWithName(entry.getKey());
			List<AnimationChannel> channels = entry.getValue();

			optional.ifPresent(modelPart -> {
				channels.forEach(animationChannel -> {
					Keyframe[] keyframes = animationChannel.keyframes();
					int i = Math.max(0, Mth.binarySearch(0, keyframes.length, ix -> g <= keyframes[ix].timestamp() * speedModifier) - 1);
					int j = Math.min(keyframes.length - 1, i + 1);

					Keyframe keyframe = keyframes[i];
					Keyframe keyframe2 = keyframes[j];
					float h = g - keyframe.timestamp() * speedModifier;
					float k;

					if (j != i) {
						k = Mth.clamp(h / ((keyframe2.timestamp() - keyframe.timestamp()) * speedModifier), 0.0F, 1.0F);
					} else {
						k = 0.0F;
					}

					keyframe2.interpolation().apply(vector3f, k, keyframes, i, j, scale);
					animationChannel.target().apply(modelPart, vector3f);
				});
			});
		}
	}

	private static float getElapsedSeconds(AnimationDefinition animationDefinition, long l) {
		float f = (float)l / 1000.0F;
		return animationDefinition.looping() ? f % animationDefinition.lengthInSeconds() : f;
	}

	public static Vector3f posVec(float f, float g, float h) {
		return new Vector3f(f, -g, h);
	}

	public static Vector3f degreeVec(float f, float g, float h) {
		return new Vector3f(f * 0.017453292F, g * 0.017453292F, h * 0.017453292F);
	}

	public static Vector3f scaleVec(double d, double e, double f) {
		return new Vector3f((float)(d - 1.0), (float)(e - 1.0), (float)(f - 1.0));
	}
}