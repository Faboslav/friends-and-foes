package com.faboslav.friendsandfoes.common.entity;

import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface MannequinEntityAccess
{
	void friendsandfoes$setPlayerUuid(@Nullable UUID uuid);
	void friendsandfoes$setPlayer(Player player);
	void friendsandfoes$setIsIllusion(boolean isIllusion);
	void friendsandfoes$setTicksUntilDespawn(int ticksUntilDespawn);
}
