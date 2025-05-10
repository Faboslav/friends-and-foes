package com.faboslav.friendsandfoes.common.entity.animation;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;

import java.util.ArrayList;

public final class BarnacleAnimations
{
	public static final AnimationHolder IDLE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("barnacle/idle"));
	public static final AnimationHolder SWIM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("barnacle/swim"));
	public static final ArrayList<AnimationHolder> TRACKED_ANIMATIONS = new ArrayList<>()
	{{
		add(IDLE);
	}};

}
