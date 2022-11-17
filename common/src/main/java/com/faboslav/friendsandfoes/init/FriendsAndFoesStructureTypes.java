package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.world.structures.LavaOceanStructure;
import net.minecraft.world.gen.structure.StructureType;

public final class FriendsAndFoesStructureTypes
{
	public static StructureType<LavaOceanStructure> LAVA_OCEAN_STRUCTURE = () -> LavaOceanStructure.CODEC;

	static {
		RegistryHelper.registerStructureType("lava_ocean_structure", LAVA_OCEAN_STRUCTURE);
	}

	public static void init() {
	}

	private FriendsAndFoesStructureTypes() {
	}
}
