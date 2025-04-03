package com.faboslav.friendsandfoes.common.network.packet;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.api.MoobloomVariant;
import com.faboslav.friendsandfoes.common.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.common.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.common.network.MessageHandler;
import com.mojang.serialization.DataResult;
import com.teamresourceful.resourcefullib.common.network.Packet;
import com.teamresourceful.resourcefullib.common.network.base.ClientboundPacketType;
import com.teamresourceful.resourcefullib.common.network.base.PacketType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import java.util.ArrayList;
import java.util.List;

public record MoobloomVariantsSyncPacket(
	List<MoobloomVariant> moobloomVariants) implements Packet<MoobloomVariantsSyncPacket>
{
	public static final ResourceLocation ID = FriendsAndFoes.makeID("moobloom_variants_sync_packet");
	public static final ClientboundPacketType<MoobloomVariantsSyncPacket> TYPE = new MoobloomVariantsSyncPacket.Handler();

	public static void sendToClient(DatapackSyncEvent event) {
		MessageHandler.DEFAULT_CHANNEL.sendToPlayer(new MoobloomVariantsSyncPacket(MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariants()), event.player());
	}

	@Override
	public PacketType<MoobloomVariantsSyncPacket> type() {
		return TYPE;
	}

	public static class Handler implements ClientboundPacketType<MoobloomVariantsSyncPacket>
	{
		@Override
		public ResourceLocation id() {
			return ID;
		}

		@Override
		public Runnable handle(final MoobloomVariantsSyncPacket packet) {
			return () -> MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.setMoobloomVariants(packet.moobloomVariants());
		}

		public MoobloomVariantsSyncPacket decode(final RegistryFriendlyByteBuf buf) {
			List<MoobloomVariant> parsedMoobloomVariants = new ArrayList<>();

			CompoundTag data = buf.readNbt();

			if (data == null) {
				FriendsAndFoes.getLogger().error("Moobloom Variant packet is empty");
				return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
			}

			//? >=1.21.5 {
			/*ListTag moobloomVariants = data.getListOrEmpty("moobloom_variants");
			*///?} else {
			ListTag moobloomVariants = data.getList("moobloom_variants", Tag.TAG_COMPOUND);
			 //?}

			for (Tag moobloomVariant : moobloomVariants) {
				DataResult<MoobloomVariant> parsedMoobloomVariant = MoobloomVariant.CODEC.parse(NbtOps.INSTANCE, moobloomVariant);
				parsedMoobloomVariant.error().ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to parse Moobloom Variant packet entry: {}", error));
				parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
			}

			return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
		}

		public void encode(final MoobloomVariantsSyncPacket packet, final RegistryFriendlyByteBuf buf) {
			CompoundTag data = new CompoundTag();
			ListTag parsedMoobloomVariants = new ListTag();

			for (MoobloomVariant moobloomVariant : packet.moobloomVariants()) {
				DataResult<Tag> parsedMoobloomVariant = MoobloomVariant.CODEC.encodeStart(NbtOps.INSTANCE, moobloomVariant);
				parsedMoobloomVariant.error().ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to encode Moobloom Variant packet entry: {}", error));
				parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
			}

			data.put("moobloom_variants", parsedMoobloomVariants);
			buf.writeNbt(data);
		}
	}
}
