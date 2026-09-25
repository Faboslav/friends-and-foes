package com.faboslav.friendsandfoes.common.entity.ai.brain.sensor;

import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.sensing.NearestVisibleLivingEntitySensor;
import net.minecraft.world.entity.ai.sensing.Sensor;
import java.util.Set;

//? if >= 1.21.4 {
import net.minecraft.server.level.ServerLevel;
//?}

public class BarnacleAttackablesSensor extends NearestVisibleLivingEntitySensor
{
	public static final float TARGET_DETECTION_DISTANCE = 8.0F;

	@Override
	//? if >= 1.21.4 {
	protected boolean isMatchingEntity(ServerLevel level, LivingEntity barnacle, LivingEntity target)
	//?} else {
	/*protected boolean isMatchingEntity(LivingEntity barnacle, LivingEntity target)
	*///?}
	{
		return target.distanceToSqr(barnacle) <= TARGET_DETECTION_DISTANCE * TARGET_DETECTION_DISTANCE
			   && target.isInWater()
			   && !barnacle.getBrain().hasMemoryValue(MemoryModuleType.HAS_HUNTING_COOLDOWN)
			   && VersionedEntity.isEntityType(target, FriendsAndFoesTags.BARNACLE_PREY)
			   //? if >= 1.21.4 {
			   && Sensor.isEntityAttackable(level, barnacle, target);
			   //?} else {
			   /*&& Sensor.isEntityAttackable(barnacle, target);
			   *///?}
	}

	@Override
	//? if >= 26.1 {
	protected MemoryModuleType<LivingEntity> getMemoryToSet()
	//?} else {
	/*protected MemoryModuleType<LivingEntity> getMemory()
	*///?}
	{
		return MemoryModuleType.NEAREST_ATTACKABLE;
	}

	@Override
	public Set<MemoryModuleType<?>> requires() {
		return Sets.union(super.requires(), ImmutableSet.of(MemoryModuleType.HAS_HUNTING_COOLDOWN));
	}
}
