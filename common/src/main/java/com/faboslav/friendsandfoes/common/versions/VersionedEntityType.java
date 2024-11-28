package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import org.jetbrains.annotations.Nullable;

public class VersionedEntityType
{
	@Nullable
	public static <T extends Entity> T create(Entity source, EntityType<T> entityType, SpawnReason spawnReason) {
		/*? >=1.21.3 {*/
		return entityType.create(source.getWorld(), spawnReason);
		/*?} else {*/
		/*return entityType.create(source.getWorld());
		*//*?}*/
	}
}
