package com.faboslav.friendsandfoes.world.structures;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolBasedGenerator;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Heightmap;
import net.minecraft.world.gen.HeightContext;
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
public class IllusionerShackStructure extends Structure
{
	public static final Codec<IllusionerShackStructure> CODEC = RecordCodecBuilder.<IllusionerShackStructure>mapCodec(instance ->
		instance.group(IllusionerShackStructure.configCodecBuilder(instance),
			StructurePool.REGISTRY_CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
			Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
			Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
			HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
			Heightmap.Type.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
			Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
		).apply(instance, IllusionerShackStructure::new)).codec();

	private final RegistryEntry<StructurePool> startPool;
	private final Optional<Identifier> startJigsawName;
	private final int size;
	private final HeightProvider startHeight;
	private final Optional<Heightmap.Type> projectStartToHeightmap;
	private final int maxDistanceFromCenter;

	public IllusionerShackStructure(
		Config config,
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
	public Optional<StructurePosition> getStructurePosition(Context context) {
		int offsetY = this.startHeight.get(context.random(), new HeightContext(context.chunkGenerator(), context.world()));
		BlockPos blockPos = new BlockPos(context.chunkPos().getStartX(), offsetY, context.chunkPos().getStartZ());

		if (FriendsAndFoes.getConfig().generateIllusionerShackStructure == false) {
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

	@Override
	public StructureType<?> getType() {
		return FriendsAndFoesStructureTypes.ILLUSIONER_SHACK_STRUCTURE;
	}
}