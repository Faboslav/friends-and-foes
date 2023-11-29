package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.entity.ai.brain.sensor.CopperGolemSpecificSensor;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.entity.ai.brain.sensor.SensorType;
import net.minecraft.entity.ai.brain.sensor.TemptationsSensor;

import java.util.function.Supplier;

/**
 * @see net.minecraft.entity.ai.brain.sensor.SensorType
 */
public final class FriendsAndFoesMemorySensorType
{
	public static final Supplier<SensorType<TemptationsSensor>> COPPER_GOLEM_TEMPTATIONS;
	public static final Supplier<SensorType<CopperGolemSpecificSensor>> COPPER_GOLEM_SPECIFIC_SENSOR;

	static {
		COPPER_GOLEM_TEMPTATIONS = RegistryHelper.registerSensorType("copper_golem_temptations", () -> new SensorType<>(() -> {
			return new TemptationsSensor(CopperGolemBrain.getTemptItems());
		}));
		COPPER_GOLEM_SPECIFIC_SENSOR = RegistryHelper.registerSensorType("copper_golem_specific_sensor", () -> new SensorType<>(() -> {
			return new CopperGolemSpecificSensor();
		}));
	}

	public static void init() {
	}

	private FriendsAndFoesMemorySensorType() {
	}
}
