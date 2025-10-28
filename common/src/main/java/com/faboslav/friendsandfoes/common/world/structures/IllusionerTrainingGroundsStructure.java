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

public final class IllusionerTrainingGroundsStructure extends JigsawStructure
{
	public IllusionerTrainingGroundsStructure(
		StructureSettings structureSettings,
		Holder<StructureTemplatePool> holder,
		int i,
		HeightProvider heightProvider,
		boolean bl
	) {
		super(structureSettings, holder, i, heightProvider, bl);
	}

	@Override
	public Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
		if (!FriendsAndFoes.getConfig().generateIllusionerTrainingGroundsStructure) {
			return Optional.empty();
		}

		return super.findGenerationPoint(context);
	}

	@Override
	public StructureType<?> type() {
		return FriendsAndFoesStructureTypes.ILLUSIONER_TRAINING_GROUNDS_STRUCTURE.get();
	}
}