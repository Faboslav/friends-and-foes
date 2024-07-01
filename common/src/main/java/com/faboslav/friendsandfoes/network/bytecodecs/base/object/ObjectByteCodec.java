package com.faboslav.friendsandfoes.network.bytecodecs.base.object;

import com.faboslav.friendsandfoes.network.bytecodecs.utils.Functions.*;
import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.base.ObjectEntryByteCodec;

public final class ObjectByteCodec {

    public static <T, C1> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, Function1<T, C1> creator) {
        return new ObjectEncoders.Encoder1<>(c1, creator);
    }

    public static <T, C1, C2> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, Function2<T, C1, C2> creator) {
        return new ObjectEncoders.Encoder2<>(c1, c2, creator);
    }

    public static <T, C1, C2, C3> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, Function3<T, C1, C2, C3> creator) {
        return new ObjectEncoders.Encoder3<>(c1, c2, c3, creator);
    }

    public static <T, C1, C2, C3, C4> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, Function4<T, C1, C2, C3, C4> creator) {
        return new ObjectEncoders.Encoder4<>(c1, c2, c3, c4, creator);
    }

    public static <T, C1, C2, C3, C4, C5> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, Function5<T, C1, C2, C3, C4, C5> creator) {
        return new ObjectEncoders.Encoder5<>(c1, c2, c3, c4, c5, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, Function6<T, C1, C2, C3, C4, C5, C6> creator) {
        return new ObjectEncoders.Encoder6<>(c1, c2, c3, c4, c5, c6, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, Function7<T, C1, C2, C3, C4, C5, C6, C7> creator) {
        return new ObjectEncoders.Encoder7<>(c1, c2, c3, c4, c5, c6, c7, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, Function8<T, C1, C2, C3, C4, C5, C6, C7, C8> creator) {
        return new ObjectEncoders.Encoder8<>(c1, c2, c3, c4, c5, c6, c7, c8, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, Function9<T, C1, C2, C3, C4, C5, C6, C7, C8, C9> creator) {
        return new ObjectEncoders.Encoder9<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, ObjectEntryByteCodec<T, C10> c10, Function10<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10> creator) {
        return new ObjectEncoders.Encoder10<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, ObjectEntryByteCodec<T, C10> c10, ObjectEntryByteCodec<T, C11> c11, Function11<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11> creator) {
        return new ObjectEncoders.Encoder11<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, ObjectEntryByteCodec<T, C10> c10, ObjectEntryByteCodec<T, C11> c11, ObjectEntryByteCodec<T, C12> c12, Function12<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12> creator) {
        return new ObjectEncoders.Encoder12<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, ObjectEntryByteCodec<T, C10> c10, ObjectEntryByteCodec<T, C11> c11, ObjectEntryByteCodec<T, C12> c12, ObjectEntryByteCodec<T, C13> c13, Function13<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13> creator) {
        return new ObjectEncoders.Encoder13<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, ObjectEntryByteCodec<T, C10> c10, ObjectEntryByteCodec<T, C11> c11, ObjectEntryByteCodec<T, C12> c12, ObjectEntryByteCodec<T, C13> c13, ObjectEntryByteCodec<T, C14> c14, Function14<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14> creator) {
        return new ObjectEncoders.Encoder14<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, ObjectEntryByteCodec<T, C10> c10, ObjectEntryByteCodec<T, C11> c11, ObjectEntryByteCodec<T, C12> c12, ObjectEntryByteCodec<T, C13> c13, ObjectEntryByteCodec<T, C14> c14, ObjectEntryByteCodec<T, C15> c15, Function15<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15> creator) {
        return new ObjectEncoders.Encoder15<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, creator);
    }

    public static <T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16> ByteCodec<T> create(ObjectEntryByteCodec<T, C1> c1, ObjectEntryByteCodec<T, C2> c2, ObjectEntryByteCodec<T, C3> c3, ObjectEntryByteCodec<T, C4> c4, ObjectEntryByteCodec<T, C5> c5, ObjectEntryByteCodec<T, C6> c6, ObjectEntryByteCodec<T, C7> c7, ObjectEntryByteCodec<T, C8> c8, ObjectEntryByteCodec<T, C9> c9, ObjectEntryByteCodec<T, C10> c10, ObjectEntryByteCodec<T, C11> c11, ObjectEntryByteCodec<T, C12> c12, ObjectEntryByteCodec<T, C13> c13, ObjectEntryByteCodec<T, C14> c14, ObjectEntryByteCodec<T, C15> c15, ObjectEntryByteCodec<T, C16> c16, Function16<T, C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, C16> creator) {
        return new ObjectEncoders.Encoder16<>(c1, c2, c3, c4, c5, c6, c7, c8, c9, c10, c11, c12, c13, c14, c15, c16, creator);
    }

}
