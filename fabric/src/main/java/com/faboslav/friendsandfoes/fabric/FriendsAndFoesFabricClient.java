package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoesClient;
import com.faboslav.friendsandfoes.client.particle.FreezingTotemParticle;
import com.faboslav.friendsandfoes.client.particle.IllusionTotemParticle;
import com.faboslav.friendsandfoes.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.init.FriendsAndFoesParticleTypes;
import com.faboslav.friendsandfoes.platform.TotemHelper;
import com.faboslav.friendsandfoes.util.TotemUtil;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;

public final class FriendsAndFoesFabricClient implements ClientModInitializer
{
	@Override
	@Environment(EnvType.CLIENT)
	public void onInitializeClient() {
		FriendsAndFoesClient.init();
		FriendsAndFoesClient.postInit();

		this.registerParticleFactories();
		this.registerTotemPacket();
	}

	private void registerParticleFactories() {
		ParticleFactoryRegistry.getInstance().register(FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING, FreezingTotemParticle.Factory::new);
		ParticleFactoryRegistry.getInstance().register(FriendsAndFoesParticleTypes.TOTEM_OF_ILLUSION, IllusionTotemParticle.Factory::new);
	}

	private void registerTotemPacket() {
		ClientPlayNetworking.registerGlobalReceiver(TotemHelper.TOTEM_EFFECT_PACKET, (client, handler, buf, responseSender) -> {
			ItemStack itemStack = buf.readItemStack();
			assert client.world != null;
			Entity entity = client.world.getEntityById(buf.readInt());

			if (itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_ILLUSION.get()) {
				client.execute(() -> TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_ILLUSION));
			} else if (itemStack.getItem() == FriendsAndFoesItems.TOTEM_OF_FREEZING.get()) {
				client.execute(() -> TotemUtil.playActivateAnimation(itemStack, entity, FriendsAndFoesParticleTypes.TOTEM_OF_FREEZING));
			}
		});
	}
}

