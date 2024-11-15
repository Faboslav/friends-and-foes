package com.faboslav.friendsandfoes.fabric.mixin;

import com.faboslav.friendsandfoes.common.world.processor.StructureEntityProcessor;
import com.faboslav.friendsandfoes.common.world.processor.StructureProcessingContext;
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
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
	private List<StructureEntityInfo> entities;

	@Unique
	private static final ThreadLocal<StructureProcessingContext> friendsandfoes_context = new ThreadLocal<>();

	@Inject(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/structure/StructureTemplate;spawnEntities(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockBox;Z)V"
		)
	)
	private void friendsandfoes_captureContext(
		ServerWorldAccess world,
		BlockPos pos,
		BlockPos pivot,
		StructurePlacementData placementData,
		Random random,
		int flags,
		CallbackInfoReturnable<Boolean> cir
	) {
		friendsandfoes_context.set(new StructureProcessingContext(
			world,
			placementData,
			pos,
			pivot,
			entities
		));
	}


	@Inject(
		method = "place",
		at = @At(
			value = "INVOKE",
			shift = At.Shift.AFTER,
			target = "Lnet/minecraft/structure/StructureTemplate;spawnEntities(Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockBox;Z)V"))
	private void friendsandfoes_clearContext(
		ServerWorldAccess serverLevelAccessor, BlockPos structurePiecePos, BlockPos structurePiecePivotPos,
		StructurePlacementData structurePlaceSettings, Random randomSource, int i, CallbackInfoReturnable<Boolean> cir
	) {
		friendsandfoes_context.remove();
	}

	@Inject(
		method = "place",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/structure/StructureTemplate.spawnEntities (Lnet/minecraft/world/ServerWorldAccess;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/BlockMirror;Lnet/minecraft/util/BlockRotation;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/util/math/BlockBox;Z)V"
		),
		cancellable = true
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
		StructureProcessingContext ctx = friendsandfoes_context.get();

		if (ctx.structurePlacementData().getProcessors().stream().noneMatch(p -> p instanceof StructureEntityProcessor)) {
			return;
		}

		List<StructureTemplate.StructureEntityInfo> processedEntities = friendsandfoes_processEntityInfoList(ctx);

		for (StructureTemplate.StructureEntityInfo entityInfo : processedEntities) {
			BlockPos entityBlockPos = entityInfo.blockPos;
			if (ctx.structurePlacementData().getBoundingBox() == null || ctx.structurePlacementData().getBoundingBox().contains(entityBlockPos)) {
				NbtCompound entityNbt = entityInfo.nbt.copy();
				Vec3d entityPos = entityInfo.pos;
				NbtList listTag = new NbtList();
				listTag.add(NbtDouble.of(entityPos.x));
				listTag.add(NbtDouble.of(entityPos.y));
				listTag.add(NbtDouble.of(entityPos.z));
				entityNbt.put("Pos", listTag);
				entityNbt.remove("UUID");
				friendsandfoes_tryCreateEntity(serverWorldAccess, entityNbt).ifPresent((entity) -> {
					float f = entity.applyMirror(ctx.structurePlacementData().getMirror());
					f += entity.getYaw() - entity.applyRotation(ctx.structurePlacementData().getRotation());
					entity.refreshPositionAndAngles(entityPos.x, entityPos.y, entityPos.z, f, entity.getPitch());
					if (ctx.structurePlacementData().shouldInitializeMobs() && entity instanceof MobEntity) {
						((MobEntity) entity).initialize(
							serverWorldAccess,
							serverWorldAccess.getLocalDifficulty(new BlockPos(entityPos)),
							SpawnReason.STRUCTURE,
							null,
							entityNbt
						);
					}

					serverWorldAccess.spawnEntityAndPassengers(entity);
				});
			}
		}

		cir.cancel();
	}

	@Unique
	private List<StructureTemplate.StructureEntityInfo> friendsandfoes_processEntityInfoList(StructureProcessingContext ctx) {
		List<StructureTemplate.StructureEntityInfo> processedEntities = new ArrayList<>();

		ServerWorldAccess serverLevelAccessor = ctx.serverWorldAccess();
		BlockPos structurePiecePos = ctx.structurePiecePos();
		BlockPos structurePiecePivotPos = ctx.structurePiecePivotPos();
		StructurePlacementData structurePlaceSettings = ctx.structurePlacementData();
		List<StructureTemplate.StructureEntityInfo> rawEntityInfos = ctx.rawEntityInfos();

		for (StructureTemplate.StructureEntityInfo rawEntityInfo : rawEntityInfos) {
			Vec3d globalPos = StructureTemplate
				.transformAround(rawEntityInfo.pos,
					structurePlaceSettings.getMirror(),
					structurePlaceSettings.getRotation(),
					structurePlaceSettings.getPosition())
				.add(Vec3d.of(structurePiecePos));
			BlockPos globalBlockPos = StructureTemplate
				.transformAround(rawEntityInfo.blockPos,
					structurePlaceSettings.getMirror(),
					structurePlaceSettings.getRotation(),
					structurePlaceSettings.getPosition())
				.add(structurePiecePos);
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
		ServerWorldAccess serverLevelAccessor,
		NbtCompound compoundTag
	) {
		try {
			return EntityType.getEntityFromNbt(compoundTag, serverLevelAccessor.toServerWorld());
		} catch (Exception exception) {
			return Optional.empty();
		}
	}
}
