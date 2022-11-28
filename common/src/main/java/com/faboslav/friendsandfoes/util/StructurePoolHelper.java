package com.faboslav.friendsandfoes.util;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.mixin.StructurePoolAccessor;
import com.mojang.datafixers.util.Pair;
import net.minecraft.registry.Registry;
import net.minecraft.structure.pool.SinglePoolElement;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public class StructurePoolHelper
{
	public static void addElementToPool(
		Registry<StructurePool> templatePoolRegistry,
		Identifier poolRL,
		String name,
		int weight
	) {
		StructurePool pool = templatePoolRegistry.get(poolRL);
		if (pool == null) {
			return;
		}

		SinglePoolElement piece = SinglePoolElement.ofLegacySingle(FriendsAndFoes.makeStringID(name)).apply(StructurePool.Projection.RIGID);

		for (int i = 0; i < weight; i++) {
			((StructurePoolAccessor) pool).getElements().add(piece);
		}

		List<Pair<StructurePoolElement, Integer>> listOfPieceEntries = new ArrayList<>(((StructurePoolAccessor) pool).getElementCounts());
		listOfPieceEntries.add(new Pair<>(piece, weight));
		((StructurePoolAccessor) pool).setElementCounts(listOfPieceEntries);
	}
}