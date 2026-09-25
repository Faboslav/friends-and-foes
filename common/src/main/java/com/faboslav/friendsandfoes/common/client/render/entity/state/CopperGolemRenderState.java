//? if >=1.21.3 && <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.level.block.WeatheringCopper;

public final class CopperGolemRenderState extends LivingEntityRenderState
{
	public final AnimationState spinHeadAnimationState = new AnimationState();
	public final AnimationState pressButtonUpAnimationState = new AnimationState();
	public final AnimationState pressButtonDownAnimationState = new AnimationState();
	public WeatheringCopper.WeatherState oxidationLevel = WeatheringCopper.WeatherState.UNAFFECTED;
	public float animationSpeedModifier = 1.0F;
	public float movementSpeedModifier = 1.0F;
	public float currentAnimationTick;
	public boolean isOxidized;

	public CopperGolemRenderState() {
	}
}
*///?}