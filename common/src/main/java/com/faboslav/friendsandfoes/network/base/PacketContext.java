package com.faboslav.friendsandfoes.network.base;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 *
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
@FunctionalInterface
public interface PacketContext
{

	void apply(PlayerEntity player, World level);
}
