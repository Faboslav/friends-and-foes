package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.*;
import com.faboslav.friendsandfoes.mixin.SpawnRestrictionAccessor;
import com.faboslav.friendsandfoes.util.ExpandedEnumValues;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.SharedConstants;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.SpawnRestriction;
import net.minecraft.tag.BiomeTags;
import net.minecraft.tag.TagKey;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;

/**
 * @see EntityType
 */
public final class ModEntityTypes
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
		GLARE = ENTITY_TYPES.register("glare", () -> EntityType.Builder.create(GlareEntity::new, SpawnGroup.CREATURE).setDimensions(0.875F, 1.4375F).maxTrackingRange(8).trackingTickInterval(2).build(FriendsAndFoes.makeStringID("glare")));
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
		addSpawns();
	}

	public static void initMobAttributes() {
		EntityAttributeRegistry.register(ModEntityTypes.COPPER_GOLEM, CopperGolemEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntityTypes.GLARE, GlareEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntityTypes.ICEOLOGER, IceologerEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntityTypes.MAULER, MaulerEntity::createAttributes);
		EntityAttributeRegistry.register(ModEntityTypes.MOOBLOOM, MoobloomEntity::createCowAttributes);
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
			addSpawn(ModTags.HAS_GLARE, GLARE.get(), SpawnGroup.CREATURE, config.glareSpawnWeight, config.glareSpawnMinGroupSize, config.glareSpawnMaxGroupSize);
		}

		if (config.enableMauler && config.enableMaulerSpawn) {
			addSpawn(ModTags.HAS_DESERT_MAULER, MAULER.get(), SpawnGroup.CREATURE, config.maulerDesertSpawnWeight, config.maulerDesertSpawnMinGroupSize, config.maulerDesertSpawnMaxGroupSize);
			addSpawn(ModTags.HAS_BADLANDS_MAULER, MAULER.get(), SpawnGroup.CREATURE, config.maulerBadlandsSpawnWeight, config.maulerBadlandsSpawnMinGroupSize, config.maulerBadlandsSpawnMaxGroupSize);
			addSpawn(ModTags.HAS_SAVANNA_MAULER, MAULER.get(), SpawnGroup.CREATURE, config.maulerSavannaSpawnWeight, config.maulerSavannaSpawnMinGroupSize, config.maulerSavannaSpawnMaxGroupSize);
		}

		if (config.enableMoobloom && config.enableMoobloomSpawn) {
			addSpawn(ModTags.HAS_LESS_MOOBLOOMS, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomFlowerForestSpawnWeight, config.moobloomFlowerForestSpawnMinGroupSize, config.moobloomFlowerForestSpawnMaxGroupSize);
			addSpawn(ModTags.HAS_MORE_MOOBLOOMS, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomMeadowSpawnWeight, config.moobloomMeadowSpawnMinGroupSize, config.moobloomMeadowSpawnMaxGroupSize);
		}
	}

	private static void addSpawn(
		TagKey<Biome> tag,
		EntityType<?> type,
		SpawnGroup spawnGroup,
		int weight,
		int min,
		int max
	) {
		BiomeModifications.addProperties((biomeContext -> {
			return biomeContext.hasTag(tag) && biomeContext.hasTag(BiomeTags.IS_OVERWORLD);
		}), (ctx, p) -> {
			p.getSpawnProperties().addSpawn(spawnGroup, new SpawnSettings.SpawnEntry(type, weight, min, max));
		});
	}

	private ModEntityTypes() {
	}
}
