package com.faboslav.friendsandfoes.forge.world;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.mojang.serialization.Codec;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.SpawnSettings;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.common.world.ModifiableBiomeInfo;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MobSpawnBiomeModifier implements BiomeModifier
{
	private static final RegistryObject<Codec<? extends BiomeModifier>> SERIALIZER = RegistryObject.create(FriendsAndFoes.makeID("faf_mob_spawns"), ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FriendsAndFoes.MOD_ID);

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

	public Codec<? extends BiomeModifier> codec() {
		return SERIALIZER.get();
	}

	public static Codec<MobSpawnBiomeModifier> makeCodec() {
		return Codec.unit(MobSpawnBiomeModifier::new);
	}
}
