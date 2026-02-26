package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.world.entity.animal.bee.Bee;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Bee.class)
public interface BeeEntityAccessor
{
	@Invoker("setHasNectar")
	void invokeSetHasNectar(boolean hasNectar);

	@Accessor("remainingCooldownBeforeLocatingNewFlower")
	int getTicksUntilCanPollinate();

	@Accessor("remainingCooldownBeforeLocatingNewFlower")
	void setTicksUntilCanPollinate(int ticksUntilCanPollinate);
}