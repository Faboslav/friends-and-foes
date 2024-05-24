package com.faboslav.friendsandfoes.common.client.render.entity.animation;

import com.faboslav.friendsandfoes.common.entity.animation.Animation;
import com.faboslav.friendsandfoes.common.entity.animation.Keyframe;
import com.faboslav.friendsandfoes.common.entity.animation.Transformation;
import com.faboslav.friendsandfoes.common.entity.animation.VectorHelper;

import java.util.ArrayList;

public final class BarnacleAnimations
{
	public static final KeyframeAnimation IDLE;
	public static final KeyframeAnimation SWIM;
	public static final ArrayList<KeyframeAnimation> ANIMATIONS;

	public BarnacleAnimations() {
	}

	static {
		IDLE = new KeyframeAnimation("idle", Animation.Builder.create(1f).looping().build());
		SWIM = new KeyframeAnimation("swim", Animation.Builder.create(1f).looping()
			.addBoneAnimation("topLeftMouth",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createRotationalVector(0f, -5f, 5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f, VectorHelper.createRotationalVector(0f, -20f, 20f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f, VectorHelper.createRotationalVector(0f, -15f, 15f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f, VectorHelper.createRotationalVector(0f, -12.5f, 12.5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f, VectorHelper.createRotationalVector(0f, -5f, 5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("topRightMouth",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createRotationalVector(0f, 5f, -5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f, VectorHelper.createRotationalVector(0f, 20f, -20f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f, VectorHelper.createRotationalVector(0f, 15f, -15f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f, VectorHelper.createRotationalVector(0f, 12.5f, -12.5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f, VectorHelper.createRotationalVector(0f, 5f, -5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("bottomLeftMouth",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createRotationalVector(5f, 0f, -5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f, VectorHelper.createRotationalVector(20f, 0f, -20f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f, VectorHelper.createRotationalVector(15f, 0f, -15f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f, VectorHelper.createRotationalVector(12.5f, 0f, -12.5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f, VectorHelper.createRotationalVector(5f, 0f, -5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("bottomRightMouth",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createRotationalVector(5f, 0f, 5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f, VectorHelper.createRotationalVector(20f, 0f, 20f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f, VectorHelper.createRotationalVector(15f, 0f, 15f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f, VectorHelper.createRotationalVector(12.5f, 0f, 12.5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f, VectorHelper.createRotationalVector(5f, 0f, 5f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR))).build());
		ANIMATIONS = new ArrayList<>()
		{{
			add(IDLE);
		}};
	}
}