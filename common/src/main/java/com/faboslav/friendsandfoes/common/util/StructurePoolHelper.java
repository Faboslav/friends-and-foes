package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.mixin.StructurePoolAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;

import java.util.ArrayList;
import java.util.List;

public final class StructurePoolHelper
{
	public static void addLegacyElementToPool(
		Registry<StructureTemplatePool> templatePoolRegistry,
		ResourceLocation poolRL,
		String name,
		int weight
	) {
		StructureTemplatePool pool;
		//? >=1.21.3 {
		pool = templatePoolRegistry.getValue(poolRL);
		//?} else {
		/*pool = templatePoolRegistry.get(poolRL);
		*///?}

		if (pool == null) {
			return;
		}

		SinglePoolElement piece = SinglePoolElement.legacy(FriendsAndFoes.makeStringID(name)).apply(StructureTemplatePool.Projection.RIGID);

		for (int i = 0; i < weight; i++) {
			((StructurePoolAccessor) pool).getElements().add(piece);
		}

		List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(((StructurePoolAccessor) pool).getElementCounts());
		listOfPieceEntries.add(new Pair<>(piece, weight));
		((StructurePoolAccessor) pool).setElementCounts(listOfPieceEntries);
	}

	public static void addSingleElementToPool(
		Registry<StructureTemplatePool> templatePoolRegistry,
		ResourceLocation poolRL,
		String name,
		int weight
	) {
		StructureTemplatePool pool;
		//? >=1.21.3 {
		pool = templatePoolRegistry.getValue(poolRL);
		//?} else {
		/*pool = templatePoolRegistry.get(poolRL);
		*///?}

		if (pool == null) {
			return;
		}

		SinglePoolElement piece = SinglePoolElement.single(FriendsAndFoes.makeStringID(name)).apply(StructureTemplatePool.Projection.RIGID);

		for (int i = 0; i < weight; i++) {
			((StructurePoolAccessor) pool).getElements().add(piece);
		}

		List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(((StructurePoolAccessor) pool).getElementCounts());
		listOfPieceEntries.add(new Pair<>(piece, weight));
		((StructurePoolAccessor) pool).setElementCounts(listOfPieceEntries);
	}
}