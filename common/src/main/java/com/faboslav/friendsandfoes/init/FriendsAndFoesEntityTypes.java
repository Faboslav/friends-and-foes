package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.client.render.entity.renderer.WildfireEntityRenderer;
import com.faboslav.friendsandfoes.entity.*;
import com.faboslav.friendsandfoes.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.mixin.SpawnRestrictionAccessor;
import com.faboslav.friendsandfoes.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import net.minecraft.SharedConstants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnLocationTypes;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.world.Heightmap;

import java.util.function.Supplier;

/**
 * @see EntityType
 */
public final class FriendsAndFoesEntityTypes
{
	public static boolean previousUseChoiceTypeRegistrations = SharedConstants.useChoiceTypeRegistrations;

	public static final Supplier<EntityType<CopperGolemEntity>> COPPER_GOLEM;
	public static final Supplier<EntityType<GlareEntity>> GLARE;
	public static final Supplier<EntityType<IceologerEntity>> ICEOLOGER;
	public static final Supplier<EntityType<IceologerIceChunkEntity>> ICE_CHUNK;
	public static final Supplier<EntityType<MaulerEntity>> MAULER;
	public static final Supplier<EntityType<MoobloomEntity>> MOOBLOOM;
	public static final Supplier<EntityType<RascalEntity>> RASCAL;
	public static final Supplier<EntityType<TuffGolemEntity>> TUFF_GOLEM;
	public static final Supplier<EntityType<WildfireEntity>> WILDFIRE;
	public static final Supplier<EntityType<PlayerIllusionEntity>> PLAYER_ILLUSION;

	static {
		SharedConstants.useChoiceTypeRegistrations = false;
		COPPER_GOLEM = RegistryHelper.registerEntityType("copper_golem", () -> EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC).dimensions(0.75F, 1.375F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("copper_golem")));
		GLARE = RegistryHelper.registerEntityType("glare", () -> EntityType.Builder.create(GlareEntity::new, CustomSpawnGroup.getGlaresCategory()).dimensions(0.875F, 1.1875F).maxTrackingRange(8).trackingTickInterval(2).build(FriendsAndFoes.makeStringID("glare")));
		ICEOLOGER = RegistryHelper.registerEntityType("iceologer", () -> EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).dimensions(0.6F, 1.95F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("iceologer")));
		ICE_CHUNK = RegistryHelper.registerEntityType("ice_chunk", () -> EntityType.Builder.create(IceologerIceChunkEntity::new, SpawnGroup.MISC).makeFireImmune().dimensions(2.5F, 1.0F).maxTrackingRange(6).build(FriendsAndFoes.makeStringID("ice_chunk")));
		MAULER = RegistryHelper.registerEntityType("mauler", () -> EntityType.Builder.create(MaulerEntity::new, SpawnGroup.CREATURE).dimensions(0.5625F, 0.5625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("mauler")));
		MOOBLOOM = RegistryHelper.registerEntityType("moobloom", () -> EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.CREATURE).dimensions(0.9F, 1.4F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("moobloom")));
		RASCAL = RegistryHelper.registerEntityType("rascal", () -> EntityType.Builder.create(RascalEntity::new, CustomSpawnGroup.getRascalsCategory()).dimensions(0.9F, 1.25F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("rascal")));
		TUFF_GOLEM = RegistryHelper.registerEntityType("tuff_golem", () -> EntityType.Builder.create(TuffGolemEntity::new, SpawnGroup.MISC).dimensions(0.75F, 1.0625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("tuff_golem")));
		WILDFIRE = RegistryHelper.registerEntityType("wildfire", () -> EntityType.Builder.create(WildfireEntity::new, SpawnGroup.MONSTER).dimensions(0.7F * WildfireEntityRenderer.SCALE, 1.875F * WildfireEntityRenderer.SCALE).maxTrackingRange(10).makeFireImmune().build(FriendsAndFoes.makeStringID("wildfire")));
		PLAYER_ILLUSION = RegistryHelper.registerEntityType("player_illusion", () -> EntityType.Builder.create(PlayerIllusionEntity::new, SpawnGroup.MISC).dimensions(0.7F, 1.875F).maxTrackingRange(10).makeFireImmune().build(FriendsAndFoes.makeStringID("player_illusion")));
		SharedConstants.useChoiceTypeRegistrations = previousUseChoiceTypeRegistrations;
	}

	public static void init() {
		createMobAttributes();
	}

	public static void postInit() {
		initSpawnRestrictions();
	}

	public static void createMobAttributes() {
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.COPPER_GOLEM, CopperGolemEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.GLARE, GlareEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.ICEOLOGER, IceologerEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.MAULER, MaulerEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.MOOBLOOM, MoobloomEntity::createCowAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.RASCAL, RascalEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.TUFF_GOLEM, TuffGolemEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.WILDFIRE, WildfireEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.PLAYER_ILLUSION, PlayerIllusionEntity::createMobAttributes);
	}

	public static void initSpawnRestrictions() {
		SpawnRestrictionAccessor.callRegister(GLARE.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ICEOLOGER.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MAULER.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(RASCAL.get(), SpawnLocationTypes.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, RascalEntity::canSpawn);
	}

	public static void addSpawnBiomeModifications(AddSpawnBiomeModificationsEvent event) {
		var config = FriendsAndFoes.getConfig();

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
