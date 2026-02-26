package com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.entity.WildfireEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.WildfireBrain;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.wildfire.WildfireShockwaveAttackTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesStatusEffects;
import com.twelvemonkeys.imageio.metadata.iptc.IPTC;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.EntityTypeTags;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.entity.player.Player;

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
			player == null
			|| !player.isAlive()
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
			this.player == null
			|| !this.player.isAlive()
		) {
			return false;
		}

		return this.player.isPassenger() && penguin.distanceToSqr(this.player) < 256.0D;
	}

	@Override
	protected void tick(ServerLevel serverLevel, PenguinEntity penguin, long time) {
		penguin.getLookControl().setLookAt(this.player, (float)(penguin.getMaxHeadYRot() + 20), (float)penguin.getMaxHeadXRot());
		if (penguin.distanceToSqr(this.player) < (double)6.25F) {
			penguin.getNavigation().stop();
		} else {
			penguin.getNavigation().moveTo(this.player, 1.0F);
		}

		if (this.player.isPassenger() && this.player.level().random.nextInt(6) == 0) {
			this.player.addEffect(new MobEffectInstance(FriendsAndFoesStatusEffects.BOAT_SPEED.holder(), 100), penguin);
		}

	}
}
