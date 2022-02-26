package com.faboslav.friendsandfoes.world.gen.feature;

import com.faboslav.friendsandfoes.config.Settings;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.*;
import net.minecraft.structure.StructureGeneratorFactory.Context;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;
import net.minecraft.world.gen.random.AtomicSimpleRandom;
import net.minecraft.world.gen.random.ChunkRandom;

import java.util.Optional;

public class IllusionerShackFeature extends StructureFeature<StructurePoolFeatureConfig>
{
	public IllusionerShackFeature(
		Codec<StructurePoolFeatureConfig> configCodec,
		StructureGeneratorFactory<StructurePoolFeatureConfig> piecesGenerator
	) {
		super(configCodec, piecesGenerator);
	}
/*
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

		if (chunkRandom.nextInt(5) != 0) {
			return false;
		}

		return true;
	}*/
}
