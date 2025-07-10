package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.BarnacleEntityRenderer;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.WildfireEntityRenderer;
import com.faboslav.friendsandfoes.common.entity.*;
import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntityAttributesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntitySpawnRestrictionsEvent;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntityTypeResourceId;
import net.minecraft.SharedConstants;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacementTypes;
import net.minecraft.world.level.levelgen.Heightmap;

/**
 * @see EntityType
 */
public final class FriendsAndFoesEntityTypes
{
	public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(BuiltInRegistries.ENTITY_TYPE, FriendsAndFoes.MOD_ID);
	public static boolean previousUseChoiceTypeRegistrations = SharedConstants.CHECK_DATA_FIXER_SCHEMA;

	public static final RegistryEntry<EntityType<BarnacleEntity>> BARNACLE;
	public static final RegistryEntry<EntityType<CopperGolemEntity>> COPPER_GOLEM;
	public static final RegistryEntry<EntityType<CrabEntity>> CRAB;
	public static final RegistryEntry<EntityType<GlareEntity>> GLARE;
	public static final RegistryEntry<EntityType<IceologerEntity>> ICEOLOGER;
	public static final RegistryEntry<EntityType<IllusionerEntity>> ILLUSIONER;
	public static final RegistryEntry<EntityType<IceologerIceChunkEntity>> ICE_CHUNK;
	public static final RegistryEntry<EntityType<MaulerEntity>> MAULER;
	public static final RegistryEntry<EntityType<MoobloomEntity>> MOOBLOOM;
	public static final RegistryEntry<EntityType<PenguinEntity>> PENGUIN;
	public static final RegistryEntry<EntityType<RascalEntity>> RASCAL;
	public static final RegistryEntry<EntityType<TuffGolemEntity>> TUFF_GOLEM;
	public static final RegistryEntry<EntityType<WildfireEntity>> WILDFIRE;
	public static final RegistryEntry<EntityType<PlayerIllusionEntity>> PLAYER_ILLUSION;

