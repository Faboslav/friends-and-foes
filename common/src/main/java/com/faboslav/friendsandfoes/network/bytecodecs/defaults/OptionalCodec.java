package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;

import java.util.Objects;
import java.util.Optional;

public record OptionalCodec<T>(ByteCodec<T> codec, T value) implements ByteCodec<Optional<T>> {

    @Override
    public void encode(Optional<T> value, ByteBuf buffer) {
        if (value.isPresent() && !Objects.equals(value.get(), this.value)) {
            buffer.writeBoolean(true);
            codec.encode(value.get(), buffer);
        } else {
            buffer.writeBoolean(false);
        }
    }

    @Override
    public Optional<T> decode(ByteBuf buffer) {
        if (buffer.readBoolean()) {
            return Optional.of(codec.decode(buffer));
        }
        return Optional.ofNullable(value);
    }
}
