package com.faboslav.friendsandfoes.common.util.world.processor;

import net.minecraft.Util;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

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
		StructurePlaceSettings structurePlacementData
	) {
		if (!globalEntityInfo.nbt.getString("id").equals("minecraft:item_frame")) {
			return globalEntityInfo;
		}

		RandomSource random = structurePlacementData.getRandom(globalEntityInfo.blockPos);

		CompoundTag newNbt = globalEntityInfo.nbt.copy();
		newNbt.getCompound("Item").putString("id", "minecraft:potion");
		newNbt.getCompound("Item").put("tag", Util.make(new CompoundTag(), potionTag -> {
			potionTag.putString("Potion", "minecraft:water");
		}));

		int randomRotation = random.nextInt(8);
		newNbt.putByte("ItemRotation", (byte) randomRotation);

		globalEntityInfo = new StructureEntityInfo(globalEntityInfo.pos, globalEntityInfo.blockPos, newNbt);

		return globalEntityInfo;
	}
}
