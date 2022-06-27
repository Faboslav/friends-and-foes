package com.faboslav.friendsandfoes.quilt;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.quiltmc.loader.api.ModContainer;
import org.quiltmc.qsl.base.api.entrypoint.server.DedicatedServerModInitializer;

public final class FriendsAndFoesQuiltServer implements DedicatedServerModInitializer
{
	@Override
	@Environment(EnvType.SERVER)
	public void onInitializeServer(ModContainer mod) {
	}
}
