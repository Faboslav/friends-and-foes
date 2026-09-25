//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.CrabEntity;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public final class CrabRenderState extends LivingEntityRenderState
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState waveAnimationState = new AnimationState();
	public final AnimationState danceAnimationState = new AnimationState();
	public CrabEntity.CrabSize size = CrabEntity.CrabSize.MEDIUM;
	public float climbProgress;

	public CrabRenderState() {
	}
}
//?}