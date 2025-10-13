package com.faboslav.friendsandfoes.common.world.structures;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStructureTypes;

import java.util.List;
import java.util.Optional;

import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.DimensionPadding;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.pools.alias.PoolAliasBinding;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.LiquidSettings;

public final class IceologerCabinStructure extends JigsawStructure
{
	public IceologerCabinStructure(
		StructureSettings settings,
		Holder<StructureTemplatePool> startPool,
		int maxDepth,
		HeightProvider startHeight,
		boolean useExpansionHack
	) {
		super(settings, startPool, maxDepth, startHeight, useExpansionHack);
	}

	@Override
	public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
		if (!FriendsAndFoes.getConfig().generateIceologerCabinStructure) {
			return Optional.empty();
		}

		return super.findGenerationPoint(context);
	}

	@Override
	public StructureType<?> type() {
		return FriendsAndFoesStructureTypes.ICEOLOGER_CABIN_STRUCTURE.get();
	}
}