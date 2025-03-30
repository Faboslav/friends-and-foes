package com.faboslav.friendsandfoes.common.entity.event;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import net.minecraft.world.entity.EntityType;

public final class IllusionerOnEntitySpawn
{
	public static boolean handleEntitySpawn(EntitySpawnEvent event) {
		return OnEntitySpawn.handleOnEntitySpawn(
			event,
			EntityType.ILLUSIONER,
			FriendsAndFoesEntityTypes.ILLUSIONER.get(),
			FriendsAndFoes.getConfig().replaceVanillaIllusioner
		);
	}
}
