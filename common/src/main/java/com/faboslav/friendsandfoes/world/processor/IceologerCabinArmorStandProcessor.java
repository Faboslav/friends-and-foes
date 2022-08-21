package com.faboslav.friendsandfoes.world.processor;

import com.faboslav.friendsandfoes.init.FriendsAndFoesStructureProcessorTypes;
import com.mojang.serialization.Codec;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate;
import net.minecraft.structure.processor.StructureProcessorType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.WorldView;
import org.jetbrains.annotations.Nullable;

/**
 * Inspired by use in Better Strongholds by
 * YUNGNICKYOUNG (https://github.com/YUNG-GANG/YUNGs-Better-Strongholds)
 */
public final class IceologerCabinArmorStandProcessor extends StructureEntityProcessor
{
	public static final IceologerCabinArmorStandProcessor INSTANCE = new IceologerCabinArmorStandProcessor();
	public static final Codec<IceologerCabinArmorStandProcessor> CODEC = Codec.unit(() -> INSTANCE);

	@Override
	public StructureTemplate.StructureEntityInfo processEntity(
		ServerWorldAccess serverWorldAccess,
		BlockPos structurePiecePos,
		BlockPos structurePieceBottomCenterPos,
		StructureTemplate.StructureEntityInfo localEntityInfo,
		StructureTemplate.StructureEntityInfo globalEntityInfo,
		StructurePlacementData structurePlacementData
	) {
		if (globalEntityInfo.nbt.getString("id").equals("minecraft:armor_stand") == false) {
			return globalEntityInfo;
		}

		Random random = structurePlacementData.getRandom(globalEntityInfo.blockPos);
		NbtCompound newNbtCompound = globalEntityInfo.nbt.copy();

		if (random.nextFloat() < 0.33F) {
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(0)).putString("id", Items.LEATHER_BOOTS.toString());
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(0)).putByte("Count", (byte) 1);
			NbtCompound bootsNbtCompound = new NbtCompound();
			bootsNbtCompound.putInt("Damage", 0);
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(0)).put("tag", bootsNbtCompound);
		}

		if (random.nextFloat() < 0.33F) {
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(1)).putString("id", Items.LEATHER_LEGGINGS.toString());
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(1)).putByte("Count", (byte) 1);
			NbtCompound bootsNbtCompound = new NbtCompound();
			bootsNbtCompound.putInt("Damage", 0);
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(1)).put("tag", bootsNbtCompound);
		}

		if (random.nextFloat() < 0.33F) {

			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(2)).putString("id", Items.LEATHER_CHESTPLATE.toString());
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(2)).putByte("Count", (byte) 1);
			NbtCompound bootsNbtCompound = new NbtCompound();
			bootsNbtCompound.putInt("Damage", 0);
			((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(2)).put("tag", bootsNbtCompound);
		}

		((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(3)).putString("id", Items.LEATHER_HELMET.toString());
		((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(3)).putByte("Count", (byte) 1);
		NbtCompound bootsNbtCompound = new NbtCompound();
		bootsNbtCompound.putInt("Damage", 0);
		((NbtCompound) newNbtCompound.getList("ArmorItems", 10).get(3)).put("tag", bootsNbtCompound);

		globalEntityInfo = new StructureTemplate.StructureEntityInfo(
			globalEntityInfo.pos,
			globalEntityInfo.blockPos,
			newNbtCompound
		);

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
