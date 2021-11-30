package com.faboslav.friendsandfoes.registry;

import com.faboslav.friendsandfoes.config.Settings;
import com.faboslav.friendsandfoes.entity.passive.CopperGolemEntity;
import com.faboslav.friendsandfoes.entity.passive.MoobloomEntity;
import com.faboslav.friendsandfoes.mixin.SpawnRestrictionAccessor;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectionContext;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.*;
import net.minecraft.entity.SpawnRestriction.SpawnPredicate;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.Heightmap;
import net.minecraft.world.biome.BiomeKeys;

import java.util.function.Predicate;

@SuppressWarnings("deprecation")
public class EntityRegistry
{
    private static final Predicate<BiomeSelectionContext> flowerForest = BiomeSelectors.includeByKey(BiomeKeys.FLOWER_FOREST);
    private static final SpawnRestriction.Location onGround = SpawnRestriction.Location.ON_GROUND;
    private static final Heightmap.Type motionBlocking = Heightmap.Type.MOTION_BLOCKING_NO_LEAVES;
    public static final EntityType<MoobloomEntity> MOOBLOOM = registerMoobloom();
    public static final EntityType<CopperGolemEntity> COPPER_GOLEM = registerCopperGolem();

    private static EntityType<CopperGolemEntity> registerCopperGolem() {
        EntityType<CopperGolemEntity> copperGolem = registerEntity(
                "copper_golem",
                CopperGolemEntity::new,
                CopperGolemEntity.createCopperGolemAttributes(),
                SpawnGroup.MISC,
                0.75F,
                1.375F
        );

        return copperGolem;
    }

    private static EntityType<MoobloomEntity> registerMoobloom() {
        SpawnGroup spawnGroup = SpawnGroup.CREATURE;

        // Register entity
        EntityType<MoobloomEntity> moobloom = registerEntity(
                "moobloom",
                MoobloomEntity::new,
                MoobloomEntity.createCowAttributes(),
                spawnGroup,
                0.9F,
                1.4F
        );

        // Register spawn restriction
        addSpawnRestriction(
                MOOBLOOM,
                MoobloomEntity::canSpawn
        );

        // Register biomes to spawn in
        addSpawn(
                flowerForest,
                spawnGroup,
                moobloom,
                32,
                2,
                4
        );

        return moobloom;
    }

    private static <T extends LivingEntity> EntityType<T> registerEntity(
            String name,
            EntityType.EntityFactory<T> entity,
            DefaultAttributeContainer.Builder attributes,
            SpawnGroup group,
            float width,
            float height
    ) {
        EntityType<T> type = FabricEntityTypeBuilder.create(group, entity).dimensions(EntityDimensions.fixed(width, height)).build();
        FabricDefaultAttributeRegistry.register(type, attributes);

        return Registry.register(
                Registry.ENTITY_TYPE,
                Settings.makeID(name),
                type
        );
    }

    public static <T extends MobEntity> void addSpawnRestriction(
            EntityType<T> entity,
            SpawnPredicate<T> predicate
    ) {
        SpawnRestrictionAccessor.invokeRegister(
                entity,
                onGround,
                motionBlocking,
                predicate
        );
    }

    public static void addSpawn(
            Predicate<BiomeSelectionContext> biomeSelector,
            SpawnGroup spawnGroup,
            EntityType<?> entityType,
            int weight,
            int minGroupSize,
            int maxGroupSize
    ) {
        BiomeModifications.addSpawn(
                biomeSelector,
                spawnGroup,
                entityType,
                weight,
                minGroupSize,
                maxGroupSize
        );
    }

    public static void init() {
    }
}