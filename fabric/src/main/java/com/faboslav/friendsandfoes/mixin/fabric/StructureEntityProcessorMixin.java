package com.faboslav.friendsandfoes.mixin.fabric;

import com.faboslav.friendsandfoes.world.processor.StructureEntityProcessor;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtDouble;
import net.minecraft.nbt.NbtList;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.structure.processor.StructureProcessor;
import net.minecraft.util.BlockMirror;
import net.minecraft.util.BlockRotation;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Allows for processing entities in Jigsaw structures.
 * Originally from YUNG's API by.
 * YUNGNICKYOUNG(https://github.com/YUNG-GANG/YUNGs-API)
 */
@Mixin(StructureTemplate.class)
public final class StructureEntityProcessorMixin
{
	@Shadow
	@Final
	private List<StructureEntityInfo> entities;

	/**
	 * Reimplements vanilla behavior for spawning entities,
	 * but with additional behavior allowing for the use of entity processing ({@link StructureEntityProcessor})
	 */
	@Inject(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/structure/StructureTemplate.spawnEntities (Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockBox;Z)V"
		)
	)
	private void friendsandfoes_processEntities(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructurePlacementData structurePlacementData,
		Random random,
		int flags,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (FabricLoader.getInstance().isModLoaded("yungsapi") == false) {
			for (StructureEntityInfo entityInfo : friendsandfoes_processEntityInfos(
				serverWorldAccess,
				structurePiecePos,
				structurePieceBottomCenterPos,
				structurePlacementData,
				this.entities
			)) {
				BlockPos blockPos = entityInfo.blockPos;

				if (
					structurePlacementData.getBoundingBox() != null
					&& structurePlacementData.getBoundingBox().contains(blockPos) == false
				) {
					continue;
				}

				NbtCompound compoundTag = entityInfo.nbt.copy();
				Vec3d vec3d = entityInfo.pos;

				NbtList nbtList = new NbtList();
				nbtList.add(NbtDouble.of(vec3d.getX()));
				nbtList.add(NbtDouble.of(vec3d.getY()));
				nbtList.add(NbtDouble.of(vec3d.getZ()));
				compoundTag.put("Pos", nbtList);
				compoundTag.remove("UUID");

				friendsandfoes_getEntity(serverWorldAccess, compoundTag).ifPresent((entity) -> {
					float f = entity.applyMirror(structurePlacementData.getMirror());
					f += entity.getYaw() - entity.applyRotation(structurePlacementData.getRotation());
					entity.refreshPositionAndAngles(
						vec3d.getX(),
						vec3d.getY(),
						vec3d.getZ(),
						f,
						entity.getPitch()
					);
					if (structurePlacementData.shouldInitializeMobs() && entity instanceof MobEntity) {
						((MobEntity) entity).initialize(
							serverWorldAccess,
							serverWorldAccess.getLocalDifficulty(
								new BlockPos(vec3d)
							),
							SpawnReason.STRUCTURE,
							null,
							compoundTag
						);
					}

					serverWorldAccess.spawnEntityAndPassengers(entity);
				});
			}
		}
	}

	/**
	 * Cancel spawning entities.
	 * This behavior is recreated in {@link #friendsandfoes_processEntities}
	 */
	@Inject(
		method = "spawnEntities",
		at = @At(value = "HEAD"),
		cancellable = true
	)
	private void friendsandfoes_cancelPlaceEntities(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockMirror mirror,
		BlockRotation rotation,
		BlockPos pivot,
		@Nullable BlockBox area,
		boolean initializeMobs,
		CallbackInfo ci
	) {
		if (FabricLoader.getInstance().isModLoaded("yungsapi") == false) {
			ci.cancel();
		}
	}

	/**
	 * Applies placement data and {@link StructureEntityProcessor}s to entities in a structure.
	 */
	private List<StructureEntityInfo> friendsandfoes_processEntityInfos(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructurePlacementData structurePlacementData,
		List<StructureEntityInfo> rawEntityList
	) {
		List<StructureEntityInfo> processedEntities = new ArrayList<>();

		for (StructureEntityInfo rawEntityItem : rawEntityList) {
			// Calculate transformed position so processors have access to the actual global world coordinates of the entity
			Vec3d globalPos = StructureTemplate
				.transformAround(
					rawEntityItem.pos,
					structurePlacementData.getMirror(),
					structurePlacementData.getRotation(),
					structurePlacementData.getPosition()
				).add(Vec3d.of(structurePiecePos));
			BlockPos globalBlockPos = StructureTemplate
				.transformAround(
					rawEntityItem.blockPos,
					structurePlacementData.getMirror(),
					structurePlacementData.getRotation(),
					structurePlacementData.getPosition()
				).add(structurePiecePos);

			StructureEntityInfo globalEntityInfo = new StructureEntityInfo(
				globalPos,
				globalBlockPos,
				rawEntityItem.nbt
			);

			// Apply processors
			for (StructureProcessor processor : structurePlacementData.getProcessors()) {
				if (processor instanceof StructureEntityProcessor == false) {
					continue;
				}

				globalEntityInfo = ((StructureEntityProcessor) processor).processEntity(
					serverWorldAccess,
					structurePiecePos,
					structurePieceBottomCenterPos,
					rawEntityItem,
					globalEntityInfo,
					structurePlacementData
				);

				if (globalEntityInfo == null) {
					break;
				}
			}

			// null value from processor indicates the entity should not be spawned
			if (globalEntityInfo != null) {
				processedEntities.add(globalEntityInfo);
			}
		}

		return processedEntities;
	}

	private static Optional<Entity> friendsandfoes_getEntity(
		ServerWorldAccess serverWorldAccess,
		NbtCompound compoundTag
	) {
		try {
			return EntityType.getEntityFromNbt(compoundTag, serverWorldAccess.toServerWorld());
		} catch (Exception exception) {
			return Optional.empty();
		}
	}
}
