package com.faboslav.friendsandfoes.fabric;

import com.chocohead.mm.api.ClassTinkerers;
import com.faboslav.friendsandfoes.entity.mob.IceologerEntity;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public class EarlyRiserFabric implements Runnable
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

		String Spell = remapper.mapClassName("intermediary", "net.minecraft.class_1617$class_1618");

		ClassTinkerers
			.enumBuilder(Spell, int.class, double.class, double.class, double.class)
			.addEnum(IceologerEntity.SUMMON_ICE_CHUNK_SPELL_NAME, () -> new Object[]{6, 0.4D, 0.3D, 0.35D})
			.build();
	}
}
