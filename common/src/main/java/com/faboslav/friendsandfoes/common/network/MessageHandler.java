package com.faboslav.friendsandfoes.common.network;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.network.packet.EntityAnimationsSyncPacket;
import com.faboslav.friendsandfoes.common.network.packet.MoobloomVariantsSyncPacket;
import com.faboslav.friendsandfoes.common.network.packet.TotemEffectPacket;
import com.teamresourceful.resourcefullib.common.network.Network;

public final class MessageHandler
{
	public static final Network DEFAULT_CHANNEL = new Network(FriendsAndFoes.makeID("networking"), 1);

	public static void init() {
		DEFAULT_CHANNEL.register(MoobloomVariantsSyncPacket.TYPE);
		DEFAULT_CHANNEL.register(EntityAnimationsSyncPacket.TYPE);
		DEFAULT_CHANNEL.register(TotemEffectPacket.TYPE);
	}
}
