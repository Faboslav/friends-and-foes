package com.faboslav.friendsandfoes.network.packet;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.api.MoobloomVariant;
import com.faboslav.friendsandfoes.api.MoobloomVariantManager;
import com.faboslav.friendsandfoes.events.lifecycle.DatapackSyncEvent;
import com.faboslav.friendsandfoes.network.MessageHandler;
import com.faboslav.friendsandfoes.network.base.Packet;
import com.faboslav.friendsandfoes.network.base.PacketContext;
import com.faboslav.friendsandfoes.network.base.PacketHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mojang.serialization.DataResult;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;

public record MoobloomVariantsSyncPacket(
	List<MoobloomVariant> moobloomVariants) implements Packet<MoobloomVariantsSyncPacket>
{
	public static Gson gson = new GsonBuilder().create();

	public static final Identifier ID = FriendsAndFoes.makeID("moobloom_variants_sync_packet");
	public static final MoobloomVariantsSyncPacket.Handler HANDLER = new MoobloomVariantsSyncPacket.Handler();

	public static void sendToClient(DatapackSyncEvent event) {
		MessageHandler.DEFAULT_CHANNEL.sendToPlayer(new MoobloomVariantsSyncPacket(MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.getMoobloomVariants()), event.player());
	}

	@Override
	public Identifier getID() {
		return ID;
	}

	@Override
	public PacketHandler<MoobloomVariantsSyncPacket> getHandler() {
		return HANDLER;
	}

	public static class Handler implements PacketHandler<MoobloomVariantsSyncPacket>
	{
		public PacketContext handle(final MoobloomVariantsSyncPacket packet) {
			return (player, level) -> MoobloomVariantManager.MOOBLOOM_VARIANT_MANAGER.setMoobloomVariants(packet.moobloomVariants());
		}

		public MoobloomVariantsSyncPacket decode(final PacketByteBuf buf) {
			List<MoobloomVariant> parsedMoobloomVariants = new ArrayList<>();

			NbtCompound data = buf.readNbt();

			if (data == null) {
				FriendsAndFoes.getLogger().error("Moobloom Variant packet is empty");
				return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
			}

			NbtList moobloomVariants = data.getList("moobloom_variants", NbtElement.COMPOUND_TYPE);

			for (NbtElement moobloomVariant : moobloomVariants) {
				DataResult<MoobloomVariant> parsedMoobloomVariant = MoobloomVariant.CODEC.parse(NbtOps.INSTANCE, moobloomVariant);
				parsedMoobloomVariant.error().ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to parse Moobloom Variant packet entry: {}", error));
				parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
			}

			return new MoobloomVariantsSyncPacket(parsedMoobloomVariants);
		}

		public void encode(final MoobloomVariantsSyncPacket packet, final PacketByteBuf buf) {
			NbtCompound data = new NbtCompound();
			NbtList parsedMoobloomVariants = new NbtList();

			for (MoobloomVariant moobloomVariant : packet.moobloomVariants()) {
				DataResult<NbtElement> parsedMoobloomVariant = MoobloomVariant.CODEC.encodeStart(NbtOps.INSTANCE, moobloomVariant);
				parsedMoobloomVariant.error().ifPresent(error -> FriendsAndFoes.getLogger().error("Failed to encode Moobloom Variant packet entry: {}", error));
				parsedMoobloomVariant.result().ifPresent(parsedMoobloomVariants::add);
			}

			data.put("moobloom_variants", parsedMoobloomVariants);
			buf.writeNbt(data);
		}
	}
}
