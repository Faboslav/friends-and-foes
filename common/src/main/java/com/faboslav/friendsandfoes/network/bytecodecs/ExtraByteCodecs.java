package com.faboslav.friendsandfoes.network.bytecodecs;

import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.base.object.ObjectByteCodec;
import io.netty.buffer.ByteBuf;
import jdk.jshell.spi.ExecutionControl;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.text.TextCodecs;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.IndexedIterable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.GlobalPos;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.Optional;

public final class ExtraByteCodecs {

    private ExtraByteCodecs() {
    }

    public static final ByteCodec<Identifier> RESOURCE_LOCATION = ByteCodec.STRING.map(Identifier::new, Identifier::toString);
    public static final ByteCodec<RegistryKey<World>> DIMENSION = resourceKey(RegistryKeys.WORLD);

    public static final ByteCodec<BlockPos> BLOCK_POS = ByteCodec.LONG.map(BlockPos::fromLong, BlockPos::asLong);
    public static final ByteCodec<ChunkPos> CHUNK_POS = ByteCodec.LONG.map(ChunkPos::new, ChunkPos::toLong);
    public static final ByteCodec<ChunkSectionPos> SECTION_POS = ByteCodec.LONG.map(ChunkSectionPos::from, ChunkSectionPos::asLong);
    public static final ByteCodec<GlobalPos> GLOBAL_POS = ObjectByteCodec.create(
            DIMENSION.fieldOf(GlobalPos::dimension),
            BLOCK_POS.fieldOf(GlobalPos::pos),
            GlobalPos::create
    );
    public static final ByteCodec<Vector3f> VECTOR_3F = ObjectByteCodec.create(
            ByteCodec.FLOAT.fieldOf(Vector3f::x),
            ByteCodec.FLOAT.fieldOf(Vector3f::y),
            ByteCodec.FLOAT.fieldOf(Vector3f::z),
            Vector3f::new
    );

    public static final ByteCodec<NbtCompound> NULLABLE_COMPOUND_TAG = CompoundTagByteCodec.INSTANCE
            .map(value -> value.orElse(null), Optional::ofNullable);
    public static final ByteCodec<NbtCompound> NONNULL_COMPOUND_TAG = CompoundTagByteCodec.INSTANCE
            .map(Optional::orElseThrow, Optional::of);
    public static final ByteCodec<Optional<NbtCompound>> COMPOUND_TAG = CompoundTagByteCodec.INSTANCE;

    public static final ByteCodec<Text> COMPONENT = StreamCodecByteCodec.ofRegistry(TextCodecs.REGISTRY_PACKET_CODEC);

    public static final ByteCodec<Item> ITEM = registry(Registries.ITEM);
    public static final ByteCodec<Fluid> FLUID = registry(Registries.FLUID);

    public static final ByteCodec<ItemStack> ITEM_STACK = StreamCodecByteCodec.ofRegistry(ItemStack.PACKET_CODEC);
    public static final ByteCodec<Ingredient> INGREDIENT = StreamCodecByteCodec.ofRegistry(Ingredient.PACKET_CODEC);

    public static <T, R extends Registry<T>> ByteCodec<RegistryKey<T>> resourceKey(RegistryKey<R> registry) {
        return RESOURCE_LOCATION.map(id -> RegistryKey.of(registry, id), RegistryKey::getValue);
    }

    public static <T> ByteCodec<T> registry(IndexedIterable<T> map) {
        return new IdMapByteCodec<>(map);
    }

    public static <A, B> ByteCodec<Pair<A, B>> pair(ByteCodec<A> first, ByteCodec<B> second) {
        return ObjectByteCodec.create(
                first.fieldOf(Pair::getFirst),
                second.fieldOf(Pair::getSecond),
                Pair::of
        );
    }

    public static <A, B> ByteCodec<Either<A, B>> either(ByteCodec<A> first, ByteCodec<B> second) {
        final ByteCodec<Either<A, B>> left = first.map(Either::left, either -> either.left().orElseThrow());
        final ByteCodec<Either<A, B>> right = second.map(Either::right, either -> either.right().orElseThrow());
        return ByteCodec.BOOLEAN.dispatch(
            value -> value ? left : right,
            either -> either.map(l -> true, r -> false)
        );
    }

    public static PacketByteBuf toFriendly(ByteBuf buffer) {
        return buffer instanceof PacketByteBuf friendlyByteBuf ? friendlyByteBuf : new PacketByteBuf(buffer);
    }

    public static RegistryByteBuf toRegistry(ByteBuf buffer) {
        if (buffer instanceof RegistryByteBuf registryFriendlyByteBuf) return registryFriendlyByteBuf;
        throw new IllegalArgumentException("ByteBuf is not a RegistryFriendlyByteBuf");
    }
}
