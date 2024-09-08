package com.faboslav.friendsandfoes.common.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.AnimatedParticle;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;

@Environment(EnvType.CLIENT)
public final class IllusionTotemParticle extends AnimatedParticle
{
	IllusionTotemParticle(
		ClientWorld world,
		double x,
		double y,
		double z,
		double velocityX,
		double velocityY,
		double velocityZ,
		SpriteProvider spriteProvider
	) {
		super(world, x, y, z, spriteProvider, 1.25F);
		this.velocityMultiplier = 0.6F;
		this.velocityX = velocityX;
		this.velocityY = velocityY;
		this.velocityZ = velocityZ;
		this.scale *= 0.75F;
		this.maxAge = 60 + this.random.nextInt(12);
		this.setSpriteForAge(spriteProvider);

		if (this.random.nextInt(4) == 0) {
			this.setColor(0.376F + this.random.nextFloat() * 0.05F, 0.243F + this.random.nextFloat() * 0.05F, 0.361F + this.random.nextFloat() * 0.05F);
		} else {
			this.setColor(0.086F + this.random.nextFloat() * 0.05F, 0.043F + this.random.nextFloat() * 0.05F, 0.075F + this.random.nextFloat() * 0.05F);
		}
	}

	@Environment(EnvType.CLIENT)
	public static class Factory implements ParticleFactory<DefaultParticleType>
	{
		private final SpriteProvider spriteProvider;

		public Factory(SpriteProvider spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(
			DefaultParticleType defaultParticleType,
			ClientWorld clientWorld,
			double d,
			double e,
			double f,
			double g,
			double h,
			double i
		) {
			return new IllusionTotemParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
		}
	}
}
