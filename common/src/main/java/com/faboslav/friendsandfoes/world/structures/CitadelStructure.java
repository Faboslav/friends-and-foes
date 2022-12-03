package com.faboslav.friendsandfoes.world.structures;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.block.BlockState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.HeightContext;
import net.minecraft.world.gen.chunk.VerticalBlockSample;
import net.minecraft.world.gen.heightprovider.HeightProvider;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.StructureType;

import java.util.Optional;

/**
 * Inspired by use in Repurposed Structures mod
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/RepurposedStructures">https://github.com/TelepathicGrunt/RepurposedStructures</a>
 */
public class CitadelStructure extends Structure
{
	public static final Codec<CitadelStructure> CODEC = RecordCodecBuilder.<CitadelStructure>mapCodec(instance ->
		instance.group(CitadelStructure.configCodecBuilder(instance),
			StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
			Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
			Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
			HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
			Heightmap.Type.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
			Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
		).apply(instance, CitadelStructure::new)).codec();

	private final RegistryEntry<StructurePool> startPool;
	private final Optional<Identifier> startJigsawName;
	private final int size;
	private final HeightProvider startHeight;
	private final Optional<Heightmap.Type> projectStartToHeightmap;
	private final int maxDistanceFromCenter;

	public CitadelStructure(
		Structure.Config config,
		RegistryEntry<StructurePool> startPool,
		Optional<Identifier> startJigsawName,
		int size,
		HeightProvider startHeight,
		Optional<Heightmap.Type> projectStartToHeightmap,
		int maxDistanceFromCenter
	) {
		super(config);
		this.startPool = startPool;
		this.startJigsawName = startJigsawName;
		this.size = size;
		this.startHeight = startHeight;
		this.projectStartToHeightmap = projectStartToHeightmap;
		this.maxDistanceFromCenter = maxDistanceFromCenter;
	}

	@Override
	public Optional<Structure.StructurePosition> getStructurePosition(Structure.Context context) {
		int offsetY = this.startHeight.get(context.random(), new HeightContext(context.chunkGenerator(), context.world()));
		BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), offsetY, context.chunkPos().getStartZ());

		if (
			FriendsAndFoes.getConfig().generateCitadelStructure == false
			|| extraSpawningChecks(context, blockPos) == false
		) {
			return Optional.empty();
		}

		return StructurePoolBasedGenerator.generate(
			context,
			this.startPool,
			this.startJigsawName,
			this.size,
			blockPos,
			false,
			this.projectStartToHeightmap,
			this.maxDistanceFromCenter
		);
	}

	protected boolean extraSpawningChecks(Structure.Context context, BlockPos blockPos) {
		int checkRadius = 16;
		BlockPos.Mutable mutable = new BlockPos.Mutable();

		for (int xOffset = -checkRadius; xOffset <= checkRadius; xOffset += 8) {
			for (int zOffset = -checkRadius; zOffset <= checkRadius; zOffset += 8) {
				VerticalBlockSample blockView = context.chunkGenerator().getColumnSample(xOffset + blockPos.getX(), zOffset + blockPos.getZ(), context.world(), context.noiseConfig());
				for (int yOffset = 0; yOffset <= 53; yOffset += 5) {
					mutable.set(blockPos).move(xOffset, yOffset, zOffset);
					BlockState state = blockView.getState(mutable.getY());

					if (state.isAir() == false && state.getFluidState().isEmpty()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public StructureType<?> getType() {
		return FriendsAndFoesStructureTypes.CITADEL_STRUCTURE;
	}
}