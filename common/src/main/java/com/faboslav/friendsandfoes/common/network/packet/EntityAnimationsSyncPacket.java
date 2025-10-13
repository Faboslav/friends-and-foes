package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationLoader;
import com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json.AnimationParser;
import com.faboslav.friendsandfoes.common.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public record EntityAnimationsSyncPacket(Map<ResourceLocation, AnimationDefinition> entityAnimations) implements Packet<EntityAnimationsSyncPacket>
{
	public static final ResourceLocation ID = FriendsAndFoes.makeID("entity_animations_sync_packet");
	public static final ClientboundPacketType<EntityAnimationsSyncPacket> TYPE = new EntityAnimationsSyncPacket.Handler();

	public static void sendToClient(DatapackSyncEvent event) {
		Map<ResourceLocation, AnimationDefinition> entityAnimations = AnimationLoader.INSTANCE.getAnimations().entrySet().stream()
			.collect(Collectors.toMap(
				Map.Entry::getKey,
				entry -> entry.getValue().get()
			));
		MessageHandler.DEFAULT_CHANNEL.sendToPlayer(new EntityAnimationsSyncPacket(entityAnimations), event.player());
	}

	@Override
	public PacketType<EntityAnimationsSyncPacket> type() {
		return TYPE;
	}

	public static class Handler implements ClientboundPacketType<EntityAnimationsSyncPacket>
	{
		@Override
		public ResourceLocation id() {
			return ID;
		}

		@Override
		public Runnable handle(final EntityAnimationsSyncPacket packet) {
			return () -> AnimationLoader.INSTANCE.apply(packet.entityAnimations());
		}

		public EntityAnimationsSyncPacket decode(final RegistryFriendlyByteBuf buf) {
			Map<ResourceLocation, AnimationDefinition> parsedEntityAnimations = new HashMap<>();

			CompoundTag data = buf.readNbt();

			if (data == null) {
				FriendsAndFoes.getLogger().error("Entity Animation packet is empty");
				return new EntityAnimationsSyncPacket(parsedEntityAnimations);
			}

			//? if >=1.21.5 {
			ListTag entityAnimations = data.getListOrEmpty("entity_animations");
			//?} else {
			/*ListTag entityAnimations = data.getList("entity_animations", Tag.TAG_COMPOUND);
			*///?}

			for (int i = 0; i < entityAnimations.size(); i++) {
				//? if >=1.21.5 {
				CompoundTag entityAnimation = entityAnimations.getCompoundOrEmpty(i);
				//?} else {
				/*CompoundTag entityAnimation = entityAnimations.getCompound(i);
				*///?}

				Tag resourceLocationTag = entityAnimation.get("resource_location");
				Tag animationDefinitionTag = entityAnimation.get("animation_definition");

				if (resourceLocationTag == null || animationDefinitionTag == null) {
					FriendsAndFoes.getLogger().error("Entity Animation packet is invalid");
					continue;
				}

				// Parse using NbtOps and actual NBT tags
				DataResult<ResourceLocation> parsedResourceLocation = ResourceLocation.CODEC.parse(NbtOps.INSTANCE, resourceLocationTag);
				parsedResourceLocation.error().ifPresent(error ->
					FriendsAndFoes.getLogger().error("Failed to parse Resource Location packet entry: {}", error.message())
				);

				DataResult<AnimationDefinition> parsedAnimationDefinition = AnimationParser.CODEC.parse(NbtOps.INSTANCE, animationDefinitionTag);
				parsedAnimationDefinition.error().ifPresent(error ->
					FriendsAndFoes.getLogger().error("Failed to parse Animation Definition packet entry: {}", error.message())
				);

				parsedResourceLocation.result().ifPresent(validResourceLocation ->
					parsedAnimationDefinition.result().ifPresent(validAnimationDefinition ->
						parsedEntityAnimations.put(validResourceLocation, validAnimationDefinition)
					)
				);
			}

			return new EntityAnimationsSyncPacket(parsedEntityAnimations);
		}

		public void encode(final EntityAnimationsSyncPacket packet, final RegistryFriendlyByteBuf buf) {
			CompoundTag data = new CompoundTag();
			ListTag entityAnimationsList = new ListTag();

			for (Map.Entry<ResourceLocation, AnimationDefinition> entry : packet.entityAnimations().entrySet()) {
				CompoundTag animationEntry = new CompoundTag();

				animationEntry.putString("resource_location", entry.getKey().toString());

				DataResult<Tag> encodedDefinition = AnimationParser.CODEC.encodeStart(NbtOps.INSTANCE, entry.getValue());
				encodedDefinition.error().ifPresent(error ->
					FriendsAndFoes.getLogger().error("Failed to encode Animation Definition for {}: {}", entry.getKey(), error.message())
				);

				encodedDefinition.result().ifPresent(tag ->
					animationEntry.put("animation_definition", tag)
				);

				entityAnimationsList.add(animationEntry);
			}

			data.put("entity_animations", entityAnimationsList);
			buf.writeNbt(data);
		}
	}
}
