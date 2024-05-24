package com.faboslav.friendsandfoes.client.render.entity.animation;

import com.faboslav.friendsandfoes.entity.animation.Animation;
import com.faboslav.friendsandfoes.entity.animation.Keyframe;
import com.faboslav.friendsandfoes.entity.animation.Transformation;
import com.faboslav.friendsandfoes.entity.animation.VectorHelper;

import java.util.ArrayList;

public final class PenguinAnimations
{
	public static final KeyframeAnimation IDLE;
	public static final KeyframeAnimation WALK;
	public static final ArrayList<KeyframeAnimation> ANIMATIONS;

	public PenguinAnimations() {
	}

	static {
		IDLE = new KeyframeAnimation("idle", Animation.Builder.create(1f).looping().build());
		WALK = new KeyframeAnimation("walk", Animation.Builder.create(1f).looping().build());
		ANIMATIONS = new ArrayList<>()
		{{
			add(IDLE);
		}};
	}
}