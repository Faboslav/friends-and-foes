package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.client.animation.AnimationContextTracker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.CLIENT)
public interface AnimatedEntity
{
	AnimationContextTracker getAnimationContextTracker();
}
