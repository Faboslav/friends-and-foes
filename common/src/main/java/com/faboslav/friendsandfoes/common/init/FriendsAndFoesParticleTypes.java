package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.particle.FreezingTotemParticle;
import com.faboslav.friendsandfoes.common.client.particle.IllusionTotemParticle;
import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import com.teamresourceful.resourcefullib.common.registry.RegistryEntry;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistries;
import com.teamresourceful.resourcefullib.common.registry.ResourcefulRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.registries.BuiltInRegistries;

/**
 * @see ParticleTypes
 */
public final class FriendsAndFoesParticleTypes
{
	public static final ResourcefulRegistry<ParticleType<?>> PARTICLE_TYPES = ResourcefulRegistries.create(BuiltInRegistries.PARTICLE_TYPE, FriendsAndFoes.MOD_ID);

	public static final RegistryEntry<SimpleParticleType> TOTEM_OF_FREEZING = PARTICLE_TYPES.register("totem_of_freezing", () -> new SimpleParticleType(false));
	public static final RegistryEntry<SimpleParticleType> TOTEM_OF_ILLUSION = PARTICLE_TYPES.register("totem_of_illusion", () -> new SimpleParticleType(false));

	@Environment(EnvType.CLIENT)
	public static void registerParticlesEvent(RegisterParticlesEvent event) {
		event.register(TOTEM_OF_FREEZING.get(), FreezingTotemParticle.Factory::new);
		event.register(TOTEM_OF_ILLUSION.get(), IllusionTotemParticle.Factory::new);
	}

	private FriendsAndFoesParticleTypes() {
	}
}
