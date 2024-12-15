package com.faboslav.friendsandfoes.common.entity.ai.pathing;

import net.minecraft.world.level.pathfinder.Path;

public final class CachedPathHolder
{
	public Path cachedPath = null;
	public int pathTimer = 0;

	public CachedPathHolder() {
	}
}
