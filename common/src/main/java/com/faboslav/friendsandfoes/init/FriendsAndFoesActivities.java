package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.ai.brain.Activity;

import java.util.function.Supplier;

/**
 * @see Activity
 */
public final class FriendsAndFoesActivities
{
	public final static Supplier<Activity> COPPER_GOLEM_SPIN_HEAD;
	public final static Supplier<Activity> COPPER_GOLEM_PRESS_BUTTON;
	public final static Supplier<Activity> CRAB_WAVE;
	public final static Supplier<Activity> GLARE_EAT_GLOW_BERRIES;
	public final static Supplier<Activity> GLARE_SHOW_DARK_SPOT;
	public final static Supplier<Activity> TUFF_GOLEM_HOME;
	public final static Supplier<Activity> RASCAL_WAIT;

	static {
		COPPER_GOLEM_SPIN_HEAD = RegistryHelper.registerActivity("copper_golem_spin_head", () -> new Activity("copper_golem_spin_head"));
		COPPER_GOLEM_PRESS_BUTTON = RegistryHelper.registerActivity("copper_golem_press_button", () -> new Activity("copper_golem_press_button"));
		CRAB_WAVE = RegistryHelper.registerActivity("crab_wave", () -> new Activity("crab_wave"));
		GLARE_EAT_GLOW_BERRIES = RegistryHelper.registerActivity("glare_eat_glow_berries", () -> new Activity("glare_eat_glow_berries"));
		GLARE_SHOW_DARK_SPOT = RegistryHelper.registerActivity("glare_show_dark_spot", () -> new Activity("glare_dark_spot"));
		TUFF_GOLEM_HOME = RegistryHelper.registerActivity("tuff_golem_home", () -> new Activity("tuff_golem_home"));
		RASCAL_WAIT = RegistryHelper.registerActivity("rascal_wait", () -> new Activity("rascal_wait"));
	}

	public static void init() {
	}

	private FriendsAndFoesActivities() {
	}
}
