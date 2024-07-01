package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

public record EnumCodec<T extends Enum<T>>(Class<T> enumClass) implements ByteCodec<T> {
    @Override
    public void encode(T value, ByteBuf buffer) {
        ByteBufUtils.writeVarInt(buffer, value.ordinal());
    }

    @Override
    public T decode(ByteBuf buffer) {
        return enumClass.getEnumConstants()[ByteBufUtils.readVarInt(buffer)];
    }
}
