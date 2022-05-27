package com.faboslav.friendsandfoes.entity;

import com.faboslav.friendsandfoes.client.render.entity.animation.AnimationContextTracker;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

public interface AnimatedEntity
{
	AnimationContextTracker getAnimationContextTracker();
}
