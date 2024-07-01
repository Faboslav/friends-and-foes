package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.util.LinkedHashMap;
import java.util.Map;

public record MapCodec<K, V>(PairCodec<K, V> codec) implements ByteCodec<Map<K, V>> {

    public MapCodec(ByteCodec<K> keyCodec, ByteCodec<V> valueCodec) {
        this(new PairCodec<>(keyCodec, valueCodec));
    }

    @Override
    public void encode(Map<K, V> value, ByteBuf buffer) {
        ByteBufUtils.writeVarInt(buffer, value.size());
        value.entrySet().forEach(entry -> codec.encode(entry, buffer));
    }

    @Override
    public Map<K, V> decode(ByteBuf buffer) {
        int size = ByteBufUtils.readVarInt(buffer);
        Map<K, V> map = new LinkedHashMap<>(size);
        for (int i = 0; i < size; i++) {
            Map.Entry<K, V> entry = codec.decode(buffer);
            map.put(entry.getKey(), entry.getValue());
        }
        return map;
    }
}
