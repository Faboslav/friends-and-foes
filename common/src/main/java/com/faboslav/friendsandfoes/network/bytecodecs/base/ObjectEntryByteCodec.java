package com.faboslav.friendsandfoes.network.bytecodecs.base;

import io.netty.buffer.ByteBuf;

import java.util.function.Function;

public record ObjectEntryByteCodec<O, T>(ByteCodec<T> codec, Function<O, T> getter) implements WrappedByteCodec<T> {

    public void mapEncode(O object, ByteBuf buffer) {
        encode(getter.apply(object), buffer);
    }
}
