package com.faboslav.friendsandfoes.network.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * All network related stuff is based on The Bumblezone mod with permission of TelepathicGrunt and ThatGravyBoat
 */
@FunctionalInterface
public interface PacketContext
{

	void apply(PlayerEntity player, World level);
}
