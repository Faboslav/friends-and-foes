//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public final class WildfireRenderState extends LivingEntityRenderState
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState shieldRotationAnimationState = new AnimationState();
	public final AnimationState shockwaveAnimationState = new AnimationState();
	public int activeShieldsCount;
	public WildfireRenderState() {
	}
}
//?}