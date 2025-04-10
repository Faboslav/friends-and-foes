package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.NaturalSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

/*? >=1.21.3 {*/
import net.minecraft.world.entity.EntitySpawnReason;
/*?} else {*/
/*import net.minecraft.world.entity.MobSpawnType;
*//*?}*/

@Mixin(NaturalSpawner.class)
public final class SpawnHelperMixin
{
	@WrapOperation(
		method = "spawnCategoryForPosition(Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/chunk/ChunkAccess;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/NaturalSpawner$SpawnPredicate;Lnet/minecraft/world/level/NaturalSpawner$AfterSpawnCallback;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/NaturalSpawner;isValidPositionForMob(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/entity/Mob;D)Z"
		)
	)
	private static boolean friendsandfoes$onEntitySpawn(
		ServerLevel serverWorld,
		Mob mob,
		double d,
		Operation<Boolean> operation
	) {
		if (EntitySpawnEvent.EVENT.invoke(new EntitySpawnEvent(mob, serverWorld, mob.isBaby(), VersionedEntitySpawnReason.NATURAL))) {
			return false;
		}

		return operation.call(serverWorld, mob, d);
	}

	@WrapOperation(
		method = "spawnMobsForChunkGeneration",
		at = @At(
			value = "INVOKE",
			/*? >=1.21.3 {*/
			target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/EntitySpawnReason;)Z"
			/*?} else {*/
			/*target = "Lnet/minecraft/world/entity/Mob;checkSpawnRules(Lnet/minecraft/world/level/LevelAccessor;Lnet/minecraft/world/entity/MobSpawnType;)Z"
			*//*?}*/
		)
	)
	private static boolean friendsandfoes$onCheckEntitySpawn(
		Mob instance,
		LevelAccessor worldAccess,
		/*? >=1.21.3 {*/
		EntitySpawnReason spawnReason,
		/*?} else {*/
		/*MobSpawnType spawnReason,
		 *//*?}*/
		Operation<Boolean> operation
	) {
		if (EntitySpawnEvent.EVENT.invoke(new EntitySpawnEvent(instance, worldAccess, instance.isBaby(), spawnReason))) {
			return false;
		}

		return operation.call(instance, worldAccess, spawnReason);
	}
}
