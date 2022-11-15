package com.faboslav.friendsandfoes.util.world.processor;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.util.Util;
import net.minecraft.util.math.random.Random;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
public final class IllusionerShackItemFrameProcessorHelper
{
	public static StructureEntityInfo processEntity(
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
		newNbt.putByte("ItemRotation", (byte) randomRotation);

		globalEntityInfo = new StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNbt);

		return globalEntityInfo;
	}
}
