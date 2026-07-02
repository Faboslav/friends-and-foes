package com.faboslav.friendsandfoes.common.network;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.network.packet.MoobloomVariantsSyncPacket;
import com.faboslav.friendsandfoes.common.network.packet.TotemEffectPacket;
import com.teamresourceful.resourcefullib.common.network.Network;

//? if <= 1.21.8 {
/*import com.faboslav.friendsandfoes.common.network.packet.SyncCopperGolemWalkAnimationPacket;
*///?}

public final class MessageHandler
{
	public static final Network DEFAULT_CHANNEL = new Network(FriendsAndFoes.makeID("networking"), 1);

	public static void init() {
		DEFAULT_CHANNEL.register(MoobloomVariantsSyncPacket.TYPE);
		DEFAULT_CHANNEL.register(TotemEffectPacket.TYPE);

		//? if <= 1.21.8 {
		/*DEFAULT_CHANNEL.register(SyncCopperGolemWalkAnimationPacket.TYPE);
		*///?}
	}
}
