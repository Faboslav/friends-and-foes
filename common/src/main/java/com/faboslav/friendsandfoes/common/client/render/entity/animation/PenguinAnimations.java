package com.faboslav.friendsandfoes.common.client.render.entity.animation;

import com.faboslav.friendsandfoes.common.entity.animation.Animation;
import com.faboslav.friendsandfoes.common.entity.animation.Keyframe;
import com.faboslav.friendsandfoes.common.entity.animation.Transformation;
import com.faboslav.friendsandfoes.common.entity.animation.VectorHelper;

import java.util.ArrayList;

public final class PenguinAnimations
{
	public static final KeyframeAnimation IDLE;
	public static final KeyframeAnimation WALK;
	public static final KeyframeAnimation SWIM;
	public static final ArrayList<KeyframeAnimation> ANIMATIONS;

	public PenguinAnimations() {
	}

	static {
		IDLE = new KeyframeAnimation("idle", Animation.Builder.create(1f).looping().build());
		WALK = new KeyframeAnimation("walk", Animation.Builder.create(1.0F).looping()
			.addBoneAnimation("body", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.24F, VectorHelper.createRotationalVector(-7.5F, 0.0F, 5.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.52F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.76F, VectorHelper.createRotationalVector(-7.5F, 0.0F, -5.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(-2.14F, 0.0F, -1.43F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.08F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.28F, VectorHelper.createRotationalVector(-7.5F, 0.0F, 5.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.56F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.84F, VectorHelper.createRotationalVector(-7.5F, 0.0F, -5.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0F, VectorHelper.createRotationalVector(-2.14F, 0.0F, -1.43F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftWing", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, -50.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.48F, VectorHelper.createRotationalVector(0.0F, 5.0F, -50.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, -50.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightWing", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, 52.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.48F, VectorHelper.createRotationalVector(0.0F, -5.0F, 52.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, 52.5F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftLeg", new Transformation(Transformation.Type.TRANSLATE,
				new Keyframe(0.0F, VectorHelper.createTranslationalVector(0.0F, 0.0F, -2.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.24F, VectorHelper.createTranslationalVector(0.0F, 0.0F, -0.13F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.52F, VectorHelper.createTranslationalVector(0.0F, 0.1F, 1.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.76F, VectorHelper.createTranslationalVector(0.0F, 1.0F, -0.11F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0F, VectorHelper.createTranslationalVector(0.0F, 0.0F, -2.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightLeg", new Transformation(Transformation.Type.TRANSLATE,
				new Keyframe(0.0F, VectorHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.24F, VectorHelper.createTranslationalVector(0.0F, 1.0F, 0.12F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.52F, VectorHelper.createTranslationalVector(0.0F, 0.1F, -2.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.76F, VectorHelper.createTranslationalVector(0.0F, 0.0F, -0.13F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0F, VectorHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.LINEAR)
			))
			.build());
		SWIM = new KeyframeAnimation("swim", Animation.Builder.create(0.68F).looping()
			.addBoneAnimation("main", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(90.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("main", new Transformation(Transformation.Type.TRANSLATE,
				new Keyframe(0.0F, VectorHelper.createTranslationalVector(0.0F, -9.0F, -4.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("body", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2F, VectorHelper.createRotationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.48F, VectorHelper.createRotationalVector(0.0F, -2.5F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.68F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(-90.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2F, VectorHelper.createRotationalVector(-90.0F, -2.5F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.48F, VectorHelper.createRotationalVector(-90.0F, 2.5F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.68F, VectorHelper.createRotationalVector(-90.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("head", new Transformation(Transformation.Type.TRANSLATE,
				new Keyframe(0.0F, VectorHelper.createTranslationalVector(0.0F, 3.0F, -3.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftWing", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(0.0F, -90.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftWing", new Transformation(Transformation.Type.TRANSLATE,
				new Keyframe(0.0F, VectorHelper.createTranslationalVector(3.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightWing", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(0.0F, 90.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightWing", new Transformation(Transformation.Type.TRANSLATE,
				new Keyframe(0.0F, VectorHelper.createTranslationalVector(-3.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("leftLeg", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.36F, VectorHelper.createRotationalVector(132.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.68F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.addBoneAnimation("rightLeg", new Transformation(Transformation.Type.ROTATE,
				new Keyframe(0.0F, VectorHelper.createRotationalVector(132.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.36F, VectorHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.68F, VectorHelper.createRotationalVector(132.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
			))
			.build());
		ANIMATIONS = new ArrayList<>()
		{{
			add(IDLE);
		}};
	}
}