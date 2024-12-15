package com.faboslav.friendsandfoes.common.client.render.entity.animation;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.animation.loader.json.AnimationLoader;

import java.util.ArrayList;

public final class RascalAnimations
{
	public static final AnimationHolder IDLE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("rascal/idle"));
	public static final AnimationHolder NOD = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("rascal/nod"));
	public static final AnimationHolder GIVE_REWARD = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("rascal/give_reward"));
	public static final AnimationHolder WALK = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("rascal/walk"));
	public static final ArrayList<AnimationHolder> ANIMATIONS = new ArrayList<>()
	{{
		add(IDLE);
		add(NOD);
		add(GIVE_REWARD);
	}};

	private RascalAnimations() {
	}
}