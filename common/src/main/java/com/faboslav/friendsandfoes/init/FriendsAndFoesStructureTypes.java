package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.world.structures.CitadelStructure;
import com.faboslav.friendsandfoes.world.structures.IceologerCabinStructure;
import com.faboslav.friendsandfoes.world.structures.IllusionerShackStructure;
import com.faboslav.friendsandfoes.world.structures.IllusionerTrainingGroundsStructure;
import net.minecraft.world.gen.structure.StructureType;

public final class FriendsAndFoesStructureTypes
{
	public static StructureType<CitadelStructure> CITADEL_STRUCTURE = () -> CitadelStructure.CODEC;
	public static StructureType<IllusionerShackStructure> ILLUSIONER_SHACK_STRUCTURE = () -> IllusionerShackStructure.CODEC;
	public static StructureType<IllusionerTrainingGroundsStructure> ILLUSIONER_TRAINING_GROUNDS_STRUCTURE = () -> IllusionerTrainingGroundsStructure.CODEC;
	public static StructureType<IceologerCabinStructure> ICEOLOGER_CABIN_STRUCTURE = () -> IceologerCabinStructure.CODEC;

	static {
		RegistryHelper.registerStructureType("citadel_structure", CITADEL_STRUCTURE);
		RegistryHelper.registerStructureType("illusioner_shack_structure", ILLUSIONER_SHACK_STRUCTURE);
		RegistryHelper.registerStructureType("illusioner_training_grounds_structure", ILLUSIONER_TRAINING_GROUNDS_STRUCTURE);
		RegistryHelper.registerStructureType("iceologer_cabin_structure", ICEOLOGER_CABIN_STRUCTURE);
	}

	public static void init() {
	}

	private FriendsAndFoesStructureTypes() {
	}
}
