package com.faboslav.friendsandfoes.common.util.world.processor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureEntityInfo;

/**
 * Inspired by use in Better Strongholds mod
 *
 * @author YUNGNICKYOUNG
 * <a href="https://github.com/YUNG-GANG/YUNGs-Better-Strongholds">https://github.com/YUNG-GANG/YUNGs-Better-Strongholds</a>
 */
public final class IceologerCabinArmorStandProcessorHelper
{
	public static StructureEntityInfo processEntity(
		StructureEntityInfo globalEntityInfo,
		StructurePlaceSettings structurePlacementData
	) {
		if (!globalEntityInfo.nbt.getString("id").equals("minecraft:armor_stand")) {
			return globalEntityInfo;
		}

		RandomSource random = structurePlacementData.getRandom(globalEntityInfo.blockPos);
		CompoundTag newNbtCompound = globalEntityInfo.nbt.copy();

		if (random.nextFloat() < 0.33F) {
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(0)).putString("id", Items.LEATHER_BOOTS.toString());
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(0)).putByte("Count", (byte) 1);
			CompoundTag bootsNbtCompound = new CompoundTag();
			bootsNbtCompound.putInt("Damage", 0);
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(0)).put("tag", bootsNbtCompound);
		}

		if (random.nextFloat() < 0.33F) {
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(1)).putString("id", Items.LEATHER_LEGGINGS.toString());
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(1)).putByte("Count", (byte) 1);
			CompoundTag bootsNbtCompound = new CompoundTag();
			bootsNbtCompound.putInt("Damage", 0);
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(1)).put("tag", bootsNbtCompound);
		}

		if (random.nextFloat() < 0.33F) {

			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(2)).putString("id", Items.LEATHER_CHESTPLATE.toString());
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(2)).putByte("Count", (byte) 1);
			CompoundTag bootsNbtCompound = new CompoundTag();
			bootsNbtCompound.putInt("Damage", 0);
			((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(2)).put("tag", bootsNbtCompound);
		}

		((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(3)).putString("id", Items.LEATHER_HELMET.toString());
		((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(3)).putByte("Count", (byte) 1);
		CompoundTag bootsNbtCompound = new CompoundTag();
		bootsNbtCompound.putInt("Damage", 0);
		((CompoundTag) newNbtCompound.getList("ArmorItems", 10).get(3)).put("tag", bootsNbtCompound);

		globalEntityInfo = new StructureEntityInfo(
			globalEntityInfo.pos,
			globalEntityInfo.blockPos,
			newNbtCompound
		);

		return globalEntityInfo;
	}
}
