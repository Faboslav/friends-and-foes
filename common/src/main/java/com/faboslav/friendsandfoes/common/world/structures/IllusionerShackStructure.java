package com.faboslav.friendsandfoes.common.world.structures;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

/**
 * Inspired by use in Repurposed Structures mod
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/RepurposedStructures">https://github.com/TelepathicGrunt/RepurposedStructures</a>
 */
public final class IllusionerShackStructure extends Structure
{
	public static final MapCodec<IllusionerShackStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(IllusionerShackStructure.settingsCodec(instance),
			StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
			ResourceLocation.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
			Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
			HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
			Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
			Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
		).apply(instance, IllusionerShackStructure::new));

	private final Holder<StructureTemplatePool> startPool;
	private final Optional<ResourceLocation> startJigsawName;
	private final int size;
	private final HeightProvider startHeight;
	private final Optional<Heightmap.Types> projectStartToHeightmap;
	private final int maxDistanceFromCenter;

	public IllusionerShackStructure(
		StructureSettings config,
		Holder<StructureTemplatePool> startPool,
		Optional<ResourceLocation> startJigsawName,
		int size,
		HeightProvider startHeight,
		Optional<Heightmap.Types> projectStartToHeightmap,
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
	public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
		int offsetY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
		BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), offsetY, context.chunkPos().getMinBlockZ());

		if (!FriendsAndFoes.getConfig().generateIllusionerShackStructure) {
			return Optional.empty();
		}

		return JigsawPlacement.addPieces(
			context,
			this.startPool,
			this.startJigsawName,
			this.size,
			blockPos,
			false,
			this.projectStartToHeightmap,
			this.maxDistanceFromCenter,
			PoolAliasLookup.EMPTY,
			JigsawStructure.DEFAULT_DIMENSION_PADDING,
			JigsawStructure.DEFAULT_LIQUID_SETTINGS
		);
	}

	@Override
	public StructureType<?> type() {
		return FriendsAndFoesStructureTypes.ILLUSIONER_SHACK_STRUCTURE.get();
	}
}