package com.faboslav.friendsandfoes.forge.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.forge.worldgen.MobSpawnsBiomeModifier;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
public class FriendsAndFoesBiomeModifiers {
	public static final DeferredRegister<Codec<? extends BiomeModifier>> BIOME_MODIFIERS = DeferredRegister.create(ForgeRegistries.Keys.BIOME_MODIFIER_SERIALIZERS, FriendsAndFoes.MOD_ID);

	public static final RegistryObject<Codec<MobSpawnsBiomeModifier>> BIOME_MODIFIER = BIOME_MODIFIERS.register("mob_spawns", () -> MobSpawnsBiomeModifier.CODEC);
}