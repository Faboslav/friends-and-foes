package com.faboslav.friendsandfoes.neoforge.world;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public class MobSpawnBiomeModifier implements BiomeModifier
{
	public static final DeferredRegister<MapCodec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FriendsAndFoes.MOD_ID);
	DeferredHolder<MapCodec<? extends BiomeModifier>, MapCodec<MobSpawnBiomeModifier>> SERIALIZER = BIOME_MODIFIER_SERIALIZERS.register("mob_spawns", () -> MapCodec.unit(MobSpawnBiomeModifier::new));

	public MobSpawnBiomeModifier() {
	}

	public void modify(RegistryEntry<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			AddSpawnBiomeModificationsEvent.EVENT.invoke(new AddSpawnBiomeModificationsEvent((tag, spawnGroup, entityType, spawnWeight, minGroupSize, maxGroupSize) -> {
				if (biome.isIn(tag)) {
					builder.getMobSpawnSettings().getSpawner(spawnGroup).add(new SpawnSettings.SpawnEntry(entityType, spawnWeight, minGroupSize, maxGroupSize));
				}
			}));
		}
	}

	public MapCodec<? extends BiomeModifier> codec() {
		return SERIALIZER.get();
	}

	public static MapCodec<MobSpawnBiomeModifier> makeCodec() {
		return MapCodec.unit(MobSpawnBiomeModifier::new);
	}
}