package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.client.particle.FreezingTotemParticle;
import com.faboslav.friendsandfoes.common.client.particle.IllusionTotemParticle;
import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import com.faboslav.friendsandfoes.common.init.registry.RegistryEntry;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistries;
import com.faboslav.friendsandfoes.common.init.registry.ResourcefulRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.particle.ParticleType;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.registry.Registries;

/**
 * @see ParticleTypes
 */
public final class FriendsAndFoesParticleTypes
{
	public static final ResourcefulRegistry<ParticleType<?>> PARTICLE_TYPES = ResourcefulRegistries.create(Registries.PARTICLE_TYPE, FriendsAndFoes.MOD_ID);

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
