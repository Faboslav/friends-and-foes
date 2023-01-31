package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @see MemoryModuleType
 */
public final class FriendsAndFoesMemoryModuleTypes
{
	public final static Supplier<MemoryModuleType<Integer>> WILDFIRE_BARRAGE_ATTACK_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> WILDFIRE_SUMMON_BLAZE_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> TUFF_GOLEM_SLEEP_COOLDOWN;
	public final static Supplier<MemoryModuleType<GlobalPos>> TUFF_GOLEM_SLEEP_POSITION;
	public final static Supplier<MemoryModuleType<Vec3d>> TUFF_GOLEM_SLEEP_ROTATION;

	static {
		WILDFIRE_BARRAGE_ATTACK_COOLDOWN = RegistryHelper.registerMemoryModuleType("wildfire_barrage_attack_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN = RegistryHelper.registerMemoryModuleType("wildfire_shockwave_attack_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		WILDFIRE_SUMMON_BLAZE_COOLDOWN = RegistryHelper.registerMemoryModuleType("wildfire_summon_blazes_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		TUFF_GOLEM_SLEEP_COOLDOWN = RegistryHelper.registerMemoryModuleType("tuff_golem_sleep_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		TUFF_GOLEM_SLEEP_POSITION = RegistryHelper.registerMemoryModuleType("tuff_golem_sleep_position", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
		TUFF_GOLEM_SLEEP_ROTATION = RegistryHelper.registerMemoryModuleType("tuff_golem_sleep_rotation", () -> new MemoryModuleType<>(Optional.of(Vec3d.CODEC)));
	}

	public static void init() {
	}

	private FriendsAndFoesMemoryModuleTypes() {
	}
}
