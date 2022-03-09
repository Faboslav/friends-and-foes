package com.faboslav.friendsandfoes.mixin;

import net.minecraft.entity.ai.pathing.EntityNavigation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(EntityNavigation.class)
public interface EntityNavigationAccessor
{
	@Accessor("nodeReachProximity")
	void setNodeReachProximity(float nodeReachProximity);
}