package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.BarnacleAttackableSensor;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.BarnacleSpecificSensor;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.CopperGolemSpecificSensor;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.GlareSpecificSensor;
import com.faboslav.friendsandfoes.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.init.registry.ResourcefulRegistry;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;
import net.minecraft.util.registry.Registry;

/**
 * @see net.minecraft.entity.ai.brain.sensor.SensorType
 */
public final class FriendsAndFoesSensorTypes
{
	public static final ResourcefulRegistry<SensorType<?>> SENSOR_TYPES = ResourcefulRegistries.create(Registry.SENSOR_TYPE, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<SensorType<BarnacleSpecificSensor>> BARNACLE_SPECIFIC_SENSOR = SENSOR_TYPES.register("barnacle_specific_sensor", () -> new SensorType<>(() -> {
		return new BarnacleSpecificSensor();
	}));
	public static final RegistryEntry<SensorType<BarnacleAttackableSensor>> BARNACLE_ATTACKABLE_SENSOR = SENSOR_TYPES.register("barnacle_attackable_sensor", () -> new SensorType<>(() -> {
		return new BarnacleAttackableSensor();
	}));
	public static final RegistryEntry<SensorType<TemptationsSensor>> COPPER_GOLEM_TEMPTATIONS = SENSOR_TYPES.register("copper_golem_temptations", () -> new SensorType<>(() -> {
		return new TemptationsSensor(CopperGolemBrain.getTemptItems());
	}));
	public static final RegistryEntry<SensorType<CopperGolemSpecificSensor>> COPPER_GOLEM_SPECIFIC_SENSOR = SENSOR_TYPES.register("copper_golem_specific_sensor", () -> new SensorType<>(() -> {
		return new CopperGolemSpecificSensor();
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
