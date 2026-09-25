//? if >=1.21.3 {
package com.faboslav.friendsandfoes.common.client.render.entity.state;

import net.minecraft.client.renderer.entity.state.LivingEntityRenderState;
import net.minecraft.world.entity.AnimationState;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public final class BarnacleRenderState extends LivingEntityRenderState
{
	public final AnimationState idleAnimationState = new AnimationState();
	public final AnimationState tentacleAttackAnimationState = new AnimationState();
	public final AnimationState attackAnimationState = new AnimationState();
	public boolean isUnderWater;
	@Nullable
	public Vec3 tentacleTargetPosition;
	public float tentacleExtension;

	public BarnacleRenderState() {
	}
}
//?}
