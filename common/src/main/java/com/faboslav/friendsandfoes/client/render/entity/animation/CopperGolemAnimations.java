package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.entity.animation.Animation;
import com.faboslav.friendsandfoes.entity.animation.Keyframe;
import com.faboslav.friendsandfoes.entity.animation.Transformation;
import com.faboslav.friendsandfoes.entity.animation.VectorHelper;

import java.util.ArrayList;

public final class CopperGolemAnimations
{
	public static final KeyframeAnimation IDLE;
	public static final KeyframeAnimation WALK;
	public static final KeyframeAnimation SPIN_HEAD;
	public static final KeyframeAnimation PRESS_BUTTON_UP;
	public static final KeyframeAnimation PRESS_BUTTON_DOWN;

	static {
		IDLE = new KeyframeAnimation("idle", Animation.Builder.create(1f).looping().build());
		WALK = getWalkKeyframeAnimation(1.0F);
		SPIN_HEAD = getSpinHeadKeyframeAnimation(1.0F);
		PRESS_BUTTON_UP = getPressButtonUpKeyframeAnimation(1.0F);
		PRESS_BUTTON_DOWN = getPressButtonDownKeyframeAnimation(1.0F);
	}

	public static KeyframeAnimation getWalkKeyframeAnimation(float speedModifier) {
		return new KeyframeAnimation("walk", Animation.Builder.create(1.0f * speedModifier).looping()
			.addBoneAnimation("body",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f * speedModifier, VectorHelper.createRotationalVector(1.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-4f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f * speedModifier, VectorHelper.createRotationalVector(-2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f * speedModifier, VectorHelper.createRotationalVector(-0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5834334f * speedModifier, VectorHelper.createRotationalVector(2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-4f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8343334f * speedModifier, VectorHelper.createRotationalVector(-2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f * speedModifier, VectorHelper.createRotationalVector(-0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("frontCloth",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833f * speedModifier, VectorHelper.createRotationalVector(-0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833f * speedModifier, VectorHelper.createRotationalVector(-0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("backCloth",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833f * speedModifier, VectorHelper.createRotationalVector(0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f * speedModifier, VectorHelper.createRotationalVector(1.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f * speedModifier, VectorHelper.createRotationalVector(1.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f * speedModifier, VectorHelper.createRotationalVector(0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833f * speedModifier, VectorHelper.createRotationalVector(0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667f * speedModifier, VectorHelper.createRotationalVector(1.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333f * speedModifier, VectorHelper.createRotationalVector(1.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167f * speedModifier, VectorHelper.createRotationalVector(0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833f * speedModifier, VectorHelper.createRotationalVector(10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f * speedModifier, VectorHelper.createRotationalVector(30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f * speedModifier, VectorHelper.createRotationalVector(30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f * speedModifier, VectorHelper.createRotationalVector(10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833f * speedModifier, VectorHelper.createRotationalVector(-10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167f * speedModifier, VectorHelper.createRotationalVector(-10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833f * speedModifier, VectorHelper.createRotationalVector(-10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f * speedModifier, VectorHelper.createRotationalVector(-10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833f * speedModifier, VectorHelper.createRotationalVector(10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667f * speedModifier, VectorHelper.createRotationalVector(30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333f * speedModifier, VectorHelper.createRotationalVector(30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167f * speedModifier, VectorHelper.createRotationalVector(10f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftLeg",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833f * speedModifier, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f * speedModifier, VectorHelper.createRotationalVector(-20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-25f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f * speedModifier, VectorHelper.createRotationalVector(-20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f * speedModifier, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833f * speedModifier, VectorHelper.createRotationalVector(5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667f * speedModifier, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(25f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333f * speedModifier, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167f * speedModifier, VectorHelper.createRotationalVector(5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightLeg",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.0833f * speedModifier, VectorHelper.createRotationalVector(5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.1667f * speedModifier, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(25f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3333f * speedModifier, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167f * speedModifier, VectorHelper.createRotationalVector(5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5833f * speedModifier, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6667f * speedModifier, VectorHelper.createRotationalVector(-20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-25f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8333f * speedModifier, VectorHelper.createRotationalVector(-20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167f * speedModifier, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("head",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("head",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.08343333f * speedModifier, VectorHelper.createRotationalVector(2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f * speedModifier, VectorHelper.createRotationalVector(-2.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f * speedModifier, VectorHelper.createRotationalVector(-0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5834334f * speedModifier, VectorHelper.createRotationalVector(2f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8343334f * speedModifier, VectorHelper.createRotationalVector(-2.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f * speedModifier, VectorHelper.createRotationalVector(-0.5f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rod",
				new Transformation(Transformation.Type.TRANSLATE,
					new Keyframe(0f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f * speedModifier, VectorHelper.createTranslationalVector(0f, -1f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createTranslationalVector(0f, -1f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5834334f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createTranslationalVector(0f, -1f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createTranslationalVector(0f, -1f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8343334f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createTranslationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR))).build()
		);
	}

	public static KeyframeAnimation getSpinHeadKeyframeAnimation(float speedModifier) {
		return new KeyframeAnimation("spin_head", Animation.Builder.create(0.75f * speedModifier)
			.addBoneAnimation("head",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.125f * speedModifier, VectorHelper.createRotationalVector(0f, 90f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.20834334f * speedModifier, VectorHelper.createRotationalVector(0f, 90f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f * speedModifier, VectorHelper.createRotationalVector(0f, 180f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f * speedModifier, VectorHelper.createRotationalVector(0f, 180f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5416766f * speedModifier, VectorHelper.createRotationalVector(0f, 270f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.625f * speedModifier, VectorHelper.createRotationalVector(0f, 270f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(0f, 360f, 0f),
						Transformation.Interpolations.LINEAR))).build()
		);
	}

	public static KeyframeAnimation getPressButtonUpKeyframeAnimation(float speedModifier) {
		return new KeyframeAnimation("press_button_up", Animation.Builder.create(1.5416767f * speedModifier)
			.addBoneAnimation("body",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.125f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-4f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583433f * speedModifier, VectorHelper.createRotationalVector(-6f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(-4f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.875f * speedModifier, VectorHelper.createRotationalVector(-6f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0834333f * speedModifier, VectorHelper.createRotationalVector(-4f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2916767f * speedModifier, VectorHelper.createRotationalVector(-6f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.4167667f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.5416767f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.125f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.20834334f * speedModifier, VectorHelper.createRotationalVector(-110f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.375f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583433f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5416766f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5834334f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.625f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8343334f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.875f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9583434f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0416767f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0834333f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2916767f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.3433333f * speedModifier, VectorHelper.createRotationalVector(-110f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.375f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.4167667f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.5416767f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.125f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.20834334f * speedModifier, VectorHelper.createRotationalVector(-110f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583433f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5416766f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5834334f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083434f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.875f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9583434f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0834333f * speedModifier, VectorHelper.createRotationalVector(-180f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.125f * speedModifier, VectorHelper.createRotationalVector(-170f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.1676667f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2083433f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2916767f * speedModifier, VectorHelper.createRotationalVector(-120f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.3433333f * speedModifier, VectorHelper.createRotationalVector(-110f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.375f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.4167667f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.5416767f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR))).build()
		);
	}

	public static KeyframeAnimation getPressButtonDownKeyframeAnimation(float speedModifier) {
		return new KeyframeAnimation("press_button_down", Animation.Builder.create(1.5416767f * speedModifier)
			.addBoneAnimation("body",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.041676664f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.125f * speedModifier, VectorHelper.createRotationalVector(6f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(18f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583433f * speedModifier, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(18f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.875f * speedModifier, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0834333f * speedModifier, VectorHelper.createRotationalVector(18f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2916767f * speedModifier, VectorHelper.createRotationalVector(20f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.4167667f * speedModifier, VectorHelper.createRotationalVector(6f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.5f * speedModifier, VectorHelper.createRotationalVector(-1f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.5416767f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("leftArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.125f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.20834334f * speedModifier, VectorHelper.createRotationalVector(-70f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.3433333f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.375f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4167667f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583433f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5416766f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5834334f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.625f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.8343334f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.875f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9583434f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0416767f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0834333f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2916767f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.3433333f * speedModifier, VectorHelper.createRotationalVector(-70f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.375f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.4167667f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.5416767f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR)))
			.addBoneAnimation("rightArm",
				new Transformation(Transformation.Type.ROTATE,
					new Keyframe(0f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.125f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.16766666f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.20834334f * speedModifier, VectorHelper.createRotationalVector(-70f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.25f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.4583433f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5416766f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.5834334f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.6766666f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7083434f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.75f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.7916766f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.875f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9167666f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(0.9583434f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.0834333f * speedModifier, VectorHelper.createRotationalVector(-140f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.125f * speedModifier, VectorHelper.createRotationalVector(-130f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.1676667f * speedModifier, VectorHelper.createRotationalVector(-90f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2083433f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.2916767f * speedModifier, VectorHelper.createRotationalVector(-80f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.3433333f * speedModifier, VectorHelper.createRotationalVector(-70f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.375f * speedModifier, VectorHelper.createRotationalVector(-40f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.4167667f * speedModifier, VectorHelper.createRotationalVector(-30f, 0f, 0f),
						Transformation.Interpolations.LINEAR),
					new Keyframe(1.5416767f * speedModifier, VectorHelper.createRotationalVector(0f, 0f, 0f),
						Transformation.Interpolations.LINEAR))).build()
		);
	}

	public static ArrayList<KeyframeAnimation> getAnimations(float speedModifier) {
		return new ArrayList<>()
		{{
			add(IDLE);
			add(getWalkKeyframeAnimation(speedModifier));
			add(getSpinHeadKeyframeAnimation(speedModifier));
			add(getPressButtonUpKeyframeAnimation(speedModifier));
			add(getPressButtonDownKeyframeAnimation(speedModifier));
		}};
	}
}