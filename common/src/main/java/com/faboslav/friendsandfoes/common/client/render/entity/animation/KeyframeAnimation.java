package com.faboslav.friendsandfoes.common.client.render.entity.animation;

import com.faboslav.friendsandfoes.common.entity.animation.Animation;

public final class KeyframeAnimation
{
	private final String name;
	private final Animation animation;

	public KeyframeAnimation(
		String name,
		Animation animation
	) {
		this.name = name;
		this.animation = animation;
	}

	public String getName() {
		return this.name;
	}

	public int getAnimationLengthInTicks() {
		return (int) Math.ceil(this.getAnimation().lengthInSeconds() * 20) + 1;
	}

	public Animation getAnimation() {
		return this.animation;
	}
}
