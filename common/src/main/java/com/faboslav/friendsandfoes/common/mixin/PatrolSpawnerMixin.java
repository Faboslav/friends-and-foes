package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.monster.PatrollingMonster;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

//? >=1.21.5 {
/*import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
*///?}

//? >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
*///?}

@Mixin(PatrolSpawner.class)
public final class PatrolSpawnerMixin
{
	boolean friendsandfoes$isBiomeSpecificIllagerSpawned = false;

	@ModifyVariable(
		method = "spawnPatrolMember",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	private PatrollingMonster friendsandfoes$modifyPatrolEntity(
		PatrollingMonster patrolEntity,
		ServerLevel world,
		BlockPos pos,
		RandomSource random,
		boolean captain
	) {
		Holder<Biome> biomeEntry = world.getBiome(pos);

		if (!this.friendsandfoes$isBiomeSpecificIllagerSpawned) {
			if (biomeEntry.is(FriendsAndFoesTags.HAS_ILLUSIONER)) {
				patrolEntity = EntityType.ILLUSIONER.create(world/*? >=1.21.3 {*/, VersionedEntitySpawnReason.PATROL/*?}*/);
			} else if (biomeEntry.is(FriendsAndFoesTags.HAS_ICEOLOGER)) {
				patrolEntity = FriendsAndFoesEntityTypes.ICEOLOGER.get().create(world/*? >=1.21.3 {*/, VersionedEntitySpawnReason.PATROL/*?}*/);
			}
		}

		return patrolEntity;
	}

	// TODO check if this works
	@Inject(
		method = "tick",
		at = @At("RETURN")
	)
	private void friendsandfoes$resetBiomeSpecificIllagerSpawnFlag(
		ServerLevel world,
		boolean spawnMonsters,
		boolean spawnAnimals,
		//? >=1.21.5 {
		/*CallbackInfo ci
		*///?} else {
		CallbackInfoReturnable<Integer> ci
		//?}
	) {
		//? >=1.21.5 {
		/*this.friendsandfoes$isBiomeSpecificIllagerSpawned = false;
		*///?} else {
		var spawnerPatrolMembersCount = ci.getReturnValue();

		if (spawnerPatrolMembersCount > 0) {
			this.friendsandfoes$isBiomeSpecificIllagerSpawned = false;
		}
		//?}
	}

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
