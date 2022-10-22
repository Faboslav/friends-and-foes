package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.ai.brain.Activity;

import java.util.function.Supplier;

/**
 * @see Activity
 */
public final class FriendsAndFoesActivities
{
	public final static Supplier<Activity> WILDFIRE_BARRAGE_ATTACK;
	public final static Supplier<Activity> WILDFIRE_SHOCKWAVE_ATTACK;

	static {
		WILDFIRE_BARRAGE_ATTACK = RegistryHelper.registerActivity("wildfire_barrage_attack", () -> new Activity("wildfire_barrage_attack"));
		WILDFIRE_SHOCKWAVE_ATTACK = RegistryHelper.registerActivity("wildfire_shockwave_attack", () -> new Activity("wildfire_shockwave"));
	}

	public static void init() {
	}

	private FriendsAndFoesActivities() {
	}
}
