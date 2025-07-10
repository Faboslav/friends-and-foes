package com.faboslav.friendsandfoes.fabric.mixin;

import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedEntitySpawnReason;
import com.faboslav.friendsandfoes.common.world.processor.StructureEntityProcessor;
import com.faboslav.friendsandfoes.common.world.processor.StructureProcessingContext;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.DoubleTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureProcessor;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Allows for processing entities in Jigsaw structures.
 * Originally from YUNG's API by.
 * YUNGNICKYOUNG (<a href="https://github.com/YUNG-GANG/">YUNGs-API</a>)
 */
@Mixin(StructureTemplate.class)
public final class StructureEntityProcessorMixin
{
	@Shadow
	@Final
	private List<StructureEntityInfo> entityInfoList;

	@Unique
	private static final ThreadLocal<StructureProcessingContext> friendsandfoes_context = new ThreadLocal<>();

	@Inject(
		method = "placeInWorld",
		at = @At(
			value = "INVOKE",
			//? if >=1.21.6 {
			target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeEntities(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;ZLnet/minecraft/util/ProblemReporter;)V"
			//?} else {
			/*target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeEntities(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Z)V"
			*///?}
		)
	)
	private void friendsandfoes_captureContext(
		ServerLevelAccessor world,
		BlockPos pos,
		BlockPos pivot,
		StructurePlaceSettings placementData,
		RandomSource random,
		int flags,
		CallbackInfoReturnable<Boolean> cir
	) {
		friendsandfoes_context.set(new StructureProcessingContext(
			world,
			placementData,
			pos,
			pivot,
			entityInfoList
		));
	}


	@Inject(
		method = "placeInWorld",
		at = @At(
			value = "INVOKE",
			shift = At.Shift.AFTER,
			//? if >=1.21.6 {
			target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeEntities(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;ZLnet/minecraft/util/ProblemReporter;)V"
			//?} else {
			/*target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeEntities(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Z)V"
			*///?}
		)
	)
	private void friendsandfoes_clearContext(
		ServerLevelAccessor serverLevelAccessor,
		BlockPos structurePiecePos,
		BlockPos structurePiecePivotPos,
		StructurePlaceSettings structurePlaceSettings,
		RandomSource randomSource,
		int i,
		CallbackInfoReturnable<Boolean> cir
	) {
		friendsandfoes_context.remove();
	}

	@Inject(
		method = "placeInWorld",
		at = @At(
			value = "INVOKE",
			//? if >=1.21.6 {
			target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeEntities(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;ZLnet/minecraft/util/ProblemReporter;)V"
			//?} else {
			/*target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;placeEntities(Lnet/minecraft/world/level/ServerLevelAccessor;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/Mirror;Lnet/minecraft/world/level/block/Rotation;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/structure/BoundingBox;Z)V"
			*///?}
		),
		cancellable = true
	)
	private void friendsandfoes_processEntities(
		ServerLevelAccessor serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructurePlaceSettings structurePlacementData,
		RandomSource random,
		int flags,
		CallbackInfoReturnable<Boolean> cir
	) {
		StructureProcessingContext ctx = friendsandfoes_context.get();

		if (ctx.structurePlacementData().getProcessors().stream().noneMatch(p -> p instanceof StructureEntityProcessor)) {
			return;
		}

		List<StructureTemplate.StructureEntityInfo> processedEntities = friendsandfoes_processEntityInfoList(ctx);

		for (StructureTemplate.StructureEntityInfo entityInfo : processedEntities) {
			BlockPos entityBlockPos = entityInfo.blockPos;
			if (ctx.structurePlacementData().getBoundingBox() == null || ctx.structurePlacementData().getBoundingBox().isInside(entityBlockPos)) {
				CompoundTag entityNbt = entityInfo.nbt.copy();
				Vec3 entityPos = entityInfo.pos;
				ListTag listTag = new ListTag();
				listTag.add(DoubleTag.valueOf(entityPos.x));
				listTag.add(DoubleTag.valueOf(entityPos.y));
				listTag.add(DoubleTag.valueOf(entityPos.z));
				entityNbt.put("Pos", listTag);
				entityNbt.remove("UUID");
				friendsandfoes_tryCreateEntity(serverWorldAccess, entityNbt).ifPresent((entity) -> {
					float f = entity.mirror(ctx.structurePlacementData().getMirror());
					f += entity.getYRot() - entity.rotate(ctx.structurePlacementData().getRotation());
					VersionedEntity.moveTo(entity, entityPos.x, entityPos.y, entityPos.z, f, entity.getXRot());
					if (ctx.structurePlacementData().shouldFinalizeEntities() && entity instanceof Mob) {
						((Mob) entity).finalizeSpawn(
							serverWorldAccess,
							serverWorldAccess.getCurrentDifficultyAt(BlockPos.containing(entityPos)),
							VersionedEntitySpawnReason.STRUCTURE,
							null
						);
					}

					serverWorldAccess.addFreshEntityWithPassengers(entity);
				});
			}
		}

		cir.cancel();
	}

