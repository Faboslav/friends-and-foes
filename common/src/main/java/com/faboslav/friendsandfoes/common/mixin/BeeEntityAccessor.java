package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.entity.passive.BeeEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BeeEntity.class)
public interface BeeEntityAccessor
{
	@Invoker("setHasNectar")
	void invokeSetHasNectar(boolean hasNectar);

	@Accessor
	int getTicksUntilCanPollinate();

	@Accessor("ticksUntilCanPollinate")
	void setTicksUntilCanPollinate(int ticksUntilCanPollinate);
}