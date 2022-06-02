package com.faboslav.friendsandfoes.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.ModEntityTypes;
import com.faboslav.friendsandfoes.util.ExpandedEnumValues;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public final class EarlyRiserFabric implements Runnable
{
	@Override
	public void run() {
		MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

		String Raid = remapper.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
		String EntityType = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_1299") + ';';

		ClassTinkerers
			.enumBuilder(Raid, EntityType, int[].class)
			.addEnum(ExpandedEnumValues.ILLUSIONER, () -> new Object[]{net.minecraft.entity.EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 0, 1, 1}})
			.addEnum(ExpandedEnumValues.ICEOLOGER, () -> new Object[]{ModEntityTypes.ICEOLOGER.get(), new int[]{0, 0, 0, 0, 1, 1, 0, 1}})
			.build();

		String SpawnGroup = remapper.mapClassName("intermediary", "net.minecraft.class_1311");

		ClassTinkerers
			.enumBuilder(SpawnGroup, String.class, int.class, boolean.class, boolean.class, int.class)
			.addEnum(ExpandedEnumValues.GLARES, () -> new Object[]{FriendsAndFoes.makeStringID("glares"), 5, true, false, 128})
			.build();
	}
}
