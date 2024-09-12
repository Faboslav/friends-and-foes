package com.faboslav.friendsandfoes.neoforge.worldgen;

import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.neoforge.init.FriendsAndFoesBiomeModifiers;
import com.mojang.serialization.Codec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public class MobSpawnsBiomeModifier implements BiomeModifier
{
	public static final Codec<MobSpawnsBiomeModifier> CODEC = Codec.unit(MobSpawnsBiomeModifier::new);

	public MobSpawnsBiomeModifier() {
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

	public Codec<? extends BiomeModifier> codec() {
		return FriendsAndFoesBiomeModifiers.BIOME_MODIFIER.get();
	}
}