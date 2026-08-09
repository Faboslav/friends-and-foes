package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;

import net.minecraft.world.level.GameRules;

public final class VersionedGameRulesProvider
{

	public static final GameRules.Key<GameRules.BooleanValue> SPAWN_MOBS = GameRules.RULE_DOMOBSPAWNING;
	public static final GameRules.Key<GameRules.BooleanValue> MOB_GRIEFING = GameRules.RULE_MOBGRIEFING;
	public static final GameRules.Key<GameRules.BooleanValue> MOB_DROPS = GameRules.RULE_DOMOBLOOT;

	public static boolean getBoolean(
		Entity entity,

		GameRules.Key<GameRules.BooleanValue> gameRule

	) {

		return ((ServerLevel) entity.level()).getGameRules().getBoolean(gameRule);

	}

	public static boolean getBoolean(
		ServerLevel serverLevel,

		GameRules.Key<GameRules.BooleanValue> gameRule

	) {

		return serverLevel.getGameRules().getBoolean(gameRule);

	}
}
