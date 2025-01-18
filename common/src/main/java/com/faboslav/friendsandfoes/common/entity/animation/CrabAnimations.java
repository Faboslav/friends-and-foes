package com.faboslav.friendsandfoes.common.entity.animation;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;

import java.util.ArrayList;

public final class CrabAnimations
{
	public static final AnimationHolder IDLE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("crab/idle"));
	public static final AnimationHolder WAVE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("crab/wave"));
	public static final AnimationHolder DANCE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("crab/dance"));
	public static final AnimationHolder WALK = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("crab/walk"));
	public static final ArrayList<AnimationHolder> ANIMATIONS = new ArrayList<>()
	{{
		add(IDLE);
		add(WAVE);
		add(DANCE);
	}};

	private CrabAnimations() {
	}
}