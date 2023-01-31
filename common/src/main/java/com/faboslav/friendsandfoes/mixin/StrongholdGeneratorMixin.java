package com.faboslav.friendsandfoes.mixin;

import com.faboslav.friendsandfoes.entity.WildfireEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.structure.StrongholdGenerator;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.StructureWorldAccess;
import net.minecraft.world.gen.StructureAccessor;
import net.minecraft.world.gen.chunk.ChunkGenerator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(StrongholdGenerator.Library.class)
public class StrongholdGeneratorMixin
{
	@Inject(
		at = @At("TAIL"),
		method = "generate"
	)
	private void friendsandfoes_isCloseEnoughForDanger(
		StructureWorldAccess world,
		StructureAccessor structureAccessor,
		ChunkGenerator chunkGenerator,
		Random random,
		BlockBox chunkBox,
		ChunkPos chunkPos,
		BlockPos pivot,
		CallbackInfo ci
	) {

	}
}
