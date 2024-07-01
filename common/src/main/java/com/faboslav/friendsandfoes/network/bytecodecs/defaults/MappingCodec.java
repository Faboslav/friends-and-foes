package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;

import java.util.function.Function;

public record MappingCodec<T, R>(
        ByteCodec<T> codec, Function<T, R> decoder, Function<R, T> encoder
) implements ByteCodec<R> {

    @Override
    public void encode(R value, ByteBuf buffer) {
        T encoded = encoder.apply(value);
        codec.encode(encoded, buffer);
    }

    @Override
    public R decode(ByteBuf buffer) {
        T decoded = codec.decode(buffer);
        return decoder.apply(decoded);
    }
}
