package com.faboslav.friendsandfoes.network.bytecodecs;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;
import net.minecraft.util.collection.IndexedIterable;

public record IdMapByteCodec<T>(IndexedIterable<T> map) implements ByteCodec<T> {

    @Override
    public void encode(T value, ByteBuf buffer) {
        int id = map.getRawId(value);
        if (id == -1) {
            throw new IllegalArgumentException("Can't find id for '" + value + "' in map " + map);
        }
        ByteBufUtils.writeVarInt(buffer, id);
    }

    @Override
    public T decode(ByteBuf buffer) {
        int id = ByteBufUtils.readVarInt(buffer);
        return map.get(id);
    }
}
