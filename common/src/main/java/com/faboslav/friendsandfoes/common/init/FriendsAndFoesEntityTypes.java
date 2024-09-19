package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.render.entity.renderer.WildfireEntityRenderer;
import com.faboslav.friendsandfoes.common.entity.*;
import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntityAttributesEvent;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterEntitySpawnRestrictionsEvent;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import com.faboslav.friendsandfoes.common.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import net.minecraft.SharedConstants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;

/**
 * @see EntityType
 */
public final class FriendsAndFoesEntityTypes
{
	public static final ResourcefulRegistry<EntityType<?>> ENTITY_TYPES = ResourcefulRegistries.create(Registry.ENTITY_TYPE, FriendsAndFoes.MOD_ID);
	public static boolean previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;

	public static final RegistryEntry<EntityType<CopperGolemEntity>> COPPER_GOLEM;
	public static final RegistryEntry<EntityType<CrabEntity>> CRAB;
	public static final RegistryEntry<EntityType<GlareEntity>> GLARE;
	public static final RegistryEntry<EntityType<IceologerEntity>> ICEOLOGER;
	public static final RegistryEntry<EntityType<IceologerIceChunkEntity>> ICE_CHUNK;
	public static final RegistryEntry<EntityType<MaulerEntity>> MAULER;
	public static final RegistryEntry<EntityType<MoobloomEntity>> MOOBLOOM;
	public static final RegistryEntry<EntityType<RascalEntity>> RASCAL;
	public static final RegistryEntry<EntityType<TuffGolemEntity>> TUFF_GOLEM;
	public static final RegistryEntry<EntityType<WildfireEntity>> WILDFIRE;
	public static final RegistryEntry<EntityType<PlayerIllusionEntity>> PLAYER_ILLUSION;

