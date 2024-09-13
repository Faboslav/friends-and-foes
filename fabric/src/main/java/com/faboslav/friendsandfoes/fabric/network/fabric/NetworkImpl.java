<<<<<<<< HEAD:fabric/src/main/java/com/faboslav/friendsandfoes/fabric/network/fabric/NetworkImpl.java
package com.faboslav.friendsandfoes.network.fabric;
========
package com.faboslav.friendsandfoes.common.network.base;
>>>>>>>> 1.20.4:common/src/main/java/com/faboslav/friendsandfoes/common/network/base/PacketHandler.java

import com.faboslav.friendsandfoes.network.base.Networking;
import net.minecraft.util.Identifier;

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
	public static Networking getNetwork(Identifier channel, int protocolVersion, boolean optional) {
		return new FabricNetworking(channel, protocolVersion);
	}
}
