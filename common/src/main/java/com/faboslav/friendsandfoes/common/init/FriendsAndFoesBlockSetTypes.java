//? if <1.21.1 {
/*package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.events.lifecycle.RegisterBlockSetTypeEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.properties.BlockSetType;

/^*
 * @see BlockSetType
 ^/
public final class FriendsAndFoesBlockSetTypes
{
	public static BlockSetType COPPER = new BlockSetType(
		FriendsAndFoes.makeStringID("copper"),
		true,
		SoundType.COPPER,
		SoundEvents.COPPER_PLACE,
		SoundEvents.COPPER_PLACE,
		SoundEvents.COPPER_PLACE,
		SoundEvents.COPPER_PLACE,
		SoundEvents.COPPER_PLACE,
		SoundEvents.COPPER_PLACE,
		SoundEvents.STONE_BUTTON_CLICK_OFF,
		SoundEvents.STONE_BUTTON_CLICK_ON
	);

	public static void registerBlockSetTypes(RegisterBlockSetTypeEvent event) {
		event.register(COPPER);
	}

	private FriendsAndFoesBlockSetTypes() {
	}
}
*///?}