	static {
		SharedConstants.CHECK_DATA_FIXER_SCHEMA = false;
		BARNACLE = ENTITY_TYPES.register("barnacle", () -> EntityType.Builder.of(BarnacleEntity::new, MobCategory.MONSTER).sized(1.69125F * BarnacleEntityRenderer.SCALE, 0.75F * BarnacleEntityRenderer.SCALE).eyeHeight(0.75F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("barnacle")));
		COPPER_GOLEM = ENTITY_TYPES.register("copper_golem", () -> EntityType.Builder.of(CopperGolemEntity::new, MobCategory.MISC).sized(0.75F, 1.375F).eyeHeight(0.75F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("copper_golem")));
		CRAB = ENTITY_TYPES.register("crab", () -> EntityType.Builder.of(CrabEntity::new, MobCategory.CREATURE).sized(0.875F, 0.5625F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("crab")));
		GLARE = ENTITY_TYPES.register("glare", () -> EntityType.Builder.of(GlareEntity::new, CustomSpawnGroup.getGlaresCategory()).sized(0.875F, 1.1875F).clientTrackingRange(8).updateInterval(2).build(VersionedEntityTypeResourceId.create("glare")));
		ICEOLOGER = ENTITY_TYPES.register("iceologer", () -> EntityType.Builder.of(IceologerEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("iceologer")));
		ILLUSIONER = ENTITY_TYPES.register("illusioner", () -> EntityType.Builder.of(IllusionerEntity::new, MobCategory.MONSTER).sized(0.6F, 1.95F).passengerAttachments(2.0F).ridingOffset(-0.6F).clientTrackingRange(8).build(VersionedEntityTypeResourceId.create("illusioner")));
		ICE_CHUNK = ENTITY_TYPES.register("ice_chunk", () -> EntityType.Builder.of(IceologerIceChunkEntity::new, MobCategory.MISC).fireImmune().sized(2.5F, 1.0F).clientTrackingRange(6).build(VersionedEntityTypeResourceId.create("ice_chunk")));
		MAULER = ENTITY_TYPES.register("mauler", () -> EntityType.Builder.of(MaulerEntity::new, MobCategory.CREATURE).sized(0.5625F, 0.5625F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("mauler")));
		MOOBLOOM = ENTITY_TYPES.register("moobloom", () -> EntityType.Builder.of(MoobloomEntity::new, MobCategory.CREATURE).sized(0.9F, 1.4F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("moobloom")));
		PENGUIN = ENTITY_TYPES.register("penguin", () -> EntityType.Builder.of(PenguinEntity::new, MobCategory.CREATURE).sized(0.875F, 1.1875F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("penguin")));
		RASCAL = ENTITY_TYPES.register("rascal", () -> EntityType.Builder.of(RascalEntity::new, CustomSpawnGroup.getRascalsCategory()).sized(0.9F, 1.25F).clientTrackingRange(10).canSpawnFarFromPlayer().build(VersionedEntityTypeResourceId.create("rascal")));
		TUFF_GOLEM = ENTITY_TYPES.register("tuff_golem", () -> EntityType.Builder.of(TuffGolemEntity::new, MobCategory.MISC).sized(0.75F, 1.0625F).eyeHeight(0.8F).clientTrackingRange(10).build(VersionedEntityTypeResourceId.create("tuff_golem")));
		WILDFIRE = ENTITY_TYPES.register("wildfire", () -> EntityType.Builder.of(WildfireEntity::new, MobCategory.MONSTER).sized(0.7F * WildfireEntityRenderer.SCALE, 1.875F * WildfireEntityRenderer.SCALE).clientTrackingRange(10).fireImmune().build(VersionedEntityTypeResourceId.create("wildfire")));
		PLAYER_ILLUSION = ENTITY_TYPES.register("player_illusion", () -> EntityType.Builder.of(PlayerIllusionEntity::new, MobCategory.MISC).sized(0.7F, 1.875F).clientTrackingRange(10).fireImmune().build(VersionedEntityTypeResourceId.create("player_illusion")));
		SharedConstants.CHECK_DATA_FIXER_SCHEMA = previousUseChoiceTypeRegistrations;
	}

