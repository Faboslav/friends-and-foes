package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.WalkAnimationState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(WalkAnimationState.class)
public interface LimbAnimatorAccessor
{
	@Accessor("speedOld")
	float getPresSpeed();

	@Accessor("speedOld")
	void setPrevSpeed(float prevSpeed);

	@Accessor("position")
	void setPos(float pos);
}
