//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public final class PenguinRenderState extends LivingEntityRenderState
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState idleWaterAnimationState = new AnimationState();
	public final AnimationState wingFlapAnimationState = new AnimationState();
	public float swimProgress;
	public boolean isSwimming;
	public boolean isUnderWater;

	public PenguinRenderState() {
	}
}
//?}