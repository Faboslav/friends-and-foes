package com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;

//? if >= 1.21.10 {
import net.minecraft.world.entity.vehicle.boat.AbstractBoat;
//?} else {
/*import net.minecraft.world.entity.vehicle.Boat;
*///?}

import java.util.Map;

public class PenguinSwimWithPlayerTask extends Behavior<PenguinEntity>
{
	private LivingEntity player;

	public PenguinSwimWithPlayerTask() {
		super(
			Map.of(
				MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT
			)
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, PenguinEntity penguin) {
		var player = penguin.getBrain().getMemory(MemoryModuleType.NEAREST_VISIBLE_PLAYER).orElse(null);

		if (
			!penguin.isInWater()
			|| player == null
			|| !player.isAlive()
			|| !player.isPassenger()
			//? if >= 1.21.10 {
			|| !(this.player.getVehicle() instanceof AbstractBoat)
			//?} else {
			/*|| !(this.player.getVehicle() instanceof Boat)
			*///?}
		) {
			return false;
		}

		this.player = player;

		return true;
	}

	@Override
	protected void start(ServerLevel world, PenguinEntity penguin, long time) {
		BehaviorUtils.lookAtEntity(penguin, this.player);
		penguin.getLookControl().setLookAt(this.player);
		this.player.addEffect(new MobEffectInstance(FriendsAndFoesStatusEffects.BOAT_SPEED.holder(), 100), penguin);
	}

	@Override
	protected boolean canStillUse(ServerLevel world, PenguinEntity penguin, long time) {
		if (
			!penguin.isInWater()
			|| this.player == null
			|| !this.player.isAlive()
			|| !this.player.isPassenger()
			|| !this.player.isPassenger()
			//? if >= 1.21.10 {
			|| !(this.player.getVehicle() instanceof AbstractBoat)
			//?} else {
			/*|| !(this.player.getVehicle() instanceof Boat)
			*///?}
		) {
			return false;
		}

		return penguin.distanceToSqr(this.player) < 256.0D;
	}

	@Override
	protected void tick(ServerLevel serverLevel, PenguinEntity penguin, long time) {
		penguin.getLookControl().setLookAt(this.player, (float)(penguin.getMaxHeadYRot() + 20), (float)penguin.getMaxHeadXRot());
		if (penguin.distanceToSqr(this.player) < (double)6.25F) {
			penguin.getNavigation().stop();
		} else {
			penguin.getNavigation().moveTo(this.player, 1.0F);
		}

		if (
			this.player.isPassenger()
			//? if >= 1.21.10 {
			&& this.player.getVehicle() instanceof AbstractBoat
			//?} else {
			/*&& this.player.getVehicle() instanceof Boat
			*///?}
			&& this.player.level().getRandom().nextInt(6) == 0
		) {
			this.player.addEffect(new MobEffectInstance(FriendsAndFoesStatusEffects.BOAT_SPEED.holder(), 100), penguin);
		}

	}
}
