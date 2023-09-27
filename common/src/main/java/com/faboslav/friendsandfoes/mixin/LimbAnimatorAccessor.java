package com.faboslav.friendsandfoes.mixin;

import net.minecraft.entity.LimbAnimator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(LimbAnimator.class)
public interface LimbAnimatorAccessor
{
	@Accessor("prevSpeed")
	float getPresSpeed();

	@Accessor("prevSpeed")
	void setPrevSpeed(float prevSpeed);

	@Accessor("pos")
	void setPos(float pos);
}
