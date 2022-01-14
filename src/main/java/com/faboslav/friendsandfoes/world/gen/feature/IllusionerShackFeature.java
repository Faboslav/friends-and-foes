package com.faboslav.friendsandfoes.world.gen.feature;

import com.faboslav.friendsandfoes.config.Settings;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityType;
import net.minecraft.structure.PoolStructurePiece;
import net.minecraft.structure.PostPlacementProcessor;
import net.minecraft.structure.StructureGeneratorFactory;
import net.minecraft.structure.StructureGeneratorFactory.Context;
import net.minecraft.structure.StructurePiecesGenerator;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.collection.Pool;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.SpawnSettings.SpawnEntry;
import net.minecraft.world.gen.chunk.StructureConfig;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.StructurePoolFeatureConfig;

import java.util.Optional;

public class IllusionerShackFeature extends StructureFeature<StructurePoolFeatureConfig>
{
	public static final Pool<SpawnEntry> MONSTER_SPAWNS;
	public static final Pool<SpawnEntry> CREATURE_SPAWNS;

	public IllusionerShackFeature(Codec<StructurePoolFeatureConfig> codec) {
		super(codec, IllusionerShackFeature::createPiecesGenerator, PostPlacementProcessor.EMPTY);
	}

	private static boolean isOnFluid(StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context) {
		BlockPos spawnXZPosition = context.chunkPos().getCenterAtY(0);
		int landHeight = context.chunkGenerator().getHeightInGround(spawnXZPosition.getX(), spawnXZPosition.getZ(), Heightmap.Type.WORLD_SURFACE_WG, context.world());
		VerticalBlockSample columnOfBlocks = context.chunkGenerator().getColumnSample(spawnXZPosition.getX(), spawnXZPosition.getZ(), context.world());
		BlockState topBlock = columnOfBlocks.getState(landHeight);
		return !topBlock.getFluidState().isEmpty();
	}

	private static boolean isVillageNearby(Context<StructurePoolFeatureConfig> context) {
		StructureConfig structureConfig = context.chunkGenerator().getStructuresConfig().getForType(StructureFeature.VILLAGE);
		if (structureConfig == null) {
			return false;
		} else {
			int i = context.chunkPos().x;
			int j = context.chunkPos().z;

			for (int k = i - 10; k <= i + 10; ++k) {
				for (int l = j - 10; l <= j + 10; ++l) {
					ChunkPos chunkPos2 = StructureFeature.VILLAGE.getStartChunk(structureConfig, context.seed(), k, l);
					if (k == chunkPos2.x && l == chunkPos2.z) {
						return true;
					}
				}
			}

			return false;
		}
	}

	public static Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> createPiecesGenerator(
		StructureGeneratorFactory.Context<StructurePoolFeatureConfig> context
	) {

		if (
			isOnFluid(context)
			|| isVillageNearby(context)
		) {
			return Optional.empty();
		}

		StructurePoolFeatureConfig newConfig = new StructurePoolFeatureConfig(
			() -> context.registryManager()
				.get(Registry.STRUCTURE_POOL_KEY)
				.get(Settings.makeID("illusioner_shack/illusioner_shack")),
			10
		);

		StructureGeneratorFactory.Context<StructurePoolFeatureConfig> newContext = new StructureGeneratorFactory.Context<>(
			context.chunkGenerator(),
			context.biomeSource(),
			context.seed(),
			context.chunkPos(),
			newConfig,
			context.world(),
			context.validBiome(),
			context.structureManager(),
			context.registryManager()
		);

		BlockPos blockpos = context.chunkPos().getCenterAtY(0);

		Optional<StructurePiecesGenerator<StructurePoolFeatureConfig>> structurePiecesGenerator = StructurePoolBasedGenerator.generate(newContext, PoolStructurePiece::new, blockpos, false, true);
		if (structurePiecesGenerator.isPresent()) {
			System.out.println("shack at :" + blockpos);
		}

		return structurePiecesGenerator;
	}

	static {
		MONSTER_SPAWNS = Pool.of(new SpawnEntry(EntityType.ILLUSIONER, 1, 1, 1));
		CREATURE_SPAWNS = Pool.of(new SpawnEntry(EntityType.WOLF, 1, 1, 1));
	}
}
