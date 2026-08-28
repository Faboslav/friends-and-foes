package com.faboslav.friendsandfoes.forge.worldgen;

import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.faboslav.friendsandfoes.forge.init.FriendsAndFoesBiomeModifiers;
import com.mojang.serialization.Codec;
import net.minecraft.core.Holder;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;

public class MobSpawnsBiomeModifier implements BiomeModifier
{
	public static final Codec<MobSpawnsBiomeModifier> CODEC = Codec.unit(MobSpawnsBiomeModifier::new);

	public MobSpawnsBiomeModifier() {
	}

	@Override
	public void modify(Holder<Biome> biome, Phase phase, ModifiableBiomeInfo.BiomeInfo.Builder builder) {
		if (phase == Phase.ADD) {
			AddSpawnBiomeModificationsEvent.EVENT.invoke(new AddSpawnBiomeModificationsEvent((tag, spawnGroup, entityType, spawnWeight, minGroupSize, maxGroupSize) -> {
				if (biome.is(tag)) {
					builder.getMobSpawnSettings().getSpawner(spawnGroup).add(
						new MobSpawnSettings.SpawnerData(
							entityType,
							spawnWeight,
							minGroupSize,
							maxGroupSize
						)
					);
				}
			}));
		}
	}

	@Override
	public Codec<? extends BiomeModifier> codec() {
		return FriendsAndFoesBiomeModifiers.BIOME_MODIFIER.get();
	}
}
