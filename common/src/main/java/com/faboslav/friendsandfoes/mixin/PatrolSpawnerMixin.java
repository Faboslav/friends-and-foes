package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.FriendsAndFoesEntityTypes;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public final class PatrolSpawnerMixin
{
	boolean isBiomeSpecificIllagerSpawned = false;

	@ModifyVariable(
		method = "spawnPillager",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	private PatrolEntity friendsandfoes_modifyPatrolEntity(
		PatrolEntity patrolEntity,
		ServerWorld world,
		BlockPos pos,
		Random random,
		boolean captain
	) {
		RegistryEntry<Biome> biomeEntry = world.getBiome(pos);

		if (this.isBiomeSpecificIllagerSpawned == false) {
			if (biomeEntry.isIn(FriendsAndFoesTags.HAS_ILLUSIONER)) {
				patrolEntity = EntityType.ILLUSIONER.create(world);
			} else if (biomeEntry.isIn(FriendsAndFoesTags.HAS_ICEOLOGER)) {
				patrolEntity = FriendsAndFoesEntityTypes.ICEOLOGER.get().create(world);
			}
		}

		return patrolEntity;
	}

	@Inject(
		method = "spawn",
		at = @At("RETURN")
	)
	private void friendsandfoes_resetBiomeSpecificIllagerSpawnFlag(
		ServerWorld world,
		boolean spawnMonsters,
		boolean spawnAnimals,
		CallbackInfoReturnable<Integer> callbackInfo
	) {
		var spawnerPatrolMembersCount = callbackInfo.getReturnValue();

		if (spawnerPatrolMembersCount > 0) {
			this.isBiomeSpecificIllagerSpawned = false;
		}
	}
}
