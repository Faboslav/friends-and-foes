package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.world.level.block.entity.BeehiveBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

//? if >=26.2 {
import net.minecraft.world.level.block.entity.BlockEntityTypes;
//?}

public class VersionedBlockEntityType
{
	//? if >= 26.2 {
	public static final BlockEntityType<BeehiveBlockEntity> BEEHIVE = BlockEntityTypes.BEEHIVE;
	//?} else {
	/*public static final BlockEntityType<BeehiveBlockEntity> BEEHIVE = BlockEntityType.BEEHIVE;
	*///?}
}
