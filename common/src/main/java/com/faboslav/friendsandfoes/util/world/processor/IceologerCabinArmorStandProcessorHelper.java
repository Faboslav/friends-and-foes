package com.faboslav.friendsandfoes.util.world.processor;

import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.structure.StructurePlacementData;
import net.minecraft.structure.StructureTemplate.StructureEntityInfo;
import net.minecraft.util.math.random.Random;

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

		globalEntityInfo = new StructureEntityInfo(
			globalEntityInfo.pos,
			globalEntityInfo.blockPos,
			newNbtCompound
		);

		return globalEntityInfo;
	}
}
