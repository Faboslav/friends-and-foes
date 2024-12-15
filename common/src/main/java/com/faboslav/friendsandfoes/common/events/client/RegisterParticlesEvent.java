package com.faboslav.friendsandfoes.common.events.client;

import com.faboslav.friendsandfoes.common.events.base.EventHandler;
import java.util.function.Function;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.SpriteSet;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;

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

	public <T extends ParticleOptions> void register(
		ParticleType<T> type,
		Function<SpriteSet, ParticleProvider<T>> registration
	) {
		registrar.register(type, registration);
	}

	@FunctionalInterface
	public interface Registrar
	{
		<T extends ParticleOptions> void register(
			ParticleType<T> type,
			Function<SpriteSet, ParticleProvider<T>> registration
		);
	}
}
