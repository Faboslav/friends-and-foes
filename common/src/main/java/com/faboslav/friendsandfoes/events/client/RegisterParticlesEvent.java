package com.faboslav.friendsandfoes.events.client;

import com.faboslav.friendsandfoes.events.base.EventHandler;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.SpriteProvider;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;

import java.util.function.Function;

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
public record RegisterParticlesEvent(Registrar registrar)
{
	public static final EventHandler<RegisterParticlesEvent> EVENT = new EventHandler<>();

	public <T extends ParticleEffect> void register(
		ParticleType<T> type,
		Function<SpriteProvider, ParticleFactory<T>> registration
	) {
		registrar.register(type, registration);
	}

	@FunctionalInterface
	public interface Registrar
	{
		<T extends ParticleEffect> void register(
			ParticleType<T> type,
			Function<SpriteProvider, ParticleFactory<T>> registration
		);
	}
}
