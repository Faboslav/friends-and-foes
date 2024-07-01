package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.Collection;
import java.util.function.Function;

public record CollectionCodec<T, C extends Collection<T>>(ByteCodec<T> codec, Function<Integer, C> creator) implements ByteCodec<C> {

    @Override
    public void encode(C value, ByteBuf buffer) {
        ByteBufUtils.writeVarInt(buffer, value.size());
        for (T t : value) {
            codec.encode(t, buffer);
        }
    }

    @Override
    public C decode(ByteBuf buffer) {
        int size = ByteBufUtils.readVarInt(buffer);
        C list = creator.apply(size);
        for (int i = 0; i < size; i++) {
            list.add(codec.decode(buffer));
        }
        return list;
    }
}
