package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.block.RegisterBlockSetTypeEvent;
import net.minecraft.block.BlockSetType;
import net.minecraft.sound.BlockSoundGroup;
import net.minecraft.sound.SoundEvents;

/**
 * @see net.minecraft.block.BlockSetType
 */
public final class FriendsAndFoesBlockSetTypes
{
	public static BlockSetType COPPER = new BlockSetType(
		FriendsAndFoes.makeStringID("copper"),
		false,
		false,
		false,
		BlockSetType.ActivationRule.EVERYTHING,
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

	public static void registerBlockSetTypes(RegisterBlockSetTypeEvent event) {
		event.register(COPPER);
	}

	private FriendsAndFoesBlockSetTypes() {
	}
}
