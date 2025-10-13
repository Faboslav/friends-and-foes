package com.faboslav.friendsandfoes.common.client.particle;

import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.util.RandomSource;

public final class FreezingTotemParticle extends SimpleAnimatedParticle
{
	FreezingTotemParticle(
		ClientLevel world,
		double x,
		double y,
		double z,
		double velocityX,
		double velocityY,
		double velocityZ,
		SpriteSet spriteProvider
	) {
		super(world, x, y, z, spriteProvider, 1.25F);
		this.friction = 0.6F;
		this.xd = velocityX;
		this.yd = velocityY;
		this.zd = velocityZ;
		this.quadSize *= 0.75F;
		this.lifetime = 60 + this.random.nextInt(12);
		this.setSpriteFromAge(spriteProvider);

		if (this.random.nextInt(4) == 0) {
			this.setColor(0.055F + this.random.nextFloat() * 0.05F, 0.153F + this.random.nextFloat() * 0.05F, 0.325F + this.random.nextFloat() * 0.05F);
		} else {
			this.setColor(0.09F + this.random.nextFloat() * 0.05F, 0.22F + this.random.nextFloat() * 0.05F, 0.451F + this.random.nextFloat() * 0.05F);
		}
	}

	public static class Factory implements ParticleProvider<SimpleParticleType>
	{
		private final SpriteSet spriteProvider;

		public Factory(SpriteSet spriteProvider) {
			this.spriteProvider = spriteProvider;
		}

		public Particle createParticle(
			SimpleParticleType defaultParticleType,
			ClientLevel clientWorld,
			double d,
			double e,
			double f,
			double g,
			double h,
			double i
			//? if >=1.21.9 {
			/*, RandomSource randomSource
			*///?}
		) {
			return new FreezingTotemParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
		}
	}
}
