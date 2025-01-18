package com.faboslav.friendsandfoes.common.entity.animation;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationHolder;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;

import java.util.ArrayList;

public final class TuffGolemAnimations
{
	public static final AnimationHolder SHOW_ITEM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/show_item"));
	public static final AnimationHolder HIDE_ITEM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/hide_item"));
	public static final AnimationHolder SLEEP = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/sleep"));
	public static final AnimationHolder SLEEP_WITH_ITEM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/sleep_with_item"));
	public static final AnimationHolder WAKE = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/wake"));
	public static final AnimationHolder WAKE_WITH_ITEM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/wake_with_item"));
	public static final AnimationHolder WAKE_AND_SHOW_ITEM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/wake_and_show_item"));
	public static final AnimationHolder WAKE_AND_HIDE_ITEM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/wake_and_hide_item"));
	public static final AnimationHolder WALK = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/walk"));
	public static final AnimationHolder WALK_WITH_ITEM = AnimationLoader.INSTANCE.getAnimationHolder(FriendsAndFoes.makeID("tuff_golem/walk_with_item"));
	public static final ArrayList<AnimationHolder> ANIMATIONS = new ArrayList<>()
	{{
		add(SHOW_ITEM);
		add(HIDE_ITEM);
		add(SLEEP);
		add(SLEEP_WITH_ITEM);
		add(WAKE);
		add(WAKE_WITH_ITEM);
		add(WAKE_AND_SHOW_ITEM);
		add(WAKE_AND_HIDE_ITEM);
	}};

	private TuffGolemAnimations() {
	}
}