package com.faboslav.friendsandfoes.common.entity.ai.brain.task.coppergolem;

import com.faboslav.friendsandfoes.common.entity.animation.CopperGolemAnimations;
import com.faboslav.friendsandfoes.common.entity.CopperGolemEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.CopperGolemBrain;
import com.faboslav.friendsandfoes.common.entity.pose.CopperGolemEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.ButtonBlock;
import net.minecraft.world.level.block.WeatheringCopper;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

public final class CopperGolemPressButtonTask extends Behavior<CopperGolemEntity>
{
	private final static float WITHING_DISTANCE = 2.0F;
	private int pressButtonTicks = 0;
	private int minPressButtonTick = 0;
	private int maxPressButtonTicks = 0;
	private boolean wasButtonPressed = false;
	private int heightDifference = 0;

	public CopperGolemPressButtonTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get(), MemoryStatus.VALUE_PRESENT,
				FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_IS_OXIDIZED.get(), MemoryStatus.VALUE_ABSENT
			), 100
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, CopperGolemEntity copperGolem) {
		if (
			copperGolem.getButtonPos() == null
			|| copperGolem.isButtonValidToBePressed(copperGolem.getButtonPos().pos()) == false
			|| copperGolem.getButtonPos().pos().closerToCenterThan(copperGolem.position(), WITHING_DISTANCE) == false
			|| copperGolem.getNavigation().isInProgress()
		) {
			return false;
		}

		return true;
	}

	@Override
	protected void start(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		this.pressButtonTicks = 0;
		this.wasButtonPressed = false;

		GlobalPos buttonPos = copperGolem.getButtonPos();

		if (buttonPos == null) {
			return;
		}

		this.heightDifference = buttonPos.pos().getY() - copperGolem.blockPosition().getY();

		if (this.heightDifference >= 1) {
			this.maxPressButtonTicks = CopperGolemAnimations.PRESS_BUTTON_UP.get().lengthInTicks(copperGolem.getAnimationSpeedModifier());
		} else {
			this.maxPressButtonTicks = CopperGolemAnimations.PRESS_BUTTON_DOWN.get().lengthInTicks(copperGolem.getAnimationSpeedModifier());
		}

		this.minPressButtonTick = copperGolem.getRandom().nextIntBetweenInclusive((int) (this.maxPressButtonTicks * 0.4), (int) (this.maxPressButtonTicks * 0.6));
	}

	@Override
	protected boolean canStillUse(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		if (
			this.pressButtonTicks > this.maxPressButtonTicks
			|| copperGolem.isOxidized()
		) {
			return false;
		}

		return true;
	}

	protected void tick(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		this.pressButtonTicks++;

		if (this.heightDifference >= 1) {
			copperGolem.startPressButtonUpAnimation();
		} else {
			copperGolem.startPressButtonDownAnimation();
		}

		if (
			!copperGolem.isWaxed()
			&& copperGolem.getOxidationLevel().ordinal() >= WeatheringCopper.WeatherState.WEATHERED.ordinal()
			&& copperGolem.getRandom().nextFloat() > 0.99
		) {
			copperGolem.setOxidationLevel(WeatheringCopper.WeatherState.OXIDIZED);
		}

		if (pressButtonTicks < this.minPressButtonTick) {
			return;
		}

		if (this.wasButtonPressed) {
			return;
		}

		GlobalPos buttonPos = copperGolem.getButtonPos();

		if (buttonPos == null) {
			return;
		}

		this.wasButtonPressed = this.tryToPressButton(copperGolem, buttonPos.pos());
	}

	@Override
	protected void stop(ServerLevel world, CopperGolemEntity copperGolem, long time) {
		copperGolem.setPose(CopperGolemEntityPose.IDLE);
		copperGolem.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.COPPER_GOLEM_BUTTON_POS.get());
		CopperGolemBrain.setPressButtonCooldown(copperGolem);
	}

	private boolean tryToPressButton(CopperGolemEntity copperGolem, BlockPos buttonPos) {
		BlockState buttonBlockState = this.getButtonBlockState(copperGolem, buttonPos);

		if (
			buttonBlockState == null
			|| buttonBlockState.getValue(ButtonBlock.POWERED)
		) {
			return false;
		}

		ButtonBlock buttonBlock = (ButtonBlock) buttonBlockState.getBlock();

		buttonBlock.press(
			buttonBlockState,
			copperGolem.level(),
			buttonPos,
			null
		);
		copperGolem.getCommandSenderWorld().playSound(null,
			buttonPos,
			buttonBlock.getSound(true),
			SoundSource.BLOCKS,
			0.3F,
			0.6F
		);
		copperGolem.getCommandSenderWorld().gameEvent(copperGolem,
			GameEvent.BLOCK_ACTIVATE,
			buttonPos
		);


		return true;
	}

	@Nullable
	private BlockState getButtonBlockState(CopperGolemEntity copperGolem, BlockPos buttonBlockPos) {
		ServerLevel serverWorld = (ServerLevel) copperGolem.getCommandSenderWorld();
		BlockState blockState = serverWorld.getBlockState(buttonBlockPos);

		if (blockState.getBlock() instanceof ButtonBlock == false) {
			return null;
		}

		return blockState;
	}
}
