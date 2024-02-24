package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.entity.animation.Animation;

import java.util.ArrayList;

public final class BarnacleAnimations
{
	public static final KeyframeAnimation IDLE;
	public static final ArrayList<KeyframeAnimation> ANIMATIONS;

	public BarnacleAnimations() {
	}

	static {
		IDLE = new KeyframeAnimation("idle", Animation.Builder.create(1f).looping().build()
		);
		ANIMATIONS = new ArrayList<>()
		{{
			add(IDLE);
		}};
	}
}