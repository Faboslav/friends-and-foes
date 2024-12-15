package com.faboslav.friendsandfoes.neoforge.worldgen;

import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.neoforge.init.FriendsAndFoesBiomeModifiers;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.neoforged.neoforge.common.world.BiomeModifier;
import net.neoforged.neoforge.common.world.ModifiableBiomeInfo;

public class MobSpawnsBiomeModifier implements BiomeModifier
{
	public static final MapCodec<MobSpawnsBiomeModifier> CODEC = MapCodec.unit(MobSpawnsBiomeModifier::new);

	public MobSpawnsBiomeModifier() {
	}

	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			AddSpawnBiomeModificationsEvent.EVENT.invoke(new AddSpawnBiomeModificationsEvent((tag, spawnGroup, entityType, spawnWeight, minGroupSize, maxGroupSize) -> {
				if (biome.is(tag)) {
					builder.getMobSpawnSettings().getSpawner(spawnGroup).add(new MobSpawnSettings.SpawnerData(entityType, spawnWeight, minGroupSize, maxGroupSize));
				}
			}));
		}
	}

	public MapCodec<? extends BiomeModifier> codec() {
		return FriendsAndFoesBiomeModifiers.BIOME_MODIFIER.get();
	}
}