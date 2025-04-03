package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public final class PatrolSpawnerMixin
{
	boolean friendsandfoes_isBiomeSpecificIllagerSpawned = false;

	@ModifyVariable(
		method = "spawnPatrolMember",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	private PatrollingMonster friendsandfoes_modifyPatrolEntity(
		PatrollingMonster patrolEntity,
		ServerLevel world,
		BlockPos pos,
		RandomSource random,
		boolean captain
	) {
		Holder<Biome> biomeEntry = world.getBiome(pos);

		if (!this.friendsandfoes_isBiomeSpecificIllagerSpawned) {
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
	private void friendsandfoes_resetBiomeSpecificIllagerSpawnFlag(
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
		/*this.friendsandfoes_isBiomeSpecificIllagerSpawned = false;
		*///?} else {
		var spawnerPatrolMembersCount = ci.getReturnValue();

		if (spawnerPatrolMembersCount > 0) {
			this.friendsandfoes_isBiomeSpecificIllagerSpawned = false;
		}
		//?}
	}
}
