package com.faboslav.friendsandfoes.quilt;

import com.chocohead.mm.api.ClassTinkerers;
import com.chocohead.mm.api.EnumAdder;
import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.ModEntity;
import com.faboslav.friendsandfoes.util.ExpandedEnumValues;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.MappingResolver;

public final class EarlyRiserQuilt implements Runnable
{
	@Override
	public void run() {
		MappingResolver remapper = FabricLoader.getInstance().getMappingResolver();

		String Raid = remapper.mapClassName("intermediary", "net.minecraft.class_3765$class_3766");
		String EntityType = 'L' + remapper.mapClassName("intermediary", "net.minecraft.class_1299") + ';';

		EnumAdder builder = ClassTinkerers
			.enumBuilder(Raid, EntityType, int[].class);

		if(FriendsAndFoes.getConfig().enableIllusionerInRaids) {
			builder.addEnum(ExpandedEnumValues.ILLUSIONER, () -> new Object[]{net.minecraft.entity.EntityType.ILLUSIONER, new int[]{0, 0, 0, 0, 1, 0, 1, 1}});
		}

		if(FriendsAndFoes.getConfig().enableIceologerInRaids) {
			builder.addEnum(ExpandedEnumValues.ICEOLOGER, () -> new Object[]{ModEntity.ICEOLOGER.get(), new int[]{0, 0, 0, 0, 1, 1, 0, 1}});
		}

		builder.build();

		String SpawnGroup = remapper.mapClassName("intermediary", "net.minecraft.class_1311");

		ClassTinkerers
			.enumBuilder(SpawnGroup, String.class, int.class, boolean.class, boolean.class, int.class)
			.addEnum(ExpandedEnumValues.GLARES, () -> new Object[]{FriendsAndFoes.makeStringID("glares"), 15, true, false, 128})
			.build();
	}
}
