package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;

import java.util.function.Supplier;

public record UnitCodec<T>(Supplier<T> value) implements ByteCodec<T>
{

    public UnitCodec(T value) {
        this(() -> value);
    }

    @Override
    public void encode(T value, ByteBuf buffer) {}

    @Override
    public T decode(ByteBuf buffer) {
        return this.value.get();
    }
}
