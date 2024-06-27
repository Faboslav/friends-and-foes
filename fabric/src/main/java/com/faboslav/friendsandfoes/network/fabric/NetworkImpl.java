package com.faboslav.friendsandfoes.network.fabric;

import com.faboslav.friendsandfoes.network.base.Networking;
import net.minecraft.util.Identifier;

public class NetworkImpl {
    public static Networking getNetwork(Identifier channel, int protocolVersion, boolean optional) {
        return new FabricNetworking(channel, protocolVersion);
    }
}
