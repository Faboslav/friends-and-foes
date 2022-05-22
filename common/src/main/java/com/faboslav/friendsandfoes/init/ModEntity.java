package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.IceologerEntity;
import com.faboslav.friendsandfoes.entity.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.entity.GlareEntity;
import com.faboslav.friendsandfoes.entity.MaulerEntity;
import com.faboslav.friendsandfoes.entity.MoobloomEntity;
import com.faboslav.friendsandfoes.mixin.SpawnRestrictionAccessor;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.SharedConstants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;
import net.minecraft.world.biome.SpawnSettings;

import java.util.Objects;
import java.util.function.Predicate;

/**
 * @see EntityType
 */
public final class ModEntity
{
	private static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.ENTITY_TYPE_KEY);
	private static boolean previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;

	public static final RegistrySupplier<EntityType<CopperGolemEntity>> COPPER_GOLEM;
	public static final RegistrySupplier<EntityType<GlareEntity>> GLARE;
	public static final RegistrySupplier<EntityType<IceologerEntity>> ICEOLOGER;
	public static final RegistrySupplier<EntityType<IceologerIceChunkEntity>> ICE_CHUNK;
	public static final RegistrySupplier<EntityType<MaulerEntity>> MAULER;
	public static final RegistrySupplier<EntityType<MoobloomEntity>> MOOBLOOM;

	static {
		SharedConstants.useChoiceTypeRegistrations = false;
		COPPER_GOLEM = ENTITY_TYPES.register("copper_golem", () -> EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC).setDimensions(0.75F, 1.375F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("copper_golem")));
		GLARE = ENTITY_TYPES.register("glare", () -> EntityType.Builder.create(GlareEntity::new, SpawnGroup.AMBIENT).setDimensions(0.875F, 1.4375F).maxTrackingRange(8).build(FriendsAndFoes.makeStringID("glare")));
		ICEOLOGER = ENTITY_TYPES.register("iceologer", () -> EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).setDimensions(0.6F, 1.95F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("iceologer")));
		ICE_CHUNK = ENTITY_TYPES.register("ice_chunk", () -> EntityType.Builder.create(IceologerIceChunkEntity::new, SpawnGroup.MISC).makeFireImmune().setDimensions(2.5F, 1.0F).maxTrackingRange(6).build(FriendsAndFoes.makeStringID("ice_chunk")));
		MAULER = ENTITY_TYPES.register("mauler", () -> EntityType.Builder.create(MaulerEntity::new, SpawnGroup.CREATURE).setDimensions(0.5625F, 0.5625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("mauler")));
		MOOBLOOM = ENTITY_TYPES.register("moobloom", () -> EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.CREATURE).setDimensions(0.9F, 1.4F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("moobloom")));
		SharedConstants.useChoiceTypeRegistrations = previousUseChoiceTypeRegistrations;
	}

	public static void initRegister() {
		previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;
		SharedConstants.useChoiceTypeRegistrations = false;
		ENTITY_TYPES.register();
		SharedConstants.useChoiceTypeRegistrations = previousUseChoiceTypeRegistrations;
		initMobAttributes();
	}

	public static void init() {
		initSpawnRestrictions();
		initBiomeModifications();
	}

	public static void initMobAttributes() {
		EntityAttributeRegistry.register(ModEntity.COPPER_GOLEM, CopperGolemEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntity.GLARE, GlareEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntity.ICEOLOGER, IceologerEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntity.MAULER, MaulerEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntity.MOOBLOOM, MoobloomEntity::createCowAttributes);
	}

	public static void initSpawnRestrictions() {
		SpawnRestrictionAccessor.callRegister(GLARE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ICEOLOGER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MAULER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
	}

	public static void initBiomeModifications() {
		var config = FriendsAndFoes.getConfig();

		if (config.enableGlareSpawn) {
			Predicate<BiomeModifications.BiomeContext> LUSH_CAVES = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.LUSH_CAVES.getValue());
			registerBiomeModification(LUSH_CAVES, GLARE.get(), SpawnGroup.AMBIENT, config.glareSpawnWeight, config.glareSpawnMinGroupSize, config.glareSpawnMaxGroupSize);
		}

		if (config.enableMaulerSpawn) {
			Predicate<BiomeModifications.BiomeContext> DESERT = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.DESERT.getValue());
			Predicate<BiomeModifications.BiomeContext> BADLANDS = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.BADLANDS.getValue());
			Predicate<BiomeModifications.BiomeContext> ERODED_BADLANDS = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.ERODED_BADLANDS.getValue());
			Predicate<BiomeModifications.BiomeContext> WOODED_BADLANDS = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.WOODED_BADLANDS.getValue());
			Predicate<BiomeModifications.BiomeContext> SAVANNA = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.SAVANNA.getValue());
			Predicate<BiomeModifications.BiomeContext> SAVANNA_PLATEAU = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.SAVANNA_PLATEAU.getValue());

			registerBiomeModification(DESERT, MAULER.get(), SpawnGroup.CREATURE, config.maulerDesertSpawnWeight, config.maulerDesertSpawnMinGroupSize, config.maulerDesertSpawnMaxGroupSize);
			registerBiomeModification(BADLANDS, MAULER.get(), SpawnGroup.CREATURE, config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			registerBiomeModification(ERODED_BADLANDS, MAULER.get(), SpawnGroup.CREATURE, config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			registerBiomeModification(WOODED_BADLANDS, MAULER.get(), SpawnGroup.CREATURE, config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			registerBiomeModification(SAVANNA, MAULER.get(), SpawnGroup.CREATURE, config.maulerSavannaSpawnWeight, config.maulerSavannaSpawnMinGroupSize, config.maulerSavannaSpawnMaxGroupSize);
			registerBiomeModification(SAVANNA_PLATEAU, MAULER.get(), SpawnGroup.CREATURE, config.maulerSavannaSpawnWeight, config.maulerSavannaSpawnMinGroupSize, config.maulerSavannaSpawnMaxGroupSize);
		}

		if (config.enableMoobloomSpawn) {
			Predicate<BiomeModifications.BiomeContext> FLOWER_FOREST = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.FLOWER_FOREST.getValue());
			Predicate<BiomeModifications.BiomeContext> MEADOW = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.MEADOW.getValue());

			registerBiomeModification(FLOWER_FOREST, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomFlowerForestSpawnWeight, config.moobloomFlowerForestSpawnMinGroupSize, config.moobloomFlowerForestSpawnMaxGroupSize);
			registerBiomeModification(MEADOW, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomMeadowSpawnWeight, config.moobloomMeadowSpawnMinGroupSize, config.moobloomMeadowSpawnMaxGroupSize);
		}
	}

	private static void registerBiomeModification(
		Predicate<BiomeModifications.BiomeContext> biomes,
		EntityType<?> type,
		SpawnGroup spawnGroup,
		int weight,
		int min,
		int max
	) {
		BiomeModifications.addProperties(biomes, (ctx, p) -> p.getSpawnProperties().addSpawn(spawnGroup, new SpawnSettings.SpawnEntry(type, weight, min, max)));
	}

	private ModEntity() {
	}
}
