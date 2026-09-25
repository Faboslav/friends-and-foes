//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import com.faboslav.friendsandfoes.common.entity.TuffGolemEntity;
import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;

//? if >=26.3 {
import net.minecraft.client.renderer.item.ItemStackRenderState;
//?}

public final class TuffGolemRenderState extends LivingEntityRenderState
{
	//? if <26.3 {
	/*public TuffGolemEntity tuffGolem;
	*///?}
	public final AnimationState showItemAnimationState = new AnimationState();
	public final AnimationState hideItemAnimationState = new AnimationState();
	public final AnimationState sleepAnimationState = new AnimationState();
	public final AnimationState sleepWithItemAnimationState = new AnimationState();
	public final AnimationState wakeAnimationState = new AnimationState();
	public final AnimationState wakeWithItemAnimationState = new AnimationState();
	public final AnimationState wakeAndShowItemAnimationState = new AnimationState();
	public final AnimationState wakeAndHideItemAnimationState = new AnimationState();
	public TuffGolemEntity.Color color = TuffGolemEntity.Color.RED;
	public float movementSpeedModifier = 1.0F;
	public boolean isHoldingItem;
	public boolean isInSleepingPose;
	public boolean isDeadOrDying;
	public float partialTick;
	//? if >=26.3 {
	public final ItemStackRenderState heldItem = new ItemStackRenderState();
	//?}

	public TuffGolemRenderState() {
	}
}
//?}