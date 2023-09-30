package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;

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
	public final static Supplier<MemoryModuleType<Integer>> RASCAL_NOD_COOLDOWN;

	static {
		WILDFIRE_BARRAGE_ATTACK_COOLDOWN = RegistryHelper.registerMemoryModuleType("wildfire_barrage_attack_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN = RegistryHelper.registerMemoryModuleType("wildfire_shockwave_attack_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		WILDFIRE_SUMMON_BLAZE_COOLDOWN = RegistryHelper.registerMemoryModuleType("wildfire_summon_blazes_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		TUFF_GOLEM_SLEEP_COOLDOWN = RegistryHelper.registerMemoryModuleType("tuff_golem_sleep_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		RASCAL_NOD_COOLDOWN = RegistryHelper.registerMemoryModuleType("rascal_nod_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	}

	public static void init() {
	}

	private FriendsAndFoesMemoryModuleTypes() {
	}
}
