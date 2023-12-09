package com.faboslav.friendsandfoes.events.lifecycle;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.server.network.ServerPlayerEntity;

public record DatapackSyncEvent(ServerPlayerEntity player)
{

	public static final EventHandler<DatapackSyncEvent> EVENT = new EventHandler<>();
}
