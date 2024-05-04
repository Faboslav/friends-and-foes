package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import com.mojang.serialization.Codec;
import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.util.math.GlobalPos;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * @see MemoryModuleType
 */
public final class FriendsAndFoesMemoryModuleTypes
{
	public static final Supplier<MemoryModuleType<Boolean>> COPPER_GOLEM_IS_OXIDIZED;
	public static final Supplier<MemoryModuleType<GlobalPos>> COPPER_GOLEM_BUTTON_POS;
	public final static Supplier<MemoryModuleType<Integer>> COPPER_GOLEM_SPIN_HEAD_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> COPPER_GOLEM_PRESS_BUTTON_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> CRAB_WAVE_COOLDOWN;
	public static final Supplier<MemoryModuleType<GlobalPos>> GLARE_DARK_SPOT_POS;
	public static final Supplier<MemoryModuleType<GlobalPos>> GLARE_GLOW_BERRIES_POS;
	public static final Supplier<MemoryModuleType<Boolean>> GLARE_IS_IDLE;
	public static final Supplier<MemoryModuleType<Boolean>> GLARE_IS_TAMED;
	public final static Supplier<MemoryModuleType<Integer>> GLARE_DARK_SPOT_LOCATING_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> GLARE_LOCATING_GLOW_BERRIES_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> WILDFIRE_BARRAGE_ATTACK_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> WILDFIRE_SHOCKWAVE_ATTACK_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> WILDFIRE_SUMMON_BLAZE_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> TUFF_GOLEM_SLEEP_COOLDOWN;
	public final static Supplier<MemoryModuleType<Integer>> RASCAL_NOD_COOLDOWN;

	static {
		COPPER_GOLEM_IS_OXIDIZED = RegistryHelper.registerMemoryModuleType("copper_golem_is_oxidized", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
		COPPER_GOLEM_BUTTON_POS = RegistryHelper.registerMemoryModuleType("copper_golem_button_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
		COPPER_GOLEM_SPIN_HEAD_COOLDOWN = RegistryHelper.registerMemoryModuleType("copper_golem_spin_head_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		GLARE_DARK_SPOT_POS = RegistryHelper.registerMemoryModuleType("glare_dark_spot_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
		GLARE_GLOW_BERRIES_POS = RegistryHelper.registerMemoryModuleType("glare_glow_berries_pos", () -> new MemoryModuleType<>(Optional.of(GlobalPos.CODEC)));
		GLARE_IS_IDLE = RegistryHelper.registerMemoryModuleType("glare_is_idle", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
		GLARE_IS_TAMED = RegistryHelper.registerMemoryModuleType("glare_is_tamed", () -> new MemoryModuleType<>(Optional.of(Codec.BOOL)));
		GLARE_DARK_SPOT_LOCATING_COOLDOWN = RegistryHelper.registerMemoryModuleType("glare_dark_spot_locating_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		GLARE_LOCATING_GLOW_BERRIES_COOLDOWN = RegistryHelper.registerMemoryModuleType("glare_locating_glow_berries_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		COPPER_GOLEM_PRESS_BUTTON_COOLDOWN = RegistryHelper.registerMemoryModuleType("copper_golem_press_button_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
		CRAB_WAVE_COOLDOWN = RegistryHelper.registerMemoryModuleType("crab_wave_cooldown", () -> new MemoryModuleType<>(Optional.of(Codec.INT)));
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
