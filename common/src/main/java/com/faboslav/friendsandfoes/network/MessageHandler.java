package com.faboslav.friendsandfoes.network;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.network.base.NetworkDirection;
import com.faboslav.friendsandfoes.network.packet.MoobloomVariantsSyncPacket;

/**
 * All network related stuff is based on The Bumblezone mod with permission of TelepathicGrunt and ThatGravyBoat
 */
public final class MessageHandler
{
	public static final NetworkChannel DEFAULT_CHANNEL = new NetworkChannel(FriendsAndFoes.MOD_ID, "networking");

	public static void init() {
		DEFAULT_CHANNEL.registerPacket(NetworkDirection.SERVER_TO_CLIENT, MoobloomVariantsSyncPacket.ID, MoobloomVariantsSyncPacket.HANDLER, MoobloomVariantsSyncPacket.class);
	}
}
