package com.faboslav.friendsandfoes.network.bytecodecs;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import io.netty.buffer.ByteBuf;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;

public class StreamCodecByteCodec {

    private static <B extends ByteBuf, T> ByteCodec<T> of(PacketCodec<B, T> codec, Function<ByteBuf, B> mapper) {
        return new ByteCodec<>() {
            @Override
            public void encode(T value, ByteBuf buffer) {
                codec.encode(mapper.apply(buffer), value);
            }

            @Override
            public T decode(ByteBuf buffer) {
                return codec.decode(mapper.apply(buffer));
            }
        };
    }

    private static <B extends ByteBuf, T> PacketCodec<B, T> to(ByteCodec<T> codec, Function<B, ByteBuf> mapper) {
        return new PacketCodec<>() {
            @Override
            public @NotNull T decode(B byteBuf) {
                return codec.decode(mapper.apply(byteBuf));
            }

            @Override
            public void encode(B byteBuf, T t) {
                codec.encode(t, mapper.apply(byteBuf));
            }
        };
    }

    public static <T> ByteCodec<T> of(PacketCodec<ByteBuf, T> codec) {
        return of(codec, Function.identity());
    }

    public static <T> PacketCodec<ByteBuf, T> to(ByteCodec<T> codec) {
        return to(codec, Function.identity());
    }

    public static <T> ByteCodec<T> ofFriendly(PacketCodec<PacketByteBuf, T> codec) {
        return of(codec, ExtraByteCodecs::toFriendly);
    }

    public static <T> PacketCodec<PacketByteBuf, T> toFriendly(ByteCodec<T> codec) {
        return to(codec, ExtraByteCodecs::toFriendly);
    }

    public static <T> ByteCodec<T> ofRegistry(PacketCodec<RegistryByteBuf, T> codec) {
        return of(codec, ExtraByteCodecs::toRegistry);
    }

    public static <T> PacketCodec<RegistryByteBuf, T> toRegistry(ByteCodec<T> codec) {
        return to(codec, ExtraByteCodecs::toRegistry);
    }

}
