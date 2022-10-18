package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.world.structures.LavaOceanStructure;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.structure.StructureType;

public final class FriendsAndFoesStructureTypes
{
	public static StructureType<LavaOceanStructure> LAVA_OCEAN_STRUCTURE = () -> LavaOceanStructure.CODEC;

	static {
		RegistryHelper.registerStructureType(new Identifier(FriendsAndFoes.MOD_ID, "lava_ocean_structure"), LAVA_OCEAN_STRUCTURE);
	}

	public static void init() {
	}

	private FriendsAndFoesStructureTypes() {
	}
}
