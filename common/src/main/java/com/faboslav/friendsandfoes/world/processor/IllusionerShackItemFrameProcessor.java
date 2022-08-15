package com.faboslav.friendsandfoes.world.processor;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.init.FriendsAndFoesStructureProcessorTypes;
import com.mojang.serialization.Codec;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

/**
 * Inspired by use in Better Strongholds by
 * YUNGNICKYOUNG (https://github.com/YUNG-GANG/YUNGs-Better-Strongholds)
 */
public final class IllusionerShackItemFrameProcessor extends StructureEntityProcessor
{
	public static final IllusionerShackItemFrameProcessor INSTANCE = new IllusionerShackItemFrameProcessor();
	public static final Codec<IllusionerShackItemFrameProcessor> CODEC = Codec.unit(() -> INSTANCE);

	@Nullable
	@Override
	public StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureEntityInfo localEntityInfo,
		StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		if (globalEntityInfo.nbt.getString("id").equals("minecraft:item_frame") == false) {
			return globalEntityInfo;
		}

		Random random = structurePlacementData.getRandom(globalEntityInfo.blockPos);

		NbtCompound newNbt = globalEntityInfo.nbt.copy();
		newNbt.getCompound("Item").putString("id", "minecraft:potion");
		newNbt.getCompound("Item").put("tag", Util.make(new NbtCompound(), potionTag -> {
			potionTag.putString("Potion", "minecraft:water");
		}));

		int randomRotation = random.nextInt(8);
		FriendsAndFoes.getLogger().info(String.valueOf(randomRotation));
		newNbt.putByte("ItemRotation", (byte) randomRotation);

		FriendsAndFoes.getLogger().info(newNbt.toString());

		globalEntityInfo = new StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNbt);

		return globalEntityInfo;
	}

	@Nullable
	@Override
	public StructureTemplate.StructureBlockInfo process(
		WorldView world,
		BlockPos pos,
		BlockPos pivot,
		StructureTemplate.StructureBlockInfo localEntityInfo,
		StructureTemplate.StructureBlockInfo globalEntityInfo,
		StructurePlacementData data
	) {
		return globalEntityInfo;
	}

	@Override
	protected StructureProcessorType<?> getType() {
		return FriendsAndFoesStructureProcessorTypes.ILLUSIONER_SHACK_ITEM_FRAME_PROCESSOR;
	}
}
