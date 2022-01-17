package com.faboslav.friendsandfoes;

import com.chocohead.mm.api.ClassTinkerers;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiser implements Runnable
{
	@Override
	public void run() {
		MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

		String Raid = remapper.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
		String EntityType = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_1299") + ';';

		ClassTinkerers
			.enumBuilder(Raid, EntityType, int[].class)
			.addEnum("ILLUSIONER", () -> new Object[]{net.minecraft.entity.EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 0, 1, 1}})
			.build();
	}
}
