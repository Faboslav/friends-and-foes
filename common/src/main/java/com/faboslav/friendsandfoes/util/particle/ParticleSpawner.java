package com.faboslav.friendsandfoes.util.particle;

import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;

public final class ParticleSpawner
{
	public static void spawnParticles(
		LivingEntity entity,
		ParticleEffect particleEffect,
		int amount,
		double speed
	) {
		World world = entity.getWorld();

		if (world.isClient()) {
			return;
		}

		ServerWorld serverWorld = (ServerWorld) world;
		Random random = entity.getRandom();

		for (int i = 0; i < amount; i++) {
			serverWorld.spawnParticles(
				particleEffect,
				entity.getParticleX(1.0D),
				entity.getRandomBodyY() + 0.5D,
				entity.getParticleZ(1.0D),
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
