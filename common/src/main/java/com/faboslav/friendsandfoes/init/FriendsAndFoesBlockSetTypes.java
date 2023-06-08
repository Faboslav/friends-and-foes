package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.platform.RegistryHelper;
import net.minecraft.block.BlockSetType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

import java.util.function.Supplier;

/**
 * @see net.minecraft.block.BlockSetType
 */
public final class FriendsAndFoesBlockSetTypes
{
	public static Supplier<BlockSetType> COPPER = () -> new BlockSetType(
		FriendsAndFoes.makeStringID("copper"),
		true,
		BlockSoundGroup.COPPER,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_COPPER_PLACE,
		SoundEvents.BLOCK_STONE_BUTTON_CLICK_OFF,
		SoundEvents.BLOCK_STONE_BUTTON_CLICK_ON
	);

	public static void init() {
		RegistryHelper.registerBlockSetType(COPPER);
	}

	private FriendsAndFoesBlockSetTypes() {
	}
}
