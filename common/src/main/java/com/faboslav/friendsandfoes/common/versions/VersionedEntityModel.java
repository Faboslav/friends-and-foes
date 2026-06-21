package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.world.entity.AnimationState;

//? if >= 1.21.6 {
import net.minecraft.client.animation.KeyframeAnimation;
//?} else {
/*import net.minecraft.client.animation.AnimationDefinition;
*///?}

//? if >= 1.21.3{
import net.minecraft.client.model.EntityModel;
//?} else {
/*import net.minecraft.client.model.HierarchicalModel;
*///?}

public class VersionedEntityModel
{
	//? if >= 1.21.6 {
	public static void Animate(EntityModel<?> entityModel, KeyframeAnimation animation, AnimationState animationState, float ageInTicks)
	{
		Animate(entityModel, animation, animationState, ageInTicks, 1.0F);
	}

	public static void Animate(EntityModel<?> entityModel, KeyframeAnimation animation, AnimationState animationState, float ageInTicks, float speedMultiplier)
	{
		animation.apply(animationState, ageInTicks, speedMultiplier);
	}
	//?} else if >= 1.21.3 {
	/*public static void Animate(EntityModel<?> entityModel, AnimationDefinition animation, AnimationState animationState, float ageInTicks)
	{
		Animate(entityModel, animation, animationState, ageInTicks, 1.0F);
	}

	public static void Animate(EntityModel<?> entityModel, AnimationDefinition animation, AnimationState animationState, float ageInTicks, float speedMultiplier)
	{
		entityModel.animate(animationState, animation, ageInTicks);
	}
	*///?} else {
	/*public static void Animate(HierarchicalModel<?> entityModel, AnimationDefinition animation, AnimationState animationState, float ageInTicks)
	{
		Animate(entityModel, animation, animationState, ageInTicks, 1.0F);
	}

	public static void Animate(HierarchicalModel<?> entityModel, AnimationDefinition animation, AnimationState animationState, float ageInTicks, float speedMultiplier)
	{
		entityModel.animate(animationState, animation, ageInTicks, speedMultiplier);
	}
	*///?}

	//? if >= 1.21.6 {
	public static void AnimateWalk(EntityModel<?> entityModel, KeyframeAnimation animation, float limbSwing, float limbSwingAmount, float timeMultiplier, float speedMultiplier)
	{
		animation.applyWalk(limbSwing, limbSwingAmount, timeMultiplier, speedMultiplier);
	}
	//?} else if >= 1.21.3 {
	/*public static void AnimateWalk(EntityModel<?> entityModel, AnimationDefinition animation, float limbSwing, float limbSwingAmount, float timeMultiplier, float speedMultiplier)
	{
		entityModel.animateWalk(animation, limbSwing, limbSwingAmount, timeMultiplier, speedMultiplier);
	}
	*///?} else {
	/*public static void AnimateWalk(HierarchicalModel<?> entityModel, AnimationDefinition animation, float limbSwing, float limbSwingAmount, float timeMultiplier, float speedMultiplier)
	{
		entityModel.animateWalk(animation, limbSwing, limbSwingAmount, timeMultiplier, speedMultiplier);
	}
	*///?}
}
