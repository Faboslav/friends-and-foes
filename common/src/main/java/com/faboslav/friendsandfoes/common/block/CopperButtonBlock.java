package com.faboslav.friendsandfoes.common.block;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.phys.BlockHitResult;

//? if < 1.21.1 {
/*import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlockSetTypes;
import net.minecraft.world.InteractionHand;
*///?}

public class CopperButtonBlock extends ButtonBlock
{
	public CopperButtonBlock(int pressTicks, Properties properties) {
		//? if >= 1.21.1 {
		super(BlockSetType.COPPER, pressTicks, properties);
		//?} else {
		/*super(properties, FriendsAndFoesBlockSetTypes.COPPER, pressTicks, true);
		*///?}
	}

	@Override
	public SoundEvent getSound(boolean powered) {
		return super.getSound(powered);
	}

	@Override
	//? if >= 1.21.1 {
	protected InteractionResult useWithoutItem(
		BlockState state,
		Level world,
		BlockPos pos,
		Player player,
		BlockHitResult hit
	)
	//?} else {
	/*public InteractionResult use(
		BlockState state,
		Level world,
		BlockPos pos,
		Player player,
		InteractionHand hand,
		BlockHitResult hit
	)
	*///?}
	{
		var actionResult = OnUseOxidizable.onOxidizableUse(state, world, pos, player, hit);

		if (actionResult.consumesAction()) {
			return actionResult;
		}

		//? if >= 1.21.1 {
		return super.useWithoutItem(state, world, pos, player, hit);
		//?} else {
		/*return super.use(state, world, pos, player, hand, hit);
		*///?}
	}
}
