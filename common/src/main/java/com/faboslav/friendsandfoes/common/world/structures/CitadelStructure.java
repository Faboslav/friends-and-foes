package com.faboslav.friendsandfoes.common.world.structures;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureTypes;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import java.util.List;
import java.util.Optional;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasLookup;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

public final class CitadelStructure extends JigsawStructure
{
	public CitadelStructure(
		StructureSettings settings,
		Holder<StructureTemplatePool> startPool,
		int maxDepth,
		HeightProvider startHeight,
		boolean useExpansionHack
	) {
		super(settings, startPool, maxDepth, startHeight, useExpansionHack);
	}

	@Override
	public Optional<Structure.GenerationStub> findGenerationPoint(Structure.GenerationContext context) {
		if (!FriendsAndFoes.getConfig().generateCitadelStructure) {
			return Optional.empty();
		}

		return super.findGenerationPoint(context);
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