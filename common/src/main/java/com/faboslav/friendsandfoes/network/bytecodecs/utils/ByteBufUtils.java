package com.faboslav.friendsandfoes.network.bytecodecs.utils;

import io.netty.buffer.ByteBuf;

import java.util.UUID;

public final class ByteBufUtils {

    public static void writeVarInt(ByteBuf buffer, int input) {
        while((input & -128) != 0) {
            buffer.writeByte(input & 127 | 128);
            input >>>= 7;
        }

        buffer.writeByte(input);
    }

    public static int readVarInt(ByteBuf buffer) {
        int i = 0;
        int j = 0;

        byte b;
        do {
            b = buffer.readByte();
            i |= (b & 127) << j++ * 7;
            if (j > 5) {
                throw new RuntimeException("VarInt too big");
            }
        } while((b & 128) == 128);

        return i;
    }

    public static void writeVarLong(ByteBuf buffer, long value) {
        while((value & -128L) != 0L) {
            buffer.writeByte((int)(value & 127L) | 128);
            value >>>= 7;
        }

        buffer.writeByte((int)value);
    }

    public static long readVarLong(ByteBuf buffer) {
        long l = 0L;
        int i = 0;

        byte b;
        do {
            b = buffer.readByte();
            l |= (long)(b & 127) << i++ * 7;
            if (i > 10) {
                throw new RuntimeException("VarLong too big");
            }
        } while((b & 128) == 128);

        return l;
    }

    public static void writeUUID(ByteBuf buffer, UUID uuid) {
        buffer.writeLong(uuid.getMostSignificantBits());
        buffer.writeLong(uuid.getLeastSignificantBits());
    }

    public static UUID readUUID(ByteBuf buffer) {
        return new UUID(buffer.readLong(), buffer.readLong());
    }
}
