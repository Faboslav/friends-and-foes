package com.faboslav.friendsandfoes.network.bytecodecs.defaults;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.utils.ByteBufUtils;
import io.netty.buffer.ByteBuf;

import java.nio.charset.StandardCharsets;

public record StringCodec(int size) implements ByteCodec<String> {

    public static final StringCodec INSTANCE = new StringCodec(32767);
    public static final StringCodec COMPONENT_LENGTH = new StringCodec(262144);

    @Override
    public void encode(String value, ByteBuf buffer) {
        int encodedLength = size * 3;
        if (value.length() > size) {
            throw new RuntimeException("String too big (was " + value.length() + " characters, max " + size + ")");
        } else {
            byte[] bs = value.getBytes(StandardCharsets.UTF_8);
            if (bs.length > encodedLength) {
                throw new RuntimeException("String too big (was " + bs.length + " bytes encoded, max " + encodedLength + ")");
            } else {
                ByteBufUtils.writeVarInt(buffer, bs.length);
                buffer.writeBytes(bs);
            }
        }
    }

    @Override
    public String decode(ByteBuf buffer) {
        int encodedLength = size * 3;
        int length = ByteBufUtils.readVarInt(buffer);
        if (length > encodedLength) {
            throw new RuntimeException("The received encoded string buffer length is longer than maximum allowed (" + length + " > " + encodedLength + ")");
        } else if (length < 0) {
            throw new RuntimeException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            String string = buffer.toString(buffer.readerIndex(), length, StandardCharsets.UTF_8);
            buffer.readerIndex(buffer.readerIndex() + length);
            if (string.length() > size) {
                throw new RuntimeException("The received string length is longer than maximum allowed (" + string.length() + " > " + size + ")");
            }
            return string;
        }
    }
}
