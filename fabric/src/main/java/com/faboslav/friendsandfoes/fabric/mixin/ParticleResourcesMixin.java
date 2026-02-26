package com.faboslav.friendsandfoes.fabric.mixin;

import com.faboslav.friendsandfoes.common.events.client.RegisterParticlesEvent;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
//? if <= 1.21.8 {
/*import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.client.particle.ParticleEngine.MutableSpriteSet;
*///?}
import net.minecraft.client.particle.ParticleProvider;
//? if >= 1.21.9 {
import net.minecraft.client.particle.ParticleResources;
import net.minecraft.client.particle.ParticleResources.MutableSpriteSet;
//?}
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Map;
import java.util.function.Function;

//? if >= 1.21.9 {
@Mixin(ParticleResources.class)
//?} else {
/*@Mixin(ParticleEngine.class)
*///?}
public abstract class ParticleResourcesMixin
{
	@Final
	@Shadow
	private Map<Identifier, MutableSpriteSet> spriteSets;

	@Final
	@Shadow
	private Int2ObjectMap<ParticleProvider<?>> providers;

	@Inject(
		method = "registerProviders",
		at = @At("RETURN")
	)
	private void friendsandfoes_onInit(CallbackInfo ci) {
		RegisterParticlesEvent.EVENT.invoke(new RegisterParticlesEvent(this::friendsandfoes_register));
	}

	@Unique
	private <T extends ParticleOptions> void friendsandfoes_register(
		ParticleType<T> particleType,
		Function<SpriteSet, ParticleProvider<T>> spriteParticleRegistration
	) {
		MutableSpriteSet mutableSpriteSet = new MutableSpriteSet();
		this.spriteSets.put(BuiltInRegistries.PARTICLE_TYPE.getKey(particleType), mutableSpriteSet);
		this.providers.put(BuiltInRegistries.PARTICLE_TYPE.getId(particleType), spriteParticleRegistration.apply(mutableSpriteSet));
	}
}
