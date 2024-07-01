package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;

import java.util.AbstractMap;
import java.util.Map;

public record PairCodec<K, V>(ByteCodec<K> first, ByteCodec<V> second) implements ByteCodec<Map.Entry<K, V>> {

    @Override
    public void encode(Map.Entry<K, V> value, ByteBuf buffer) {
        first.encode(value.getKey(), buffer);
        second.encode(value.getValue(), buffer);
    }

    @Override
    public Map.Entry<K, V> decode(ByteBuf buffer) {
        return new AbstractMap.SimpleEntry<>(first.decode(buffer), second.decode(buffer));
    }
}
