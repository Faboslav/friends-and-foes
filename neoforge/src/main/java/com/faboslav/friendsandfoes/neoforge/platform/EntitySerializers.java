package com.faboslav.friendsandfoes.neoforge.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

public final class EntitySerializers implements com.faboslav.friendsandfoes.common.platform.EntitySerializers
{
	public static final DeferredRegister<EntityDataSerializer<?>> ENTITY_DATA_SERIALIZERS = DeferredRegister.create(NeoForgeRegistries.ENTITY_DATA_SERIALIZERS, FriendsAndFoes.MOD_ID);

	@Override
	public void register(String id, EntityDataSerializer<?> serializer) {
		FriendsAndFoes.getLogger().info("Registering entity data serializer: {}", id);
		ENTITY_DATA_SERIALIZERS.register(id, () -> serializer);
	}
}
