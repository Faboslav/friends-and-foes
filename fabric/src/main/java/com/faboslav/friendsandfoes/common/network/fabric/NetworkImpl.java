package com.faboslav.friendsandfoes.common.network.fabric;

import com.faboslav.friendsandfoes.common.network.base.Networking;
import com.faboslav.friendsandfoes.fabric.network.FabricNetworking;
import net.minecraft.resources.ResourceLocation;

/**
 * Network related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public class NetworkImpl
{
	public static Networking getNetwork(ResourceLocation channel, int protocolVersion, boolean optional) {
		return new FabricNetworking(channel, protocolVersion);
	}
}
