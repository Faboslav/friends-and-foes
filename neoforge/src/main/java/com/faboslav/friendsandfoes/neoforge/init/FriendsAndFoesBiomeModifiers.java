package com.faboslav.friendsandfoes.neoforge.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.neoforge.worldgen.MobSpawnsBiomeModifier;
import com.mojang.serialization.MapCodec;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class FriendsAndFoesBiomeModifiers
{
	public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FriendsAndFoes.MOD_ID);

	public static final DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<MobSpawnsBiomeModifier>> BIOME_MODIFIER = BIOME_MODIFIERS.register("mob_spawns", () -> MobSpawnsBiomeModifier.CODEC);
}