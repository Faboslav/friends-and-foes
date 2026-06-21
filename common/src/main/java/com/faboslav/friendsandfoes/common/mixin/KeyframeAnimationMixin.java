package com.faboslav.friendsandfoes.common.mixin;

import net.minecraft.client.animation.AnimationDefinition;
import net.minecraft.client.animation.KeyframeAnimation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(KeyframeAnimation.class)
public interface KeyframeAnimationMixin
{
	@Accessor("definition")
	AnimationDefinition friendsandfoes$getDefinition();
}
