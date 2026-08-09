package com.faboslav.friendsandfoes.fabric.mixin;

import com.faboslav.friendsandfoes.common.events.entity.EntitySpawnEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.SpawnGroupData;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import net.minecraft.world.entity.MobSpawnType;

@Mixin(StructureTemplate.class)
public class StructureTemplateEntitySpawnMixin
{
	@WrapOperation(

		method = "method_17917",

		at = @At(
			value = "INVOKE",

			target = "Lnet/minecraft/world/entity/Mob;finalizeSpawn(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/world/DifficultyInstance;Lnet/minecraft/world/entity/MobSpawnType;Lnet/minecraft/world/entity/SpawnGroupData;)Lnet/minecraft/world/entity/SpawnGroupData;"

		)
	)
	private static SpawnGroupData friendsandfoes$onFinalizeSpawn(
		Mob mob,
		ServerLevelAccessor level,
		DifficultyInstance difficulty,

		MobSpawnType spawnReason,

		SpawnGroupData spawnGroupData,
		Operation<SpawnGroupData> original, Rotation rotation,
		Mirror mirror,
		Vec3 pos,
		boolean finalizeEntities,
		ServerLevelAccessor capturedLevel,
		Entity entity
	) {
		if (!EntitySpawnEvent.EVENT.invoke(
			new EntitySpawnEvent(
				mob,
				level,
				mob.isBaby(),

				MobSpawnType.STRUCTURE

			)
		)) {
			return original.call(mob, level, difficulty, spawnReason, spawnGroupData);
		} else {
			return null;
		}
	}
}