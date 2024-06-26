package com.faboslav.friendsandfoes.network.defaults;

/*
import com.faboslav.friendsandfoes.network.Packet;
import com.teamresourceful.bytecodecs.base.ByteCodec;
import com.teamresourceful.resourcefullib.common.bytecodecs.StreamCodecByteCodec;
import com.teamresourceful.resourcefullib.common.network.Packet;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Identifier;

public abstract class CodecPacketType<T extends Packet<T>> extends AbstractPacketType<T> {

    protected PacketCodec<RegistryByteBuf, T> codec;

    public CodecPacketType(Class<T> clazz, Identifier id, PacketCodec<RegistryByteBuf, T> codec) {
        super(clazz, id);
        this.codec = codec;
    }

    public CodecPacketType(Class<T> clazz, Identifier id, ByteCodec<T> codec) {
        this(clazz, id, StreamCodecByteCodec.toRegistry(codec));
    }

    @Override
    public void encode(T message, RegistryByteBuf buffer) {
        codec.encode(buffer, message);
    }

    @Override
    public T decode(RegistryByteBuf buffer) {
        return codec.decode(buffer);
    }
}*/
