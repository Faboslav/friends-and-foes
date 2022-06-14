package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.init.ModEntityTypes;
import com.faboslav.friendsandfoes.init.ModTags;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.mob.PatrolEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.spawner.PatrolSpawner;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PatrolSpawner.class)
public class PatrolSpawnerMixin
{
	boolean isBiomeSpecificIllagerSpawned = false;

	@ModifyVariable(
		method = "spawnPillager",
		ordinal = 0,
		at = @At(
			value = "LOAD"
		)
	)
	private PatrolEntity modifyPatrolEntity(
		PatrolEntity patrolEntity,
		ServerWorld world,
		BlockPos pos,
		Random random,
		boolean captain
	) {
		RegistryEntry<Biome> biomeEntry = world.getBiome(pos);

		if (this.isBiomeSpecificIllagerSpawned == false) {
			if (biomeEntry.isIn(ModTags.HAS_ILLUSIONER)) {
				patrolEntity = EntityType.ILLUSIONER.create(world);
			} else if (biomeEntry.isIn(ModTags.HAS_ICEOLOGER)) {
				patrolEntity = ModEntityTypes.ICEOLOGER.get().create(world);
			}
		}

		return patrolEntity;
	}

	@Inject(
		method = "spawn",
		at = @At("RETURN"),
		cancellable = true
	)
	private void resetBiomeSpecificIllagerSpawnFlag(
		ServerWorld world,
		boolean spawnMonsters,
		boolean spawnAnimals,
		CallbackInfoReturnable<Integer> cir
	) {
		var spawnerPatrolMembersCount = cir.getReturnValue();

		if (spawnerPatrolMembersCount > 0) {
			this.isBiomeSpecificIllagerSpawned = false;
		}

		cir.setReturnValue(spawnerPatrolMembersCount);
	}
}
