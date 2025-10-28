package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.GameRules;

public final class VersionedGameRulesProvider
{
	public static GameRules getGameRules(Entity entity) {
		GameRules gameRules;

		/*? if >=1.21.3 {*/
		gameRules = ((ServerLevel)entity.level()).getGameRules();
		/*?} else {*/
		/*gameRules = entity.level().getGameRules();
		 *//*?}*/

		return gameRules;
	}
}
