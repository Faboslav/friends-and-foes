package com.faboslav.friendsandfoes.network.bytecodecs.base;

import io.netty.buffer.ByteBuf;

public interface WrappedByteCodec<T> extends ByteCodec<T> {

    ByteCodec<T> codec();

    @Override
    default void encode(T value, ByteBuf buffer) {
        codec().encode(value, buffer);
    }

    @Override
    default T decode(ByteBuf buffer) {
        return codec().decode(buffer);
    }
}
