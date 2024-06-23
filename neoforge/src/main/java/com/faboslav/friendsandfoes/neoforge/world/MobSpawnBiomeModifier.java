package com.faboslav.friendsandfoes.neoforge.world;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.events.lifecycle.AddSpawnBiomeModificationsEvent;
import com.mojang.serialization.Codec;
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
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIER_SERIALIZERS =
		DeferredRegister.create(NeoForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FriendsAndFoes.MOD_ID);

	DeferredHolder<Codec<? extends BiomeModifier>, Codec<MobSpawnBiomeModifier>> SERIALIZER = BIOME_MODIFIER_SERIALIZERS.register("mob_spawns", () -> Codec.unit(MobSpawnBiomeModifier::new));

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