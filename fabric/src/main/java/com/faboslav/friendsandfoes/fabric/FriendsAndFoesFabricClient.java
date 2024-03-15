package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.client.particle.FreezingTotemParticle;
import com.faboslav.friendsandfoes.client.particle.IllusionTotemParticle;
import com.faboslav.friendsandfoes.events.lifecycle.ClientSetupEvent;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;

public final class FriendsAndFoesFabricClient implements ClientModInitializer
{
	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		FriendsAndFoesClient.init();
		FriendsAndFoesClient.postInit();

		this.registerParticleFactories();
		this.initEvents();
	}

	private void initEvents() {
		ClientSetupEvent.EVENT.invoke(new ClientSetupEvent(Runnable::run));
	}

	private void registerParticleFactories() {
		ParticleFactoryRegistry.getInstance().register(FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING, FreezingTotemParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(FriendsAndFoesParticleTypes.TOTEM_OF_ILLUSION, IllusionTotemParticle.Factory::new);
	}
}

