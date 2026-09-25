//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.MaulerEntity;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

public final class MaulerRenderState extends LivingEntityRenderState
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState snapAnimationState = new AnimationState();
	public final AnimationState burrowDownAnimationState = new AnimationState();
	public final AnimationState burrowUpAnimationState = new AnimationState();
	public MaulerEntity.Type type = MaulerEntity.Type.DESERT;
	public boolean isBurrowedDown;

	public MaulerRenderState() {
	}
}
//?}