package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.world.structures.CitadelStructure;
import com.faboslav.friendsandfoes.common.world.structures.IceologerCabinStructure;
import com.faboslav.friendsandfoes.common.world.structures.IllusionerShackStructure;
import com.faboslav.friendsandfoes.common.world.structures.IllusionerTrainingGroundsStructure;
import net.minecraft.registry.Registries;
import net.minecraft.world.gen.structure.StructureType;

public final class FriendsAndFoesStructureTypes {
    public static final ResourcefulRegistry<StructureType<?>> STRUCTURES = ResourcefulRegistries.create(Registries.STRUCTURE_TYPE, FriendsAndFoes.MOD_ID);

    public static final RegistryEntry<StructureType<CitadelStructure>> CITADEL_STRUCTURE = STRUCTURES.register("citadel_structure", () -> () -> CitadelStructure.CODEC);
    public static final RegistryEntry<StructureType<IllusionerShackStructure>> ILLUSIONER_SHACK_STRUCTURE = STRUCTURES.register("illusioner_shack_structure", () -> () -> IllusionerShackStructure.CODEC);
    public static final RegistryEntry<StructureType<IllusionerTrainingGroundsStructure>> ILLUSIONER_TRAINING_GROUNDS_STRUCTURE = STRUCTURES.register("illusioner_training_grounds_structure", () -> () -> IllusionerTrainingGroundsStructure.CODEC);
    public static final RegistryEntry<StructureType<IceologerCabinStructure>> ICEOLOGER_CABIN_STRUCTURE = STRUCTURES.register("iceologer_cabin_structure", () -> () -> IceologerCabinStructure.CODEC);

    private FriendsAndFoesStructureTypes() {
    }
}
