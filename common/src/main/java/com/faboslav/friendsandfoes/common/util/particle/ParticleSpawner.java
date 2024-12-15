package com.faboslav.friendsandfoes.common.util.particle;

import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.level.Level;

public final class ParticleSpawner
{
	public static void spawnParticles(
		LivingEntity entity,
		ParticleOptions particleEffect,
		int amount,
		double speed
	) {
		Level world = entity.level();

		if (world.isClientSide()) {
			return;
		}

		ServerLevel serverWorld = (ServerLevel) world;
		RandomSource random = entity.getRandom();

		for (int i = 0; i < amount; i++) {
			serverWorld.sendParticles(
				particleEffect,
				entity.getRandomX(1.0D),
				entity.getRandomY() + 0.5D,
				entity.getRandomZ(1.0D),
				1,
				random.nextGaussian() * 0.02D,
				random.nextGaussian() * 0.02D,
				random.nextGaussian() * 0.02D,
				speed
			);
		}
	}

	private ParticleSpawner() {
	}
}
