package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.init.registry.ResourcefulRegistry;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.util.registry.Registry;

import java.util.Optional;

/**
 * @see MemoryModuleType
 */
public final class FriendsAndFoesMemoryModuleTypes
{
	public static final ResourcefulRegistry<MemoryModuleType<?>> MEMORY_MODULE_TYPES = ResourcefulRegistries.create(Registry.MEMORY_MODULE_TYPE, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<MemoryModuleType<Boolean>> COPPER_GOLEM_IS_OXIDIZED = MEMORY_MODULE_TYPES.register("copper_golem_is_oxidized", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
	public static final RegistryEntry<MemoryModuleType<GlobalPos>> COPPER_GOLEM_BUTTON_POS = MEMORY_MODULE_TYPES.register("copper_golem_button_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
	public static final RegistryEntry<MemoryModuleType<Integer>> COPPER_GOLEM_SPIN_HEAD_COOLDOWN = MEMORY_MODULE_TYPES.register("copper_golem_spin_head_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<GlobalPos>> GLARE_DARK_SPOT_POS = MEMORY_MODULE_TYPES.register("glare_dark_spot_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
	public static final RegistryEntry<MemoryModuleType<GlobalPos>> GLARE_GLOW_BERRIES_POS = MEMORY_MODULE_TYPES.register("glare_glow_berries_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
	public static final RegistryEntry<MemoryModuleType<Boolean>> GLARE_IS_IDLE = MEMORY_MODULE_TYPES.register("glare_is_idle", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
	public static final RegistryEntry<MemoryModuleType<Boolean>> GLARE_IS_TAMED = MEMORY_MODULE_TYPES.register("glare_is_tamed", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
	public static final RegistryEntry<MemoryModuleType<Integer>> GLARE_DARK_SPOT_LOCATING_COOLDOWN = MEMORY_MODULE_TYPES.register("glare_dark_spot_locating_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> GLARE_LOCATING_GLOW_BERRIES_COOLDOWN = MEMORY_MODULE_TYPES.register("glare_locating_glow_berries_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> COPPER_GOLEM_PRESS_BUTTON_COOLDOWN = MEMORY_MODULE_TYPES.register("copper_golem_press_button_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> CRAB_WAVE_COOLDOWN = MEMORY_MODULE_TYPES.register("crab_wave_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> WILDFIRE_BARRAGE_ATTACK_COOLDOWN = MEMORY_MODULE_TYPES.register("wildfire_barrage_attack_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN = MEMORY_MODULE_TYPES.register("wildfire_shockwave_attack_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> WILDFIRE_SUMMON_BLAZE_COOLDOWN = MEMORY_MODULE_TYPES.register("wildfire_summon_blazes_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> TUFF_GOLEM_SLEEP_COOLDOWN = MEMORY_MODULE_TYPES.register("tuff_golem_sleep_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
	public static final RegistryEntry<MemoryModuleType<Integer>> RASCAL_NOD_COOLDOWN = MEMORY_MODULE_TYPES.register("rascal_nod_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));

	private FriendsAndFoesMemoryModuleTypes() {
	}
}
