package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.config.FriendsAndFoesConfig;
import com.faboslav.friendsandfoes.entity.mob.IceologerEntity;
import com.faboslav.friendsandfoes.entity.mob.IceologerIceChunkEntity;
import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.entity.passive.GlareEntity;
import com.faboslav.friendsandfoes.entity.passive.MoobloomEntity;
import com.faboslav.friendsandfoes.mixin.SpawnRestrictionAccessor;
import dev.architectury.registry.level.biome.BiomeModifications;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.entity.Entity;
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
@SuppressWarnings({"rawtypes", "unchecked"})
public final class ModEntity
{
	public static final DeferredRegister<EntityType<?>> ENTITY = DeferredRegister.create(FriendsAndFoes.MOD_ID, Registry.ENTITY_TYPE_KEY);

	public static final EntityType<CopperGolemEntity> COPPER_GOLEM;
	public static final EntityType<GlareEntity> GLARE;
	public static final EntityType<IceologerEntity> ICEOLOGER;
	public static final EntityType<IceologerIceChunkEntity> ICE_CHUNK;
	public static final EntityType<MoobloomEntity> MOOBLOOM;

	static {
		COPPER_GOLEM = register("copper_golem", EntityType.Builder.create(CopperGolemEntity::new, SpawnGroup.MISC).setDimensions(0.75F, 1.375F));
		GLARE = register("glare", EntityType.Builder.create(GlareEntity::new, SpawnGroup.AMBIENT).setDimensions(0.875F, 1.4375F));
		ICEOLOGER = register("iceologer", EntityType.Builder.create(IceologerEntity::new, SpawnGroup.MONSTER).setDimensions(0.6F, 1.95F).maxTrackingRange(8));
		ICE_CHUNK = register("ice_chunk", EntityType.Builder.create(IceologerIceChunkEntity::new, SpawnGroup.MISC).setDimensions(2.5F, 1.0F));
		MOOBLOOM = register("moobloom", EntityType.Builder.create(MoobloomEntity::new, SpawnGroup.CREATURE).setDimensions(0.9F, 1.4F));
	}

	public static void init() {
		initMobAttributes();
		initSpawnRestrictions();
		initBiomeModifications();
		ENTITY.register();
	}

	private static void initMobAttributes() {
		EntityAttributeRegistry.register(() -> ModEntity.COPPER_GOLEM, CopperGolemEntity::createAttributes);
		EntityAttributeRegistry.register(() -> ModEntity.GLARE, GlareEntity::createAttributes);
		EntityAttributeRegistry.register(() -> ModEntity.ICEOLOGER, IceologerEntity::createAttributes);
		EntityAttributeRegistry.register(() -> ModEntity.MOOBLOOM, MoobloomEntity::createCowAttributes);
	}

	private static void initSpawnRestrictions() {
		SpawnRestrictionAccessor.callRegister(GLARE, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, GlareEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(ICEOLOGER, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, IceologerEntity::canSpawn);
		SpawnRestrictionAccessor.callRegister(MOOBLOOM, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, MoobloomEntity::canSpawn);
	}

	private static void initBiomeModifications() {
		Predicate<BiomeModifications.BiomeContext> LUSH_CAVES = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.LUSH_CAVES.getValue());
		Predicate<BiomeModifications.BiomeContext> FLOWER_FOREST = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.FLOWER_FOREST.getValue());
		Predicate<BiomeModifications.BiomeContext> MEADOW = (ctx) -> Objects.equals(ctx.getKey(), BiomeKeys.MEADOW.getValue());

		if (FriendsAndFoesConfig.enableGlareSpawn) {
			registerBiomeModification(LUSH_CAVES, GLARE, SpawnGroup.MISC, FriendsAndFoesConfig.glareSpawnWeight, FriendsAndFoesConfig.glareSpawnMinGroupSize, FriendsAndFoesConfig.glareSpawnMaxGroupSize);
		}

		if (FriendsAndFoesConfig.enableMoobloomSpawn) {
			registerBiomeModification(FLOWER_FOREST, MOOBLOOM, SpawnGroup.CREATURE, FriendsAndFoesConfig.moobloomSpawnWeight, FriendsAndFoesConfig.moobloomSpawnMinGroupSize, FriendsAndFoesConfig.moobloomSpawnMaxGroupSize);
			registerBiomeModification(MEADOW, MOOBLOOM, SpawnGroup.CREATURE, FriendsAndFoesConfig.moobloomSpawnWeight, FriendsAndFoesConfig.moobloomSpawnMinGroupSize, FriendsAndFoesConfig.moobloomSpawnMaxGroupSize);
		}
	}

	private static <T extends Entity> EntityType<T> register(
		String name,
		EntityType.Builder<T> type
	) {
		var entityType = type.build(name);

		ENTITY.register(name, () -> entityType);

		return entityType;
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
}
