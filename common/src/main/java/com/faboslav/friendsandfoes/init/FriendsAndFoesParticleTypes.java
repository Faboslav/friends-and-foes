package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.particle.SimpleParticleType;

/**
 * @see ParticleTypes
 */
public final class FriendsAndFoesParticleTypes
{
	public static final SimpleParticleType TOTEM_OF_FREEZING = new SimpleParticleType(false);
	public static final SimpleParticleType TOTEM_OF_ILLUSION = new SimpleParticleType(false);

	public static void init() {
	}

	private FriendsAndFoesParticleTypes() {
	}

	static {
		RegistryHelper.registerParticleType("totem_of_freezing", TOTEM_OF_FREEZING);
		RegistryHelper.registerParticleType("totem_of_illusion", TOTEM_OF_ILLUSION);
	}

}