	public static void registerEntityAttributes(RegisterEntityAttributesEvent event) {
		event.register(FriendsAndFoesEntityTypes.BARNACLE.get(), BarnacleEntity.createBarnacleAttributes());
		event.register(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), CopperGolemEntity.createCopperGolemAttributes());
		event.register(FriendsAndFoesEntityTypes.CRAB.get(), CrabEntity.createCrabAttributes());
		event.register(FriendsAndFoesEntityTypes.GLARE.get(), GlareEntity.createGlareAttributes());
		event.register(FriendsAndFoesEntityTypes.ICEOLOGER.get(), IceologerEntity.createIceologerAttributes());
		event.register(FriendsAndFoesEntityTypes.ILLUSIONER.get(), IllusionerEntity.createAttributes());
		event.register(FriendsAndFoesEntityTypes.MAULER.get(), MaulerEntity.createMaulerAttributes());
		event.register(FriendsAndFoesEntityTypes.MOOBLOOM.get(), MoobloomEntity.createAttributes());
		event.register(FriendsAndFoesEntityTypes.PENGUIN.get(), PenguinEntity.createPenguinAttributes());
		event.register(FriendsAndFoesEntityTypes.RASCAL.get(), RascalEntity.createRascalAttributes());
		event.register(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), TuffGolemEntity.createTuffGolemAttributes());
		event.register(FriendsAndFoesEntityTypes.WILDFIRE.get(), WildfireEntity.createWildfireAttributes());
		event.register(FriendsAndFoesEntityTypes.PLAYER_ILLUSION.get(), PlayerIllusionEntity.createMobAttributes());
	}

	public static void registerEntitySpawnRestrictions(RegisterEntitySpawnRestrictionsEvent event) {
		event.register(BARNACLE.get(), SpawnPlacementTypes.IN_WATER, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, BarnacleEntity::canSpawn);
		event.register(CRAB.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
		event.register(GLARE.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		event.register(ICEOLOGER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::checkPatrollingMonsterSpawnRules);
		event.register(ILLUSIONER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, IllusionerEntity::checkPatrollingMonsterSpawnRules);
		event.register(MAULER.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
		event.register(MOOBLOOM.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
		event.register(PENGUIN.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, PenguinEntity::canSpawn);
		event.register(RASCAL.get(), SpawnPlacementTypes.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, RascalEntity::canSpawn);
	}

	public static void addSpawnBiomeModifications(AddSpawnBiomeModificationsEvent event) {
		var config = FriendsAndFoes.getConfig();

		if (config.enableBarnacle && config.enableBarnacleSpawn) {
			event.add(FriendsAndFoesTags.HAS_BARNACLE, MobCategory.MONSTER, BARNACLE.get(), config.barnacleSpawnWeight, config.barnacleSpawnMinGroupSize, config.barnacleSpawnMaxGroupSize);
		}

		if (config.enableCrab && config.enableCrabSpawn) {
			event.add(FriendsAndFoesTags.HAS_CRAB, MobCategory.CREATURE, CRAB.get(), config.crabSpawnWeight, config.crabSpawnMinGroupSize, config.crabSpawnMaxGroupSize);
		}

		if (config.enableGlare && config.enableGlareSpawn) {
			event.add(FriendsAndFoesTags.HAS_GLARE, CustomSpawnGroup.getGlaresCategory(), GLARE.get(), config.glareSpawnWeight, config.glareSpawnMinGroupSize, config.glareSpawnMaxGroupSize);
		}

		if (config.enableMauler && config.enableMaulerSpawn) {
			if(config.enableMaulerSpawnInBadlands) {
				event.add(FriendsAndFoesTags.HAS_BADLANDS_MAULER, MobCategory.CREATURE, MAULER.get(), config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			}

			if(config.enableMaulerSpawnInDesert) {
				event.add(FriendsAndFoesTags.HAS_DESERT_MAULER, MobCategory.CREATURE, MAULER.get(), config.maulerDesertSpawnWeight, config.maulerDesertSpawnMinGroupSize, config.maulerDesertSpawnMaxGroupSize);
			}

			if(config.enableMaulerSpawnInSavanna) {
				event.add(FriendsAndFoesTags.HAS_SAVANNA_MAULER, MobCategory.CREATURE, MAULER.get(), config.maulerSavannaSpawnWeight, config.maulerSavannaSpawnMinGroupSize, config.maulerSavannaSpawnMaxGroupSize);
			}
		}

		if (config.enableMoobloom && config.enableMoobloomSpawn) {
			event.add(FriendsAndFoesTags.HAS_MOOBLOOMS, MobCategory.CREATURE, MOOBLOOM.get(), config.moobloomSpawnWeight, config.moobloomSpawnMinGroupSize, config.moobloomSpawnMaxGroupSize);
		}

		if (config.enablePenguin && config.enablePenguinSpawn) {
			event.add(FriendsAndFoesTags.HAS_PENGUIN, MobCategory.CREATURE, PENGUIN.get(), config.penguinSpawnWeight, config.penguinSpawnMinGroupSize, config.penguinSpawnMaxGroupSize);
		}

		if (config.enableRascal && config.enableRascalSpawn) {
			event.add(FriendsAndFoesTags.HAS_RASCAL, CustomSpawnGroup.getRascalsCategory(), RASCAL.get(), config.rascalSpawnWeight, config.rascalSpawnMinGroupSize, config.rascalSpawnMaxGroupSize);
		}
	}

	private FriendsAndFoesEntityTypes() {
	}
}
