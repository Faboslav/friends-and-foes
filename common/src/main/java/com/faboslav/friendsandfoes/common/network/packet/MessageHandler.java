package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.network.Network;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class MessageHandler
{
	public static final Network DEFAULT_CHANNEL = new Network(FriendsAndFoes.makeID("networking"), 1);

	public static void init() {
		DEFAULT_CHANNEL.register(MoobloomVariantsSyncPacket.TYPE);
		DEFAULT_CHANNEL.register(TotemEffectPacket.TYPE);
	}
}
