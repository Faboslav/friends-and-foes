//? if <= 1.21.8 {
/*package com.faboslav.friendsandfoes.common.entity.animation;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;

import java.util.ArrayList;

public final class CopperGolemAnimations
{
	public static final AnimationHolder IDLE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("copper_golem/idle"));
	public static final AnimationHolder WALK = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("copper_golem/walk"));
	public static final AnimationHolder SPIN_HEAD = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("copper_golem/spin_head"));
	public static final AnimationHolder PRESS_BUTTON_UP = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("copper_golem/press_button_up"));
	public static final AnimationHolder PRESS_BUTTON_DOWN = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("copper_golem/press_button_down"));
	public static final ArrayList<AnimationHolder> TRACKED_ANIMATIONS = new ArrayList<>()
	{{
		add(IDLE);
		add(SPIN_HEAD);
		add(PRESS_BUTTON_UP);
		add(PRESS_BUTTON_DOWN);
	}};
}
*///?}