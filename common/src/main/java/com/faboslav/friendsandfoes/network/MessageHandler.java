package com.faboslav.friendsandfoes.network;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.network.base.NetworkDirection;
import com.faboslav.friendsandfoes.network.packet.MoobloomVariantsSyncPacket;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 *
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public final class MessageHandler
{
	public static final NetworkChannel DEFAULT_CHANNEL = new NetworkChannel(FriendsAndFoes.MOD_ID, "networking");

	public static void init() {
		DEFAULT_CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, MoobloomVariantsSyncPacket.ID, MoobloomVariantsSyncPacket.HANDLER, MoobloomVariantsSyncPacket.class);
	}
}
