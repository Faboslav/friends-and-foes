package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.Entity;
import net.minecraft.world.GameRules;

public final class VersionedGameRulesProvider
{
	public static GameRules getGameRules(Entity entity) {
		GameRules gameRules;

		/*? >=1.21.3 {*/
		gameRules = ((net.minecraft.server.world.ServerWorld) entity.getWorld()).getGameRules();
		/*?} else {*/
		/*gameRules = entity.getWorld().getGameRules();
		*//*?}*/

		return gameRules;
	}
}
