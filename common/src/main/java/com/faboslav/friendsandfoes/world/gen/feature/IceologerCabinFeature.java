package com.faboslav.friendsandfoes.world.gen.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.mojang.serialization.Codec;
import net.minecraft.structure.StructureGeneratorFactory.Context;
import net.minecraft.structure.StructureSetKeys;
import net.minecraft.world.gen.GenerationStep;
import net.minecraft.world.gen.feature.JigsawFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

public final class IceologerCabinFeature extends JigsawFeature
{
	public IceologerCabinFeature(Codec<StructurePoolFeatureConfig> configCodec) {
		super(configCodec, 0, true, true, IceologerCabinFeature::canGenerate);
	}

	private static boolean canGenerate(Context<StructurePoolFeatureConfig> context) {
		return FriendsAndFoes.getConfig().generateIceologerCabinStructure
			   && isVillageNearby(context) == false
			   && isSuitableChunk(context);
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

		return chunkRandom.nextInt(3) == 0;
	}

	@Override
	public GenerationStep.Feature getGenerationStep() {
		return GenerationStep.Feature.SURFACE_STRUCTURES;
	}
}