package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.world.gen.feature.IceologerCabinStructure;
import com.faboslav.friendsandfoes.world.gen.feature.IllusionerShackStructure;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.structure.Structure;
import net.minecraft.world.gen.structure.Structures;


/**
 * @see Structures
 */
public final class ModStructures
{
	private static final DeferredRegister<Structure> STRUCTURES = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.STRUCTURE_KEY);

	public static final RegistrySupplier<Structure> ILLUSIONER_SHACK;
	public static final RegistrySupplier<Structure> ICEOLOGER_CABIN;

	static {
		//PILLAGER_OUTPOST = register(StructureKeys.PILLAGER_OUTPOST, new JigsawStructure(createConfig(BiomeTags.PILLAGER_OUTPOST_HAS_STRUCTURE, Map.of(SpawnGroup.MONSTER, new StructureSpawns(StructureSpawns.BoundingBox.STRUCTURE, Pool.of(new SpawnSettings.SpawnEntry[]{new SpawnSettings.SpawnEntry(EntityType.PILLAGER, 1, 1, 1)}))), GenerationStep.Feature.SURFACE_STRUCTURES, StructureTerrainAdaptation.BEARD_THIN), PillagerOutpostGenerator.STRUCTURE_POOLS, 7, ConstantHeightProvider.create(YOffset.fixed(0)), true, Heightmap.Type.WORLD_SURFACE_WG));

		ILLUSIONER_SHACK = STRUCTURES.register("illusioner_shack", () -> new IllusionerShackStructure(/*load from json */);
		ICEOLOGER_CABIN = STRUCTURES.register("iceologer_cabin", () -> new IceologerCabinStructure(/*load from json */);
	}


	public static void initRegister() {
		STRUCTURES.register();
	}

	public static void init() {
	}

	private ModStructures() {
	}
}
