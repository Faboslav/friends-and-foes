package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.entity.animation.Animation;
import com.faboslav.friendsandfoes.entity.animation.Keyframe;
import com.faboslav.friendsandfoes.entity.animation.Transformation;
import com.faboslav.friendsandfoes.entity.animation.VectorHelper;

import java.util.ArrayList;

public final class TuffGolemAnimations
{
	public static final KeyframeAnimation SHOW_ITEM;
	public static final KeyframeAnimation HIDE_ITEM;
	public static final KeyframeAnimation SLEEP;
	public static final KeyframeAnimation SLEEP_WITH_ITEM;
	public static final KeyframeAnimation WAKE;
	public static final KeyframeAnimation WAKE_WITH_ITEM;
	public static final KeyframeAnimation WAKE_AND_SHOW_ITEM;
	public static final KeyframeAnimation WAKE_AND_HIDE_ITEM;
	public static final ArrayList<KeyframeAnimation> ANIMATIONS;

	public TuffGolemAnimations() {
	}

	static {
		SHOW_ITEM = new KeyframeAnimation("showItem", Animation.Builder.create(0.4583333333333333F).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.0833F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.0833F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.0833F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.0417F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		HIDE_ITEM = new KeyframeAnimation("hideItem", Animation.Builder.create(0.4583333333333333F).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0.125F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.125F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.125F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0.2917F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.25F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		SLEEP = new KeyframeAnimation("sleep", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(20F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		SLEEP_WITH_ITEM = new KeyframeAnimation("sleep_with_item", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(20F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).build());
		WAKE = new KeyframeAnimation("wake", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.4167F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.4167F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		WAKE_WITH_ITEM = new KeyframeAnimation("wake_with_item", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).build());
		WAKE_AND_SHOW_ITEM = new KeyframeAnimation("wake_and_show_item", Animation.Builder.create(1.375F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.4167F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.4167F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(1F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0.9167F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0.9167F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).build());
		WAKE_AND_HIDE_ITEM = new KeyframeAnimation("wake_and_hide_item", Animation.Builder.create(1.375F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, VectorHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.1667F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.3333F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(1.2083F, VectorHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2917F, VectorHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(0F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2083F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2917F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.ROTATE,
				new Keyframe(0F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, VectorHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.TRANSLATE,
				new Keyframe(0F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, VectorHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, VectorHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.0417F, VectorHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Type.SCALE,
				new Keyframe(1.0417F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.25F, VectorHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.2917F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(1.375F, VectorHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		ANIMATIONS = new ArrayList<>()
		{{
			add(SHOW_ITEM);
			add(HIDE_ITEM);
			add(SLEEP);
			add(SLEEP_WITH_ITEM);
			add(WAKE);
			add(WAKE_WITH_ITEM);
			add(WAKE_AND_SHOW_ITEM);
			add(WAKE_AND_HIDE_ITEM);
		}};
	}
}