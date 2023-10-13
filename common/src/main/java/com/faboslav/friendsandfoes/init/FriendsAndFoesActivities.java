package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.ai.brain.Activity;

import java.util.function.Supplier;

/**
 * @see Activity
 */
public final class FriendsAndFoesActivities
{
	public final static Supplier<Activity> TUFF_GOLEM_HOME;
	public final static Supplier<Activity> RASCAL_WAIT;

	static {
		TUFF_GOLEM_HOME = RegistryHelper.registerActivity("tuff_golem_home", () -> new Activity("tuff_golem_home"));
		RASCAL_WAIT = RegistryHelper.registerActivity("rascal_wait", () -> new Activity("rascal_wait"));
	}

	public static void init() {
	}

	private FriendsAndFoesActivities() {
	}
}
