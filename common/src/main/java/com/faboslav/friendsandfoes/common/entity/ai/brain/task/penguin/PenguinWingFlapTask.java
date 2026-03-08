package com.faboslav.friendsandfoes.common.entity.ai.brain.task.penguin;

import com.faboslav.friendsandfoes.common.entity.PenguinEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.PenguinBrain;
import com.faboslav.friendsandfoes.common.entity.animation.PenguinAnimations;
import com.faboslav.friendsandfoes.common.entity.pose.FriendsAndFoesEntityPose;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import com.faboslav.friendsandfoes.common.util.MovementUtil;
import com.faboslav.friendsandfoes.common.versions.VersionedEntity;
import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.behavior.BehaviorUtils;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public final class PenguinWingFlapTask extends Behavior<PenguinEntity>
{
	private final static int WAIT_TICKS = 5;
	private final static int WING_FLAP_DURATION = PenguinAnimations.WING_FLAP.get().lengthInTicks();
	private final static int WING_FLAP_TASK_DURATION = WING_FLAP_DURATION + (2 * WAIT_TICKS);

	@Nullable
	private LivingEntity nearestTarget;
	private int wingFlapTicks = 0;
	private int maxWingFlapTicks = 0;
	private boolean hasDroppedFeather = false;

	public PenguinWingFlapTask() {
		super(
			Map.of(
				MemoryModuleType.NEAREST_VISIBLE_PLAYER, MemoryStatus.VALUE_PRESENT,
				MemoryModuleType.BREED_TARGET, MemoryStatus.VALUE_ABSENT,
				MemoryModuleType.TEMPTING_PLAYER, MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.PENGUIN_WING_FLAP_COOLDOWN.get(), MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.PENGUIN_HAS_EGG.get(), MemoryStatus.VALUE_ABSENT,
				FriendsAndFoesMemoryModuleTypes.PENGUIN_EGG_POS.get(), MemoryStatus.VALUE_ABSENT
			), WING_FLAP_TASK_DURATION
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, PenguinEntity penguin) {
		if (penguin.getNavigation().isInProgress()) {
			return false;
		}

		this.nearestTarget = penguin.getBrain().getMemoryInternal(MemoryModuleType.NEAREST_VISIBLE_PLAYER).orElse(null);

		return true;
	}

	@Override
	protected void start(ServerLevel world, PenguinEntity penguin, long time) {
		MovementUtil.stopMovement(penguin);

		if(this.nearestTarget != null) {
			BehaviorUtils.lookAtEntity(penguin, this.nearestTarget);
			penguin.getLookControl().setLookAt(this.nearestTarget);
		}

		penguin.getLookControl().tick();

		this.hasDroppedFeather = false;
		this.wingFlapTicks = 0;
		this.maxWingFlapTicks = WING_FLAP_TASK_DURATION;
		penguin.startWingFlapAnimation();
	}

	@Override
	protected boolean canStillUse(ServerLevel world, PenguinEntity penguin, long time) {
		return this.wingFlapTicks <= this.maxWingFlapTicks;
	}

	protected void tick(ServerLevel world, PenguinEntity penguin, long time) {
		this.wingFlapTicks++;

		if(this.wingFlapTicks <= WAIT_TICKS) {
			return;
		}

		if (wingFlapTicks % 5 == 0 && penguin.getRandom().nextFloat() > 0.98 && !this.hasDroppedFeather) {
			if (VersionedGameRulesProvider.getBoolean(penguin, VersionedGameRulesProvider.MOB_DROPS)) {
				this.hasDroppedFeather = true;
				VersionedEntity.spawnAtLocation(penguin, FriendsAndFoesItems.PENGUIN_FEATHER.get().getDefaultInstance(), 1);
			}
		}

	}

	@Override
	protected void stop(ServerLevel world, PenguinEntity penguin, long time) {
		penguin.setEntityPose(FriendsAndFoesEntityPose.IDLE);
		PenguinBrain.setWingFlapCooldown(penguin);
	}
}
