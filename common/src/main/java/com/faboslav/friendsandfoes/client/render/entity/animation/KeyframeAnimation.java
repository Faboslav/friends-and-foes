package com.faboslav.friendsandfoes.client.render.entity.animation;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.animation.Animation;

@Environment(EnvType.CLIENT)
public class KeyframeAnimation
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

	public Animation getAnimation() {
		return this.animation;
	}
}
