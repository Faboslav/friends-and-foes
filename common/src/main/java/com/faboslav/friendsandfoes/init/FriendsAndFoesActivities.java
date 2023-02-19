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

	static {
		TUFF_GOLEM_HOME = RegistryHelper.registerActivity("tuff_golem_home", () -> new Activity("tuff_golem_home"));
	}

	public static void init() {
	}

	private FriendsAndFoesActivities() {
	}
}
