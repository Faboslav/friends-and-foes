package com.faboslav.friendsandfoes.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

import java.util.ArrayList;

@Environment(EnvType.CLIENT)
public final class TuffGolemAnimations
{
	public static final KeyframeAnimation SHOW_ITEM;
	public static final KeyframeAnimation HIDE_ITEM;
	public static final KeyframeAnimation SLEEP;
	public static final KeyframeAnimation SLEEP_WITH_ITEM;
	public static final KeyframeAnimation WAKE;
	public static final KeyframeAnimation WAKE_WITH_ITEM;
	public static final ArrayList<KeyframeAnimation> ANIMATIONS;

	public TuffGolemAnimations() {
	}

	static {
		SHOW_ITEM = new KeyframeAnimation("showItem", Animation.Builder.create(0.4583333333333333F).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.0833F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.0833F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.0417F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		HIDE_ITEM = new KeyframeAnimation("hideItem", Animation.Builder.create(0.4583333333333333F).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, 0F, -5.75F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, -4F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0.125F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.125F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, -2F, -1.5F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, 0F, 2F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0.2917F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.375F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4583F, AnimationHelper.createScalingVector(1F, 1F, 1F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		SLEEP = new KeyframeAnimation("sleep", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(20F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		SLEEP_WITH_ITEM = new KeyframeAnimation("sleep_with_item", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-80F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(20F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
			)
		).build());
		WAKE = new KeyframeAnimation("wake", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-45F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-50F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).build());
		WAKE_WITH_ITEM = new KeyframeAnimation("wake_with_item", Animation.Builder.create(0.9166666666666666F).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("leftArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-75F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-95F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-90F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("rightArm",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, -2F, -2F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(25F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("body",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("frontCloth",
			new Transformation(
				Transformation.Targets.SCALE,
				new Keyframe(0F, AnimationHelper.createScalingVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.ROTATE,
				new Keyframe(0F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createRotationalVector(15F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.4167F, AnimationHelper.createRotationalVector(10F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-5F, 0F, 0F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0F, 0F, 0F), Transformation.Interpolations.LINEAR)
			)
		).addBoneAnimation("clothStand",
			new Transformation(
				Transformation.Targets.TRANSLATE,
				new Keyframe(0F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0F, -1F, -6F), Transformation.Interpolations.LINEAR),
				new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0F, 0F, -6F), Transformation.Interpolations.LINEAR)
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
		}};
	}
}