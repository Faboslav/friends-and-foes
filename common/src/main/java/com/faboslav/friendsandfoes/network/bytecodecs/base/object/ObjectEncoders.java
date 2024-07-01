package com.faboslav.friendsandfoes.network.bytecodecs.base.object;

import com.faboslav.friendsandfoes.network.bytecodecs.utils.Functions.*;
import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.base.ObjectEntryByteCodec;
import io.netty.buffer.ByteBuf;

public final class ObjectEncoders {

    public record Encoder1<T, C1>(
            ObjectEntryByteCodec<T, C1> c1,
            Function1<T, C1> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer));
        }
    }

    public record Encoder2<T, C1, C2>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            Function2<T, C1, C2> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer));
        }
    }

    public record Encoder3<T, C1, C2, C3>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            Function3<T, C1, C2, C3> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer));
        }
    }

    public record Encoder4<T, C1, C2, C3, C4>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            Function4<T, C1, C2, C3, C4> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer));
        }
    }

    public record Encoder5<T, C1, C2, C3, C4, C5>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            Function5<T, C1, C2, C3, C4, C5> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer));
        }
    }

    public record Encoder6<T, C1, C2, C3, C4, C5, C6>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            Function6<T, C1, C2, C3, C4, C5, C6> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer));
        }
    }

    public record Encoder7<T, C1, C2, C3, C4, C5, C6, C7>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            Function7<T, C1, C2, C3, C4, C5, C6, C7> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer));
        }
    }

    public record Encoder8<T, C1, C2, C3, C4, C5, C6, C7, C8>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            Function8<T, C1, C2, C3, C4, C5, C6, C7, C8> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer));
        }
    }

    public record Encoder9<T, C1, C2, C3, C4, C5, C6, C7, C8, C9>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            Function9<T, C1, C2, C3, C4, C5, C6, C7, C8, C9> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer));
        }
    }

    public record Encoder10<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            ObjectEntryByteCodec<T, C10> c10,
            Function10<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
            c10.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer), c10.decode(buffer));
        }
    }

    public record Encoder11<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            ObjectEntryByteCodec<T, C10> c10,
            ObjectEntryByteCodec<T, C11> c11,
            Function11<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
            c10.mapEncode(value, buffer);
            c11.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer), c10.decode(buffer), c11.decode(buffer));
        }
    }

    public record Encoder12<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            ObjectEntryByteCodec<T, C10> c10,
            ObjectEntryByteCodec<T, C11> c11,
            ObjectEntryByteCodec<T, C12> c12,
            Function12<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
            c10.mapEncode(value, buffer);
            c11.mapEncode(value, buffer);
            c12.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer), c10.decode(buffer), c11.decode(buffer), c12.decode(buffer));
        }
    }

    public record Encoder13<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            ObjectEntryByteCodec<T, C10> c10,
            ObjectEntryByteCodec<T, C11> c11,
            ObjectEntryByteCodec<T, C12> c12,
            ObjectEntryByteCodec<T, C13> c13,
            Function13<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
            c10.mapEncode(value, buffer);
            c11.mapEncode(value, buffer);
            c12.mapEncode(value, buffer);
            c13.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer), c10.decode(buffer), c11.decode(buffer), c12.decode(buffer), c13.decode(buffer));
        }
    }

    public record Encoder14<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            ObjectEntryByteCodec<T, C10> c10,
            ObjectEntryByteCodec<T, C11> c11,
            ObjectEntryByteCodec<T, C12> c12,
            ObjectEntryByteCodec<T, C13> c13,
            ObjectEntryByteCodec<T, C14> c14,
            Function14<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
            c10.mapEncode(value, buffer);
            c11.mapEncode(value, buffer);
            c12.mapEncode(value, buffer);
            c13.mapEncode(value, buffer);
            c14.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer), c10.decode(buffer), c11.decode(buffer), c12.decode(buffer), c13.decode(buffer), c14.decode(buffer));
        }
    }

    public record Encoder15<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            ObjectEntryByteCodec<T, C10> c10,
            ObjectEntryByteCodec<T, C11> c11,
            ObjectEntryByteCodec<T, C12> c12,
            ObjectEntryByteCodec<T, C13> c13,
            ObjectEntryByteCodec<T, C14> c14,
            ObjectEntryByteCodec<T, C15> c15,
            Function15<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
            c10.mapEncode(value, buffer);
            c11.mapEncode(value, buffer);
            c12.mapEncode(value, buffer);
            c13.mapEncode(value, buffer);
            c14.mapEncode(value, buffer);
            c15.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer), c10.decode(buffer), c11.decode(buffer), c12.decode(buffer), c13.decode(buffer), c14.decode(buffer), c15.decode(buffer));
        }
    }

    public record Encoder16<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16>(
            ObjectEntryByteCodec<T, C1> c1,
            ObjectEntryByteCodec<T, C2> c2,
            ObjectEntryByteCodec<T, C3> c3,
            ObjectEntryByteCodec<T, C4> c4,
            ObjectEntryByteCodec<T, C5> c5,
            ObjectEntryByteCodec<T, C6> c6,
            ObjectEntryByteCodec<T, C7> c7,
            ObjectEntryByteCodec<T, C8> c8,
            ObjectEntryByteCodec<T, C9> c9,
            ObjectEntryByteCodec<T, C10> c10,
            ObjectEntryByteCodec<T, C11> c11,
            ObjectEntryByteCodec<T, C12> c12,
            ObjectEntryByteCodec<T, C13> c13,
            ObjectEntryByteCodec<T, C14> c14,
            ObjectEntryByteCodec<T, C15> c15,
            ObjectEntryByteCodec<T, C16> c16,
            Function16<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16> creator
    ) implements ByteCodec<T> {

        @Override
        public void encode(T value, ByteBuf buffer) {
            c1.mapEncode(value, buffer);
            c2.mapEncode(value, buffer);
            c3.mapEncode(value, buffer);
            c4.mapEncode(value, buffer);
            c5.mapEncode(value, buffer);
            c6.mapEncode(value, buffer);
            c7.mapEncode(value, buffer);
            c8.mapEncode(value, buffer);
            c9.mapEncode(value, buffer);
            c10.mapEncode(value, buffer);
            c11.mapEncode(value, buffer);
            c12.mapEncode(value, buffer);
            c13.mapEncode(value, buffer);
            c14.mapEncode(value, buffer);
            c15.mapEncode(value, buffer);
            c16.mapEncode(value, buffer);
        }

        @Override
        public T decode(ByteBuf buffer) {
            return creator.apply(c1.decode(buffer), c2.decode(buffer), c3.decode(buffer), c4.decode(buffer), c5.decode(buffer), c6.decode(buffer), c7.decode(buffer), c8.decode(buffer), c9.decode(buffer), c10.decode(buffer), c11.decode(buffer), c12.decode(buffer), c13.decode(buffer), c14.decode(buffer), c15.decode(buffer), c16.decode(buffer));
        }
    }

}
