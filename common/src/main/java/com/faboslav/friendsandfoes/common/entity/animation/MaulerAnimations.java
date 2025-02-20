package com.faboslav.friendsandfoes.common.entity.animation;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;

import java.util.ArrayList;

public final class MaulerAnimations
{
	public static final AnimationHolder IDLE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("mauler/idle"));
	public static final AnimationHolder WALK = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("mauler/walk"));
	public static final AnimationHolder RUN = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("mauler/run"));
	public static final AnimationHolder SNAP = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("mauler/snap"));
	public static final ArrayList<AnimationHolder> TRACKED_ANIMATIONS = new ArrayList<>()
	{{
		add(IDLE);
	}};

	private MaulerAnimations() {
	}
}
