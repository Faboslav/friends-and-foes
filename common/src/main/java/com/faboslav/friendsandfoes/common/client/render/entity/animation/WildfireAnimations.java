package com.faboslav.friendsandfoes.common.client.render.entity.animation;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.animation.loader.json.AnimationLoader;

import java.util.ArrayList;

public final class WildfireAnimations
{
	public static final AnimationHolder IDLE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("wildfire/idle"));
	public static final AnimationHolder WALK = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("wildfire/walk"));
	public static final AnimationHolder SHIELD_ROTATION = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("wildfire/shield_rotation"));
	public static final AnimationHolder SHOCKWAVE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("wildfire/shockwave"));
	public static final ArrayList<AnimationHolder> TRACKED_ANIMATIONS = new ArrayList<>()
	{{
		add(IDLE);
		add(SHOCKWAVE);
	}};

	private WildfireAnimations() {
	}
}
