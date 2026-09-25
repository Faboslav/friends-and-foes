//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public final class RascalRenderState extends LivingEntityRenderState
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState nodAnimationState = new AnimationState();
	public final AnimationState giveRewardAnimationState = new AnimationState();

	public RascalRenderState() {
	}
}
//?}