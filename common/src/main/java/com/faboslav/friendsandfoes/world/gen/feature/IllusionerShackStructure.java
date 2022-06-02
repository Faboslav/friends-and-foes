package com.faboslav.friendsandfoes.world.gen.feature;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.ChunkRandom;
import net.minecraft.util.math.random.LocalRandom;
import net.minecraft.util.registry.RegistryEntry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.JigsawStructure;
import net.minecraft.world.gen.structure.Structure;

import java.util.Optional;

public final class IllusionerShackStructure extends JigsawStructure
{
	public IllusionerShackStructure(
		Config config,
		RegistryEntry<StructurePool> startPool,
		Optional<Identifier> startJigsawName,
		int size,
		HeightProvider startHeight,
		boolean useExpansionHack,
		Optional<Heightmap.Type> projectStartToHeightmap,
		int maxDistanceFromCenter
	) {
		super(config, startPool, startJigsawName, size, startHeight, useExpansionHack, projectStartToHeightmap, maxDistanceFromCenter);
	}

	@Override
	public Optional<StructurePosition> getStructurePosition(Structure.Context context) {
		if (
			FriendsAndFoes.getConfig().enableIllusioner == false
			|| FriendsAndFoes.getConfig().generateIllusionerShackStructure == false
			|| isSuitableChunk(context) == false
		) {
			return Optional.empty();
		}

		return super.getStructurePosition(context);
	}

	private static boolean isSuitableChunk(Structure.Context context) {
		int i = context.chunkPos().x >> 4;
		int j = context.chunkPos().z >> 4;

		ChunkRandom chunkRandom = new ChunkRandom(new LocalRandom(0L));
		chunkRandom.setSeed((long) (i ^ j << 4) ^ context.seed());
		chunkRandom.nextInt();

		BlockPos blockpos = context.chunkPos().getCenterAtY(0);
		int topLandY = context.chunkGenerator().getHeight(
			blockpos.getX(),
			blockpos.getZ(),
			Heightmap.Type.WORLD_SURFACE_WG,
			context.world(),
			context.noiseConfig()
		);

		return topLandY >= 80;
	}
}