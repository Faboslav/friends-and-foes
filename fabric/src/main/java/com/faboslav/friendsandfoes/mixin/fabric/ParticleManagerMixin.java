package com.faboslav.friendsandfoes.mixin.fabric;

import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleManager;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Function;

@Mixin(ParticleManager.class)
public abstract class ParticleManagerMixin
{
	@Final
	@Shadow
	private Map<Identifier, ParticleManager.SimpleSpriteProvider> spriteAwareFactories;

	@Final
	@Shadow
	private Int2ObjectMap<ParticleFactory<?>> factories;

	@Inject(
		method = "registerDefaultFactories",
		at = @At("RETURN")
	)
	private void friendsandfoes_onInit(CallbackInfo ci) {
		RegisterParticlesEvent.EVENT.invoke(new RegisterParticlesEvent(this::friendsandfoes_register));
	}

	@Unique
	private <T extends ParticleEffect> void friendsandfoes_register(
		ParticleType<T> particleType,
		Function<SpriteProvider, ParticleFactory<T>> spriteParticleRegistration
	) {
		ParticleManager.SimpleSpriteProvider mutableSpriteSet = new ParticleManager.SimpleSpriteProvider();
		this.spriteAwareFactories.put(Registry.PARTICLE_TYPE.getId(particleType), mutableSpriteSet);
		this.factories.put(Registry.PARTICLE_TYPE.getRawId(particleType), spriteParticleRegistration.apply(mutableSpriteSet));
	}
}
