package com.faboslav.friendsandfoes.fabric.mixin;

import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
*///?}

@Mixin(PatrolSpawner.class)
public final class PatrolSpawnerMixin
{
	@Inject(
		method = "spawnPatrolMember",
		at = @At(
			value = "INVOKE",
			/*? >=1.21.3 {*/
			target = "Lnet/minecraft/world/entity/monster/PatrollingMonster;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/EntitySpawnReason;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;"
			/*?} else {*/
			/*target = "Lnet/minecraft/world/entity/monster/PatrollingMonster;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;"
			*//*?}*/
		),
		cancellable = true
	)
	public void friendsandfoes$onSpawnEntity(
		ServerLevel serverLevel,
		BlockPos blockPos,
		RandomSource randomSource,
		boolean bl,
		CallbackInfoReturnable<Boolean> cir,
		@Local(ordinal = 0) PatrollingMonster patrollingMonster
	) {
		if (EntitySpawnEvent.EVENT.invoke(
			new EntitySpawnEvent(
				patrollingMonster,
				serverLevel,
				patrollingMonster.isBaby(),
				/*? >=1.21.3 {*/
				EntitySpawnReason.STRUCTURE
				/*?} else {*/
				/*MobSpawnType.STRUCTURE
				 *//*?}*/
			)
		)) {
			cir.setReturnValue(false);
		}
	}
}
