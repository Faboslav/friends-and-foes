package com.faboslav.friendsandfoes.common.world.structures;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public final class CitadelStructure extends Structure
{
	public static final MapCodec<CitadelStructure> CODEC = RecordCodecBuilder.mapCodec(instance ->
		instance.group(CitadelStructure.settingsCodec(instance),
			StructureTemplatePool.CODEC.fieldOf("start_pool").forGetter(structure -> structure.startPool),
			Identifier.CODEC.optionalFieldOf("start_jigsaw_name").forGetter(structure -> structure.startJigsawName),
			Codec.intRange(0, 30).fieldOf("size").forGetter(structure -> structure.size),
			HeightProvider.CODEC.fieldOf("start_height").forGetter(structure -> structure.startHeight),
			Heightmap.Types.CODEC.optionalFieldOf("project_start_to_heightmap").forGetter(structure -> structure.projectStartToHeightmap),
			//? >= 1.21.10 {
			JigsawStructure.MaxDistance.CODEC.fieldOf("max_distance_from_center").forGetter((structure) -> structure.maxDistanceFromCenter)
			//?} else {
			/*Codec.intRange(1, 128).fieldOf("max_distance_from_center").forGetter(structure -> structure.maxDistanceFromCenter)
			*///?}
		).apply(instance, CitadelStructure::new));

	private final Holder<StructureTemplatePool> startPool;
	private final Optional<Identifier> startJigsawName;
	private final int size;
	private final HeightProvider startHeight;
	private final Optional<Heightmap.Types> projectStartToHeightmap;
	//? >= 1.21.10 {
	private final JigsawStructure.MaxDistance maxDistanceFromCenter;
	//?} else {
	/*private final int maxDistanceFromCenter;
	*///?}

	public CitadelStructure(
		Structure.StructureSettings config,
		Holder<StructureTemplatePool> startPool,
		Optional<Identifier> startJigsawName,
		int size,
		HeightProvider startHeight,
		Optional<Heightmap.Types> projectStartToHeightmap,
		//? >= 1.21.10 {
		JigsawStructure.MaxDistance maxDistanceFromCenter
		//?} else {
		/*int maxDistanceFromCenter
		 *///?}
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
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		int offsetY = this.startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
		BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), offsetY, context.chunkPos().getMinBlockZ());

		if (
			!FriendsAndFoes.getConfig().generateCitadelStructure
			|| !extraSpawningChecks(context, blockPos)
		) {
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

	private boolean extraSpawningChecks(Structure.GenerationContext context, BlockPos blockPos) {
		int checkRadius = 16;
		BlockPos.MutableBlockPos mutable = new BlockPos.MutableBlockPos();

		for (int xOffset = -checkRadius; xOffset <= checkRadius; xOffset += 8) {
			for (int zOffset = -checkRadius; zOffset <= checkRadius; zOffset += 8) {
				NoiseColumn blockView = context.chunkGenerator().getBaseColumn(xOffset + blockPos.getX(), zOffset + blockPos.getZ(), context.heightAccessor(), context.randomState());
				for (int yOffset = 0; yOffset <= 53; yOffset += 5) {
					mutable.set(blockPos).move(xOffset, yOffset, zOffset);
					BlockState state = blockView.getBlock(mutable.getY());

					if (!state.isAir() && state.getFluidState().isEmpty()) {
						return false;
					}
				}
			}
		}

		return true;
	}

	@Override
	public StructureType<?> type() {
		return FriendsAndFoesStructureTypes.CITADEL_STRUCTURE.get();
	}
}