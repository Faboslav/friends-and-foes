package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.api.MoobloomVariants;
import com.faboslav.friendsandfoes.client.render.entity.renderer.WildfireEntityRenderer;
import com.faboslav.friendsandfoes.entity.*;
import com.faboslav.friendsandfoes.mixin.SpawnRestrictionAccessor;
import com.faboslav.friendsandfoes.platform.BiomeModifications;
import com.faboslav.friendsandfoes.platform.CustomSpawnGroup;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.faboslav.friendsandfoes.tag.FriendsAndFoesTags;
import net.minecraft.SharedConstants;
import net.minecraft.block.PlantBlock;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
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
	public static final Supplier<EntityType<WildfireEntity>> WILDFIRE;

	static {
		SharedConstants.useChoiceTypeRegistrations = false;
		COPPER_GOLEM = RegistryHelper.registerEntityType("copper_golem", () -> EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC).setDimensions(0.75F, 1.375F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("copper_golem")));
		GLARE = RegistryHelper.registerEntityType("glare", () -> EntityType.Builder.create(GlareEntity::new, CustomSpawnGroup.getGlaresCategory()).setDimensions(0.875F, 1.4375F).maxTrackingRange(8).trackingTickInterval(2).build(FriendsAndFoes.makeStringID("glare")));
		ICEOLOGER = RegistryHelper.registerEntityType("iceologer", () -> EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).setDimensions(0.6F, 1.95F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("iceologer")));
		ICE_CHUNK = RegistryHelper.registerEntityType("ice_chunk", () -> EntityType.Builder.create(IceologerIceChunkEntity::new, SpawnGroup.MISC).makeFireImmune().setDimensions(2.5F, 1.0F).maxTrackingRange(6).build(FriendsAndFoes.makeStringID("ice_chunk")));
		MAULER = RegistryHelper.registerEntityType("mauler", () -> EntityType.Builder.create(MaulerEntity::new, SpawnGroup.CREATURE).setDimensions(0.5625F, 0.5625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("mauler")));
		MOOBLOOM = RegistryHelper.registerEntityType("moobloom", () -> EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.CREATURE).setDimensions(0.9F, 1.4F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("moobloom")));
		WILDFIRE = RegistryHelper.registerEntityType("wildfire", () -> EntityType.Builder.create(WildfireEntity::new, SpawnGroup.MONSTER).setDimensions(0.7F * WildfireEntityRenderer.SCALE, 1.875F * WildfireEntityRenderer.SCALE).maxTrackingRange(10).makeFireImmune().build(FriendsAndFoes.makeStringID("wildfire")));
		SharedConstants.useChoiceTypeRegistrations = previousUseChoiceTypeRegistrations;
	}

	public static void init() {
		createMobAttributes();
	}

	public static void postInit() {
		initSpawnRestrictions();
		addSpawns();
		addMoobloomVariants();
	}

	public static void createMobAttributes() {
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.COPPER_GOLEM, CopperGolemEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.GLARE, GlareEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.ICEOLOGER, IceologerEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.MAULER, MaulerEntity::createAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.MOOBLOOM, MoobloomEntity::createCowAttributes);
		RegistryHelper.registerEntityAttribute(FriendsAndFoesEntityTypes.WILDFIRE, WildfireEntity::createAttributes);
	}

	public static void initSpawnRestrictions() {
		SpawnRestrictionAccessor.callRegister(GLARE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ICEOLOGER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MAULER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
	}

	public static void addSpawns() {
		var config = FriendsAndFoes.getConfig();

		if (config.enableGlare && config.enableGlareSpawn) {
			BiomeModifications.addMobSpawn(FriendsAndFoesTags.HAS_GLARE, GLARE.get(), CustomSpawnGroup.getGlaresCategory(), config.glareSpawnWeight, config.glareSpawnMinGroupSize, config.glareSpawnMaxGroupSize);
		}

		if (config.enableMauler && config.enableMaulerSpawn) {
			BiomeModifications.addMobSpawn(FriendsAndFoesTags.HAS_BADLANDS_MAULER, MAULER.get(), SpawnGroup.CREATURE, config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			BiomeModifications.addMobSpawn(FriendsAndFoesTags.HAS_DESERT_MAULER, MAULER.get(), SpawnGroup.CREATURE, config.maulerDesertSpawnWeight, config.maulerDesertSpawnMinGroupSize, config.maulerDesertSpawnMaxGroupSize);
			BiomeModifications.addMobSpawn(FriendsAndFoesTags.HAS_SAVANNA_MAULER, MAULER.get(), SpawnGroup.CREATURE, config.maulerSavannaSpawnWeight, config.maulerSavannaSpawnMinGroupSize, config.maulerSavannaSpawnMaxGroupSize);
		}

		if (config.enableMoobloom && config.enableMoobloomSpawn) {
			BiomeModifications.addMobSpawn(FriendsAndFoesTags.HAS_LESS_MOOBLOOMS, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomFlowerForestSpawnWeight, config.moobloomFlowerForestSpawnMinGroupSize, config.moobloomFlowerForestSpawnMaxGroupSize);
			BiomeModifications.addMobSpawn(FriendsAndFoesTags.HAS_MORE_MOOBLOOMS, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomMeadowSpawnWeight, config.moobloomMeadowSpawnMinGroupSize, config.moobloomMeadowSpawnMaxGroupSize);
		}
	}

	public static void addMoobloomVariants() {
		MoobloomVariants.add("buttercup", (PlantBlock) FriendsAndFoesBlocks.BUTTERCUP.get());
	}

	private FriendsAndFoesEntityTypes() {
	}
}
