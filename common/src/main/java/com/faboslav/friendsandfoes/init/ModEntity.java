package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.mob.IceologerEntity;
import com.faboslav.friendsandfoes.entity.mob.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import com.faboslav.friendsandfoes.entity.passive.MaulerEntity;
import com.faboslav.friendsandfoes.entity.passive.MoobloomEntity;
import com.faboslav.friendsandfoes.mixin.SpawnRestrictionAccessor;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
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

	public static final RegistrySupplier<EntityType<CopperGolemEntity>> COPPER_GOLEM;
	public static final RegistrySupplier<EntityType<GlareEntity>> GLARE;
	public static final RegistrySupplier<EntityType<IceologerEntity>> ICEOLOGER;
	public static final RegistrySupplier<EntityType<IceologerIceChunkEntity>> ICE_CHUNK;
	public static final RegistrySupplier<EntityType<MaulerEntity>> MAULER;
	public static final RegistrySupplier<EntityType<MoobloomEntity>> MOOBLOOM;

	static {
		COPPER_GOLEM = ENTITY_TYPES.register("copper_golem", () -> EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC).setDimensions(0.75F, 1.375F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("copper_golem")));
		GLARE = ENTITY_TYPES.register("glare", () -> EntityType.Builder.create(GlareEntity::new, SpawnGroup.AMBIENT).setDimensions(0.875F, 1.4375F).maxTrackingRange(8).build(FriendsAndFoes.makeStringID("glare")));
		ICEOLOGER = ENTITY_TYPES.register("iceologer", () -> EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).setDimensions(0.6F, 1.95F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("iceologer")));
		ICE_CHUNK = ENTITY_TYPES.register("ice_chunk", () -> EntityType.Builder.create(IceologerIceChunkEntity::new, SpawnGroup.MISC).setDimensions(2.5F, 1.0F).maxTrackingRange(6).build(FriendsAndFoes.makeStringID("ice_chunk")));
		MAULER = ENTITY_TYPES.register("mauler", () -> EntityType.Builder.create(MaulerEntity::new, SpawnGroup.CREATURE).setDimensions(0.5625F, 0.625F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("mauler")));
		MOOBLOOM = ENTITY_TYPES.register("moobloom", () -> EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.CREATURE).setDimensions(0.9F, 1.4F).maxTrackingRange(10).build(FriendsAndFoes.makeStringID("moobloom")));
	}

	public static void initRegister() {
		ENTITY_TYPES.register();
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
		EntityAttributeRegistry.register(ModEntity.MAULER, MaulerEntity::createRabbitAttributes);
		EntityAttributeRegistry.register(ModEntity.MOOBLOOM, MoobloomEntity::createCowAttributes);
	}

	public static void initSpawnRestrictions() {
		SpawnRestrictionAccessor.callRegister(GLARE.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ICEOLOGER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MAULER.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MaulerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM.get(), SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
	}

	public static void initBiomeModifications() {
		Predicate<BiomeModifications.BiomeContext> DESERT = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.DESERT.getValue());
		Predicate<BiomeModifications.BiomeContext> FLOWER_FOREST = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.FLOWER_FOREST.getValue());
		Predicate<BiomeModifications.BiomeContext> LUSH_CAVES = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.LUSH_CAVES.getValue());
		Predicate<BiomeModifications.BiomeContext> MEADOW = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.MEADOW.getValue());

		var config = FriendsAndFoes.getConfig();

		if (config.enableGlareSpawn) {
			registerBiomeModification(LUSH_CAVES, GLARE.get(), SpawnGroup.AMBIENT, config.glareSpawnWeight, config.glareSpawnMinGroupSize, config.glareSpawnMaxGroupSize);
		}

		if (config.enableMaulerSpawn) {
			registerBiomeModification(DESERT, MAULER.get(), SpawnGroup.CREATURE, config.maulerpawnWeight, config.maulerSpawnMinGroupSize, config.maulerSpawnMaxGroupSize);
		}

		if (config.enableMoobloomSpawn) {
			registerBiomeModification(FLOWER_FOREST, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomSpawnWeight, config.moobloomSpawnMinGroupSize, config.moobloomSpawnMaxGroupSize);
			registerBiomeModification(MEADOW, MOOBLOOM.get(), SpawnGroup.CREATURE, config.moobloomSpawnWeight, config.moobloomSpawnMinGroupSize, config.moobloomSpawnMaxGroupSize);
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
