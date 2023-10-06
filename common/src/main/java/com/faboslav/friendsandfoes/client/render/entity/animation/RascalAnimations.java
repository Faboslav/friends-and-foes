package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.entity.animation.Animation;
import com.faboslav.friendsandfoes.entity.animation.Keyframe;
import com.faboslav.friendsandfoes.entity.animation.Transformation;
import com.faboslav.friendsandfoes.entity.animation.VectorHelper;

import java.util.ArrayList;

public final class RascalAnimations
{
	public static final KeyframeAnimation NOD;
	public static final KeyframeAnimation GIVE_REWARD;

	public static final ArrayList<KeyframeAnimation> ANIMATIONS;

	public RascalAnimations() {
	}

	static {
		NOD = new KeyframeAnimation("nod", Animation.Builder.create(0.4167f)
			.addBoneAnimation("head",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createTranslationalVector(0f, 2f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("head",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833f, VectorHelper.createRotationalVector(15f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftArm",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createTranslationalVector(0f, 2f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createRotationalVector(-15f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(-25f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f, VectorHelper.createRotationalVector(5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightArm",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createTranslationalVector(0f, 2f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f, VectorHelper.createRotationalVector(15f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(25f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f, VectorHelper.createRotationalVector(5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftLeg",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createTranslationalVector(0f, 2f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftLeg",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createRotationalVector(15f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightLeg",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createTranslationalVector(0f, 2f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightLeg",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createRotationalVector(-15f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(-20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f, VectorHelper.createRotationalVector(5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("bag",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createTranslationalVector(0f, 2f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("bag",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createRotationalVector(10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(15f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("body",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createTranslationalVector(0f, 2f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("body",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR))).build()
		);
		GIVE_REWARD = new KeyframeAnimation("give reward", Animation.Builder.create(0.6766666f).looping()
			.addBoneAnimation("head",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createTranslationalVector(0f, 0f, 1f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("head",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(25f, -2.5f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftArm",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createTranslationalVector(0f, -2f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(-50f, -25f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f, VectorHelper.createRotationalVector(-40f, -40f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f, VectorHelper.createRotationalVector(-50f, -25f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f, VectorHelper.createRotationalVector(-50f, -25f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightArm",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createTranslationalVector(0f, -2f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createRotationalVector(50f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f, VectorHelper.createRotationalVector(-50f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(-50f, 20f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f, VectorHelper.createRotationalVector(-50f, 20f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("bag",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f, VectorHelper.createTranslationalVector(2f, -9f, -9f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createTranslationalVector(-2.5f, -10f, -9f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f, VectorHelper.createTranslationalVector(-2.5f, -10f, -9f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("bag",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f, VectorHelper.createRotationalVector(-20f, 160f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f, VectorHelper.createRotationalVector(-25f, 180f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f, VectorHelper.createRotationalVector(-20f, 200f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f, VectorHelper.createRotationalVector(-15f, 190f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f, VectorHelper.createRotationalVector(-25f, 185f, 0f),
						Transformation.Interpolations.LINEAR))).build()
		);
	}

	static {
		ANIMATIONS = new ArrayList<>()
		{{
			add(NOD);
			add(GIVE_REWARD);
		}};
	}
}