package com.faboslav.friendsandfoes.common.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SimpleAnimatedParticle;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.SimpleParticleType;

@Environment(EnvType.CLIENT)
public final class IllusionTotemParticle extends SimpleAnimatedParticle
{
	IllusionTotemParticle(
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
			this.setColor(0.376F + this.random.nextFloat() * 0.05F, 0.243F + this.random.nextFloat() * 0.05F, 0.361F + this.random.nextFloat() * 0.05F);
		} else {
			this.setColor(0.086F + this.random.nextFloat() * 0.05F, 0.043F + this.random.nextFloat() * 0.05F, 0.075F + this.random.nextFloat() * 0.05F);
		}
	}

	@Environment(EnvType.CLIENT)
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
		) {
			return new IllusionTotemParticle(clientWorld, d, e, f, g, h, i, this.spriteProvider);
		}
	}
}
