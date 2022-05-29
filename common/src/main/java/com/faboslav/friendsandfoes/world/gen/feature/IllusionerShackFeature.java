package com.faboslav.friendsandfoes.world.gen.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureGeneratorFactory.Context;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

public final class IllusionerShackFeature extends JigsawStructure
{
	public IllusionerShackFeature(Codec<StructurePoolFeatureConfig> configCodec) {
		super(configCodec, 0, true, true, IllusionerShackFeature::canGenerate);
	}

	private static boolean canGenerate(Context<StructurePoolFeatureConfig> context) {

		return FriendsAndFoes.getConfig().generateIllusionerShackStructure != false
			   && isVillageNearby(context) != true
			   && isSuitableChunk(context) != false;
	}

	private static boolean isVillageNearby(Context<StructurePoolFeatureConfig> context) {
		int chunkPosX = context.chunkPos().x;
		int chunkPosZ = context.chunkPos().z;

		return context.chunkGenerator().method_41053(
			StructureSetKeys.VILLAGES,
			context.seed(),
			chunkPosX, chunkPosZ, 10
		);
	}

	private static boolean isSuitableChunk(Context<StructurePoolFeatureConfig> context) {
		int i = context.chunkPos().x >> 4;
		int j = context.chunkPos().z >> 4;

		ChunkRandom chunkRandom = new ChunkRandom(new AtomicSimpleRandom(0L));
		chunkRandom.setSeed((long) (i ^ j << 4) ^ context.seed());
		chunkRandom.nextInt();

		BlockPos blockpos = context.chunkPos().getCenterAtY(0);
		int topLandY = context.chunkGenerator().getHeight(blockpos.getX(), blockpos.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());

		return topLandY >= 80;
	}

	@Override
	public GenerationStep.Feature getGenerationStep() {
		return GenerationStep.Feature.SURFACE_STRUCTURES;
	}
}