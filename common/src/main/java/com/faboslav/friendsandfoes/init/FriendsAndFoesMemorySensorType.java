package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.BarnacleAttackableSensor;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.BarnacleSpecificSensor;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.CopperGolemSpecificSensor;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.GlareSpecificSensor;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;

import java.util.function.Supplier;

/**
 * @see net.minecraft.entity.ai.brain.sensor.SensorType
 */
public final class FriendsAndFoesMemorySensorType
{
	public static final Supplier<SensorType<BarnacleSpecificSensor>> BARNACLE_SPECIFIC_SENSOR;
	public static final Supplier<SensorType<BarnacleAttackableSensor>> BARNACLE_ATTACKABLE_SENSOR;
	public static final Supplier<SensorType<TemptationsSensor>> COPPER_GOLEM_TEMPTATIONS;
	public static final Supplier<SensorType<CopperGolemSpecificSensor>> COPPER_GOLEM_SPECIFIC_SENSOR;
	public static final Supplier<SensorType<TemptationsSensor>> GLARE_TEMPTATIONS;
	public static final Supplier<SensorType<GlareSpecificSensor>> GLARE_SPECIFIC_SENSOR;

	static {
		BARNACLE_SPECIFIC_SENSOR = RegistryHelper.registerSensorType("barnacle_specific_sensor", () -> new SensorType<>(() -> {
			return new BarnacleSpecificSensor();
		}));
		BARNACLE_ATTACKABLE_SENSOR = RegistryHelper.registerSensorType("barnacle_attackable_sensor", () -> new SensorType<>(() -> {
			return new BarnacleAttackableSensor();
		}));
		COPPER_GOLEM_TEMPTATIONS = RegistryHelper.registerSensorType("copper_golem_temptations", () -> new SensorType<>(() -> {
			return new TemptationsSensor(CopperGolemBrain.getTemptItems());
		}));
		COPPER_GOLEM_SPECIFIC_SENSOR = RegistryHelper.registerSensorType("copper_golem_specific_sensor", () -> new SensorType<>(() -> {
			return new CopperGolemSpecificSensor();
		}));
		GLARE_TEMPTATIONS = RegistryHelper.registerSensorType("glare_temptations", () -> new SensorType<>(() -> {
			return new TemptationsSensor(GlareBrain.getTemptItems());
		}));
		GLARE_SPECIFIC_SENSOR = RegistryHelper.registerSensorType("glare_specific_sensor", () -> new SensorType<>(() -> {
			return new GlareSpecificSensor();
		}));
	}

	public static void init() {
	}

	private FriendsAndFoesMemorySensorType() {
	}
}