	@Unique
	private List<StructureTemplate.StructureEntityInfo> friendsandfoes_processEntityInfoList(StructureProcessingContext ctx) {
		List<StructureTemplate.StructureEntityInfo> processedEntities = new ArrayList<>();

		ServerLevelAccessor serverLevelAccessor = ctx.serverWorldAccess();
		BlockPos structurePiecePos = ctx.structurePiecePos();
		BlockPos structurePiecePivotPos = ctx.structurePiecePivotPos();
		StructurePlaceSettings structurePlaceSettings = ctx.structurePlacementData();
		List<StructureTemplate.StructureEntityInfo> rawEntityInfos = ctx.rawEntityInfos();

		for (StructureTemplate.StructureEntityInfo rawEntityInfo : rawEntityInfos) {
			Vec3 globalPos = StructureTemplate
				.transform(rawEntityInfo.pos,
					structurePlaceSettings.getMirror(),
					structurePlaceSettings.getRotation(),
					structurePlaceSettings.getRotationPivot())
				.add(Vec3.atLowerCornerOf(structurePiecePos));
			BlockPos globalBlockPos = StructureTemplate
				.transform(rawEntityInfo.blockPos,
					structurePlaceSettings.getMirror(),
					structurePlaceSettings.getRotation(),
					structurePlaceSettings.getRotationPivot())
				.offset(structurePiecePos);
			StructureTemplate.StructureEntityInfo globalEntityInfo = new StructureTemplate.StructureEntityInfo(globalPos, globalBlockPos, rawEntityInfo.nbt);

			for (StructureProcessor processor : structurePlaceSettings.getProcessors()) {
				if (processor instanceof StructureEntityProcessor) {
					globalEntityInfo = ((StructureEntityProcessor) processor).processEntity(serverLevelAccessor, structurePiecePos, structurePiecePivotPos, rawEntityInfo, globalEntityInfo, structurePlaceSettings);
					if (globalEntityInfo == null) break;
				}
			}

			if (globalEntityInfo != null) {
				processedEntities.add(globalEntityInfo);
			}
		}

		return processedEntities;
	}

	@Unique
	private static Optional<Entity> friendsandfoes_tryCreateEntity(
		ServerLevelAccessor serverLevelAccessor,
		CompoundTag compoundTag
	) {
		try {
			//? if >=1.21.6 {
			return Optional.empty();
			//?} else if >=1.21.3 {
			/*return EntityType.create(compoundTag, serverLevelAccessor.getLevel(), VersionedEntitySpawnReason.STRUCTURE);
			*///?} else {
			/*return EntityType.create(compoundTag, serverLevelAccessor.getLevel());
			 *///?}
		} catch (Exception exception) {
			return Optional.empty();
		}
	}
}
