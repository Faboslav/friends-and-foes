package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CrabBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.sensor.CopperGolemSpecificSensor;
import com.faboslav.friendsandfoes.common.entity.ai.brain.sensor.GlareSpecificSensor;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.registry.Registries;

/**
 * @see net.minecraft.entity.ai.brain.sensor.SensorType
 */
public final class FriendsAndFoesSensorTypes
{
	public static final ResourcefulRegistry<SensorType<?>> SENSOR_TYPES = ResourcefulRegistries.create(Registries.SENSOR_TYPE, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<SensorType<TemptationsSensor>> COPPER_GOLEM_TEMPTATIONS = SENSOR_TYPES.register("copper_golem_temptations", () -> new SensorType<>(() -> {
		return new TemptationsSensor(CopperGolemBrain.getTemptItems());
	}));
	public static final RegistryEntry<SensorType<CopperGolemSpecificSensor>> COPPER_GOLEM_SPECIFIC_SENSOR = SENSOR_TYPES.register("copper_golem_specific_sensor", () -> new SensorType<>(() -> {
		return new CopperGolemSpecificSensor();
	}));
	public static final RegistryEntry<SensorType<TemptationsSensor>> CRAB_TEMPTATIONS = SENSOR_TYPES.register("crab_temptations", () -> new SensorType<>(() -> {
		return new TemptationsSensor(CrabBrain.getTemptItems());
	}));
	public static final RegistryEntry<SensorType<TemptationsSensor>> GLARE_TEMPTATIONS = SENSOR_TYPES.register("glare_temptations", () -> new SensorType<>(() -> {
		return new TemptationsSensor(GlareBrain.getTemptItems());
	}));
	public static final RegistryEntry<SensorType<GlareSpecificSensor>> GLARE_SPECIFIC_SENSOR = SENSOR_TYPES.register("glare_specific_sensor", () -> new SensorType<>(() -> {
		return new GlareSpecificSensor();
	}));

	private FriendsAndFoesSensorTypes() {
	}
}
