//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.phys.Vec2;

public final class GlareRenderState extends LivingEntityRenderState
{
	public final AnimationState sitAnimationState = new AnimationState();
	public final AnimationState flyAnimationState = new AnimationState();
	public Vec2 eyesPositionOffset = Vec2.ZERO;
	public boolean isGrumpy;
	public boolean isOrderedToSit;
	public boolean hasFlower;

	public GlareRenderState() {
	}
}
//?}