package com.faboslav.friendsandfoes.common.client.render.entity.animation.animator;

import com.faboslav.friendsandfoes.common.entity.animation.AnimationChannel;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import com.faboslav.friendsandfoes.common.entity.animation.animator.Keyframe;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.util.Mth;
import org.joml.Vector3f;

import java.util.List;
import java.util.Map;
import java.util.Optional;

//? >=1.21.3 {
import net.minecraft.client.model.EntityModel;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
*///?}

public final class KeyframeAnimator
{
	public static void animateKeyframe(
		//? >=1.21.3 {
		EntityModel<?> model,
		//?} else {
		/*HierarchicalModel<?> model,
		 *///?}
		AnimationDefinition animationDefinition,
		long runningTime,
		float scale,
		Vector3f vector3f,
		float speedModifier
	) {
		float g = getElapsedSeconds(animationDefinition, runningTime, speedModifier);

		for (Map.Entry<String, List<AnimationChannel>> entry : animationDefinition.boneAnimations().entrySet()) {
			//? if >=1.21.6 {
			Optional<ModelPart> optional = getAnyDescendantWithName(model, entry.getKey());
			//?} else {
			/*Optional<ModelPart> optional = model.getAnyDescendantWithName(entry.getKey());
			*///?}
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
					var target = animationChannel.target();

					if(target == AnimationChannel.Target.POSITION) {
						modelPart.offsetPos(vector3f);
					} else if(target == AnimationChannel.Target.ROTATION) {
						modelPart.offsetRotation(vector3f);
					} else if(target == AnimationChannel.Target.SCALE) {
						modelPart.offsetScale(vector3f);
					}
				});
			});
		}
	}

	// TODO rework this whole thing later for baking
	//? if >=1.21.6 {
	private static Optional<ModelPart> getAnyDescendantWithName(
		EntityModel<?> model,
		String name
	) {
		return name.equals("root") ? Optional.of(model.root()) : model.root().getAllParts().stream().filter((modelPart) -> modelPart.hasChild(name)).findFirst().map((modelPart) -> modelPart.getChild(name));
	}
	//?}

	private static float getElapsedSeconds(AnimationDefinition animationDefinition, long l, float speedModifier) {
		float f = (float)l / 1000.0F;
		return animationDefinition.looping() ? f % (animationDefinition.lengthInSeconds() * speedModifier) : f;
	}
}
