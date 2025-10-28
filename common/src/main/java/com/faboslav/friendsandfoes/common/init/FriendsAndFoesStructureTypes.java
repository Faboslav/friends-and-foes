package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.world.structures.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;

public final class FriendsAndFoesStructureTypes
{
	public static final ResourcefulRegistry<StructureType<?>> STRUCTURES = ResourcefulRegistries.create(BuiltInRegistries.STRUCTURE_TYPE, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<StructureType<JigsawStructure>> CITADEL_STRUCTURE = STRUCTURES.register("citadel_structure", () -> () -> CitadelStructure.CODEC);
	public static final RegistryEntry<StructureType<JigsawStructure>> ILLUSIONER_SHACK_STRUCTURE = STRUCTURES.register("illusioner_shack_structure", () -> () -> IllusionerShackStructure.CODEC);
	public static final RegistryEntry<StructureType<JigsawStructure>> ILLUSIONER_TRAINING_GROUNDS_STRUCTURE = STRUCTURES.register("illusioner_training_grounds_structure", () -> () -> IllusionerTrainingGroundsStructure.CODEC);
	public static final RegistryEntry<StructureType<JigsawStructure>> ICEOLOGER_CABIN_STRUCTURE = STRUCTURES.register("iceologer_cabin_structure", () -> () -> IceologerCabinStructure.CODEC);

	private FriendsAndFoesStructureTypes() {
	}
}
