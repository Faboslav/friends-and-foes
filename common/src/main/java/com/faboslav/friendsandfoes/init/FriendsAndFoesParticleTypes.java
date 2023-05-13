package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.particle.ParticleTypes;

/**
 * @see ParticleTypes
 */
public final class FriendsAndFoesParticleTypes
{
	public static final DefaultParticleType TOTEM_OF_FREEZING = new DefaultParticleType(false);
	public static final DefaultParticleType TOTEM_OF_ILLUSION = new DefaultParticleType(false);

	public static void init() {
	}

	private FriendsAndFoesParticleTypes() {
	}

	static {
		RegistryHelper.registerParticleType("totem_of_freezing", TOTEM_OF_FREEZING);
		RegistryHelper.registerParticleType("totem_of_illusion", TOTEM_OF_ILLUSION);
	}

}