	static {
		SharedConstants.useChoiceTypeRegistrations = false;
		COPPER_GOLEM = ENTITY_TYPES.register("copper_golem", () -> EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC).setDimensions(0.75F, 1.375F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("copper_golem")));
		CRAB = ENTITY_TYPES.register("crab", () -> EntityType.Builder.create(CrabEntity::new, SpawnGroup.CREATURE).setDimensions(0.875F, 0.5625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("crab")));
		GLARE = ENTITY_TYPES.register("glare", () -> EntityType.Builder.create(GlareEntity::new, CustomSpawnGroup.getGlaresCategory()).setDimensions(0.875F, 1.1875F).maxTrackingRange(8).trackingTickInterval(2).build(FriendsAndFoes.makeStringID("glare")));
		ICEOLOGER = ENTITY_TYPES.register("iceologer", () -> EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).setDimensions(0.6F, 1.95F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("iceologer")));
		ICE_CHUNK = ENTITY_TYPES.register("ice_chunk", () -> EntityType.Builder.create(IceologerIceChunkEntity::new, SpawnGroup.MISC).makeFireImmune().setDimensions(2.5F, 1.0F).maxTrackingRange(6).build(FriendsAndFoes.makeStringID("ice_chunk")));
		MAULER = ENTITY_TYPES.register("mauler", () -> EntityType.Builder.create(MaulerEntity::new, SpawnGroup.CREATURE).setDimensions(0.5625F, 0.5625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("mauler")));
		MOOBLOOM = ENTITY_TYPES.register("moobloom", () -> EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.CREATURE).setDimensions(0.9F, 1.4F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("moobloom")));
		RASCAL = ENTITY_TYPES.register("rascal", () -> EntityType.Builder.create(RascalEntity::new, CustomSpawnGroup.getRascalsCategory()).setDimensions(0.9F, 1.25F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("rascal")));
		TUFF_GOLEM = ENTITY_TYPES.register("tuff_golem", () -> EntityType.Builder.create(TuffGolemEntity::new, SpawnGroup.MISC).setDimensions(0.75F, 1.0625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("tuff_golem")));
		WILDFIRE = ENTITY_TYPES.register("wildfire", () -> EntityType.Builder.create(WildfireEntity::new, SpawnGroup.MONSTER).setDimensions(0.7F * WildfireEntityRenderer.SCALE, 1.875F * WildfireEntityRenderer.SCALE).maxTrackingRange(10).makeFireImmune().build(FriendsAndFoes.makeStringID("wildfire")));
		PLAYER_ILLUSION = ENTITY_TYPES.register("player_illusion", () -> EntityType.Builder.create(PlayerIllusionEntity::new, SpawnGroup.MISC).setDimensions(0.7F, 1.875F).maxTrackingRange(10).makeFireImmune().build(FriendsAndFoes.makeStringID("player_illusion")));
		SharedConstants.useChoiceTypeRegistrations = previousUseChoiceTypeRegistrations;
	}

	public static void registerEntityAttributes(RegisterEntityAttributesEvent event) {
		event.register(FriendsAndFoesEntityTypes.COPPER_GOLEM.get(), CopperGolemEntity.createCopperGolemAttributes());
		event.register(FriendsAndFoesEntityTypes.CRAB.get(), CrabEntity.createCrabAttributes());
		event.register(FriendsAndFoesEntityTypes.GLARE.get(), GlareEntity.createGlareAttributes());
		event.register(FriendsAndFoesEntityTypes.ICEOLOGER.get(), IceologerEntity.createIceologerAttributes());
		event.register(FriendsAndFoesEntityTypes.MAULER.get(), MaulerEntity.createMaulerAttributes());
		event.register(FriendsAndFoesEntityTypes.MOOBLOOM.get(), MoobloomEntity.createCowAttributes());
		event.register(FriendsAndFoesEntityTypes.RASCAL.get(), RascalEntity.createRascalAttributes());
		event.register(FriendsAndFoesEntityTypes.TUFF_GOLEM.get(), TuffGolemEntity.createTuffGolemAttributes());
		event.register(FriendsAndFoesEntityTypes.WILDFIRE.get(), WildfireEntity.createWildfireAttributes());
		event.register(FriendsAndFoesEntityTypes.PLAYER_ILLUSION.get(), PlayerIllusionEntity.createMobAttributes());
	}

	public static void registerEntitySpawnRestrictions(RegisterEntitySpawnRestrictionsEvent event) {
		event.register(CRAB.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, CrabEntity::canSpawn);
		event.register(GLARE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		event.register(ICEOLOGER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::canSpawn);
		event.register(MAULER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
		event.register(MOOBLOOM.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
		event.register(RASCAL.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RascalEntity::canSpawn);
	}

	public static void addSpawnBiomeModifications(AddSpawnBiomeModificationsEvent event) {
		var config = FriendsAndFoes.getConfig();

		if (config.enableCrab && config.enableCrabSpawn) {
			event.add(FriendsAndFoesTags.HAS_CRAB, SpawnGroup.CREATURE, CRAB.get(), config.crabSpawnWeight, config.crabSpawnMinGroupSize, config.crabSpawnMaxGroupSize);
		}

		if (config.enableGlare && config.enableGlareSpawn) {
			event.add(FriendsAndFoesTags.HAS_GLARE, CustomSpawnGroup.getGlaresCategory(), GLARE.get(), config.glareSpawnWeight, config.glareSpawnMinGroupSize, config.glareSpawnMaxGroupSize);
		}

		if (config.enableMauler && config.enableMaulerSpawn) {
			event.add(FriendsAndFoesTags.HAS_BADLANDS_MAULER, SpawnGroup.CREATURE, MAULER.get(), config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			event.add(FriendsAndFoesTags.HAS_DESERT_MAULER, SpawnGroup.CREATURE, MAULER.get(), config.maulerDesertSpawnWeight, config.maulerDesertSpawnMinGroupSize, config.maulerDesertSpawnMaxGroupSize);
			event.add(FriendsAndFoesTags.HAS_SAVANNA_MAULER, SpawnGroup.CREATURE, MAULER.get(), config.maulerSavannaSpawnWeight, config.maulerSavannaSpawnMinGroupSize, config.maulerSavannaSpawnMaxGroupSize);
		}

		if (config.enableMoobloom && config.enableMoobloomSpawn) {
			event.add(FriendsAndFoesTags.HAS_MOOBLOOMS, SpawnGroup.CREATURE, MOOBLOOM.get(), config.moobloomSpawnWeight, config.moobloomSpawnMinGroupSize, config.moobloomSpawnMaxGroupSize);
		}

		if (config.enableRascal && config.enableRascalSpawn) {
			event.add(FriendsAndFoesTags.HAS_RASCAL, CustomSpawnGroup.getRascalsCategory(), RASCAL.get(), 4, 1, 1);
		}
	}

	private FriendsAndFoesEntityTypes() {
	}
}
