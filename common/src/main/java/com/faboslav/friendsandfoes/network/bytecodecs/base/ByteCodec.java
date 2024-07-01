package com.faboslav.friendsandfoes.network.bytecodecs.base;

import com.faboslav.friendsandfoes.network.bytecodecs.defaults.*;
import com.faboslav.friendsandfoes.network.bytecodecs.utils.ByteBufUtils;
import com.faboslav.friendsandfoes.network.bytecodecs.utils.Either;
import io.netty.buffer.ByteBuf;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

public interface ByteCodec<T> {

    void encode(T value, ByteBuf buffer);

    T decode(ByteBuf buffer);

    default ByteCodec<List<T>> listOf() {
        return collectionOf(ArrayList::new);
    }

    default ByteCodec<Set<T>> setOf() {
        return collectionOf(HashSet::new);
    }

    default ByteCodec<Set<T>> linkedSetOf() {
        return collectionOf(LinkedHashSet::new);
    }

    default <C extends Collection<T>> ByteCodec<C> collectionOf(Function<Integer, C> getter) {
        return new CollectionCodec<>(this, getter);
    }

    default ByteCodec<Optional<T>> optionalOf() {
        return new OptionalCodec<>(this, null);
    }

    default ByteCodec<Optional<T>> optionalOf(T value) {
        return new OptionalCodec<>(this, value);
    }

    default ByteCodec<Optional<T>> optionalOf(Supplier<T> value) {
        return new OptionalSupplierCodec<>(this, value);
    }

    default <O> ObjectEntryByteCodec<O, T> fieldOf(Function<O, T> getter) {
        return new ObjectEntryByteCodec<>(this, getter);
    }

    default <O> ObjectEntryByteCodec<O, Optional<T>> optionalFieldOf(Function<O, Optional<T>> getter) {
        return new ObjectEntryByteCodec<>(this.optionalOf(), getter);
    }

    default <O> ObjectEntryByteCodec<O, Optional<T>> optionalFieldOf(T value, Function<O, Optional<T>> getter) {
        return new ObjectEntryByteCodec<>(this.optionalOf(value), getter);
    }

    default <O> ObjectEntryByteCodec<O, Optional<T>> optionalFieldOf(Supplier<T> value, Function<O, Optional<T>> getter) {
        return new ObjectEntryByteCodec<>(this.optionalOf(value), getter);
    }

    default <R> ByteCodec<R> map(Function<T, R> decoder, Function<R, T> encoder) {
        return new MappingCodec<>(this, decoder, encoder);
    }

    default <O> ByteCodec<O> dispatch(Function<T, ByteCodec<O>> getter, Function<O, T> keyGetter) {
        return new KeyDispatchCodec<>(this, getter, keyGetter);
    }

    static <F, S> ByteCodec<Either<F, S>> either(ByteCodec<F> first, ByteCodec<S> second) {
        final ByteCodec<Either<F, S>> left = first.map(Either::ofLeft, Either::leftOrThrow);
        final ByteCodec<Either<F, S>> right = second.map(Either::ofRight, Either::rightOrThrow);
        return ByteCodec.BOOLEAN.dispatch(value -> value ? left : right, Either::isLeft);
    }

    static <T> ByteCodec<T> choice(ByteCodec<T> first, ByteCodec<T> second, Function<T, Either<T, T>> discriminator) {
        return either(first, second).map(Either::value, discriminator);
    }

    static <T extends Enum<T>> ByteCodec<T> ofEnum(Class<T> enumClass) {
        return new EnumCodec<>(enumClass);
    }

    static <T> ByteCodec<T> unit(T value) {
        return new UnitCodec<>(value);
    }

    static <T> ByteCodec<T> unit(Supplier<T> value) {
        return new UnitCodec<>(value);
    }

    static <T> ByteCodec<T> passthrough(BiConsumer<ByteBuf, T> encoder, Function<ByteBuf, T> decoder) {
        return new PassthroughCodec<>(encoder, decoder);
    }

    static <K, V> MapCodec<K, V> mapOf(ByteCodec<K> keyCodec, ByteCodec<V> valueCodec) {
        return new MapCodec<>(keyCodec, valueCodec);
    }

    static <K, V> PairCodec<K, V> pairOf(ByteCodec<K> keyCodec, ByteCodec<V> valueCodec) {
        return new PairCodec<>(keyCodec, valueCodec);
    }

    ByteCodec<String> STRING = StringCodec.INSTANCE;
    ByteCodec<String> STRING_COMPONENT = StringCodec.COMPONENT_LENGTH;
    ByteCodec<Character> CHAR = new PassthroughCodec<>((buf, value) -> buf.writeChar(value), ByteBuf::readChar);
    ByteCodec<Boolean> BOOLEAN = new PassthroughCodec<>(ByteBuf::writeBoolean, ByteBuf::readBoolean);
    ByteCodec<Byte> BYTE = new PassthroughCodec<>((buf, value) -> buf.writeByte(value), ByteBuf::readByte);
    ByteCodec<Short> SHORT = new PassthroughCodec<>((buf, value) -> buf.writeShort(value), ByteBuf::readShort);
    ByteCodec<Integer> INT = new PassthroughCodec<>(ByteBuf::writeInt, ByteBuf::readInt);
    ByteCodec<Integer> VAR_INT = new PassthroughCodec<>(ByteBufUtils::writeVarInt, ByteBufUtils::readVarInt);
    ByteCodec<Long> LONG = new PassthroughCodec<>(ByteBuf::writeLong, ByteBuf::readLong);
    ByteCodec<Long> VAR_LONG = new PassthroughCodec<>(ByteBufUtils::writeVarLong, ByteBufUtils::readVarLong);
    ByteCodec<Float> FLOAT = new PassthroughCodec<>(ByteBuf::writeFloat, ByteBuf::readFloat);
    ByteCodec<Double> DOUBLE = new PassthroughCodec<>(ByteBuf::writeDouble, ByteBuf::readDouble);

    ByteCodec<UUID> UUID = new PassthroughCodec<>(ByteBufUtils::writeUUID, ByteBufUtils::readUUID);
}
