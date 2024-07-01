package com.faboslav.friendsandfoes.network.bytecodecs;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import java.io.IOException;
import java.util.Optional;

public final class CompoundTagByteCodec implements ByteCodec<Optional<NbtCompound>> {

    public static final CompoundTagByteCodec INSTANCE = new CompoundTagByteCodec();

    @Override
    public void encode(Optional<NbtCompound> value, ByteBuf buffer) {
        if (value.isEmpty()) {
            buffer.writeByte(0);
        } else {
            try {
                NbtIo.writeCompound(value.get(), new ByteBufOutputStream(buffer));
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }

    }

    @Override
    public Optional<NbtCompound> decode(ByteBuf buffer) {
        int i = buffer.readerIndex();
        byte b = buffer.readByte();
        if (b == 0) {
            return Optional.empty();
        } else {
            buffer.readerIndex(i);

            try {
                return Optional.of(NbtIo.readCompound(new ByteBufInputStream(buffer), NbtSizeTracker.of(2097152L)));
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
    }
}