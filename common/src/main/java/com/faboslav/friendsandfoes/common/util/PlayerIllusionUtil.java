package com.faboslav.friendsandfoes.common.util;

import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.LivingEntity;

public final class PlayerIllusionUtil
{
	public static boolean tryToTeleport(ServerLevel serverLevel, LivingEntity livingEntity, int x, int y, int z) {
		y -= 8;
		//? if >=1.21.3 {
		int worldBottomY = serverLevel.getMinY();
		//?} else {
		/*int worldBottomY = serverLevel.getMinBuildHeight();
		 *///?}
		int logicalHeight = serverLevel.getLogicalHeight();
		double bottomY = Math.max(y, worldBottomY);
		double topY = Math.min(bottomY + 16, logicalHeight- 1);

		for (int i = 0; i < 16; ++i) {
			y = (int) Mth.clamp(y + 1, bottomY, topY);
			boolean teleportResult = livingEntity.randomTeleport(x, y, z, false);

			if (teleportResult) {
				return true;
			}
		}

		return false;
	}
}
