package com.faboslav.friendsandfoes.network.bytecodecs;

import com.faboslav.friendsandfoes.network.bytecodecs.base.ByteCodec;
import com.faboslav.friendsandfoes.network.bytecodecs.base.object.ObjectByteCodec;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

/**
 * Use {@link ExtraByteCodecs#ITEM_STACK} instead.
 */
@Deprecated
@ApiStatus.ScheduledForRemoval(inVersion = "21.0")
public class ItemStackByteCodec {

    public static final ByteCodec<ItemStack> CODEC = StreamCodecByteCodec.ofRegistry(ItemStack.PACKET_CODEC);

    public static final ByteCodec<ItemStack> EMPTY_ITEM = ByteCodec.unit(ItemStack.EMPTY);
    public static final ByteCodec<ItemStack> SINGLE_ITEM = ExtraByteCodecs.ITEM.map(ItemStack::new, ItemStack::getItem);
    public static final ByteCodec<ItemStack> ITEM_WITH_COUNT = ObjectByteCodec.create(
            ExtraByteCodecs.ITEM.fieldOf(ItemStack::getItem),
            ByteCodec.INT.fieldOf(ItemStack::getCount),
            ItemStack::new
    );
    public static final ByteCodec<ItemStack> ITEM_WITH_COUNT_AND_TAG = CODEC;
}
