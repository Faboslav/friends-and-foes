package com.faboslav.friendsandfoes.common.entity.event;

import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.ServerLevelAccessor;

public final class OnEntitySpawn
{
	public static boolean handleOnEntitySpawn(
		EntitySpawnEvent event,
		EntityType<? extends Mob> entityTypeToReplace,
		EntityType<? extends Mob> entityTypeToSpawn,
		boolean spawnCondition
	) {
		Mob entity = event.entity();

		if (event.spawnReason() == VersionedEntitySpawnReason.NATURAL
			|| event.spawnReason() == VersionedEntitySpawnReason.CHUNK_GENERATION
			|| event.spawnReason() == VersionedEntitySpawnReason.STRUCTURE
		) {
			if (entity.getType() != entityTypeToReplace) {
				return false;
			}

			if (!spawnCondition) {
				return false;
			}

			LevelAccessor world = event.worldAccess();
			Mob entityToSpawn = entityTypeToSpawn.create(entity.level()/*? >=1.21.3 {*/, event.spawnReason()/*?}*/);

			if (entityToSpawn == null) {
				return false;
			}

			entityToSpawn.moveTo(
				entity.getX(),
				entity.getY(),
				entity.getZ(),
				entityToSpawn.getRandom().nextFloat() * 360.0F,
				0.0F
			);

			entityToSpawn.copyPosition(entity);
			entityToSpawn.yBodyRotO = entity.yBodyRotO;
			entityToSpawn.yBodyRot = entity.yBodyRot;
			entityToSpawn.yHeadRotO = entity.yHeadRotO;
			entityToSpawn.yHeadRot = entity.yHeadRot;
			entityToSpawn.setBaby(entity.isBaby());
			entityToSpawn.setNoAi(entity.isNoAi());
			entityToSpawn.setInvulnerable(entity.isInvulnerable());

			if(entity.hasCustomName()) {
				entityToSpawn.setCustomName(entity.getCustomName());
				entityToSpawn.setCustomNameVisible(entity.isCustomNameVisible());
			}

			if (entity.isPersistenceRequired()) {
				entityToSpawn.setPersistenceRequired();
			}

			entityToSpawn.setCanPickUpLoot(entity.canPickUpLoot());

			for(EquipmentSlot equipmentSlot : EquipmentSlot.values()) {
				ItemStack itemStack = entity.getItemBySlot(equipmentSlot);
				if (!itemStack.isEmpty()) {
					entityToSpawn.setItemSlot(equipmentSlot, itemStack.copyAndClear());
					entityToSpawn.setDropChance(equipmentSlot, entity.getEquipmentDropChance(equipmentSlot));
				}
			}

			entityToSpawn.finalizeSpawn(
				(ServerLevelAccessor) world,
				world.getCurrentDifficultyAt(entity.blockPosition()),
				event.spawnReason(),
				null
			);

			boolean spawnResult = world.addFreshEntity(entityToSpawn);

			if(!spawnResult) {
				entity.discard();
				return false;
			}

			return true;
		}

		return false;
	}
}
