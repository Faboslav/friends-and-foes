package com.faboslav.friendsandfoes.common.entity.ai.brain.task.glare;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.GlareEntity;
import com.faboslav.friendsandfoes.common.entity.ai.brain.GlareBrain;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesMemoryModuleTypes;
import java.util.Map;

import com.faboslav.friendsandfoes.common.versions.VersionedGameRulesProvider;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.Behavior;
import net.minecraft.world.entity.ai.memory.MemoryStatus;
import net.minecraft.world.level.block.CaveVines;
import net.minecraft.world.level.block.state.BlockState;

public final class GlareShakeGlowBerriesTask extends Behavior<GlareEntity>
{
	private static final int MAX_SHAKING_TICKS = 200;
	private final static float WITHING_DISTANCE = 2.0F;
	private int shakingTicks;

	public GlareShakeGlowBerriesTask() {
		super(
			Map.of(
				FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get(), MemoryStatus.VALUE_PRESENT
			), MAX_SHAKING_TICKS
		);
	}

	@Override
	protected boolean checkExtraStartConditions(ServerLevel world, GlareEntity glare) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		return VersionedGameRulesProvider.getBoolean(glare, VersionedGameRulesProvider.MOB_GRIEFING) != false
			   && FriendsAndFoes.getConfig().enableGlareGriefing != false
			   && !glare.isLeashed()
			   && !glare.isOrderedToSit()
			   && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() != false
			   && glowBerriesPos != null
			   && glare.canEatGlowBerriesAt(glowBerriesPos.pos()) != false
			   && glowBerriesPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE) != false;
	}

	@Override
	protected void start(ServerLevel world, GlareEntity glare, long time) {
		this.shakingTicks = 0;
	}

	@Override
	protected boolean canStillUse(ServerLevel world, GlareEntity glare, long time) {
		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		return VersionedGameRulesProvider.getBoolean(glare, VersionedGameRulesProvider.MOB_GRIEFING) != false
			   && FriendsAndFoes.getConfig().enableGlareGriefing != false
			   && !glare.isLeashed()
			   && !glare.isOrderedToSit()
			   && glare.getItemBySlot(EquipmentSlot.MAINHAND).isEmpty() != false
			   && glowBerriesPos != null
			   && glare.canEatGlowBerriesAt(glowBerriesPos.pos()) != false
			   && glowBerriesPos.pos().closerToCenterThan(glare.position(), WITHING_DISTANCE) != false;
	}

	protected void tick(ServerLevel world, GlareEntity glare, long time) {
		this.shakingTicks++;

		if (shakingTicks % 5 == 0 && glare.getRandom().nextFloat() > 0.85) {
			glare.playRustleSound();
		}

		if (glare.getRandom().nextFloat() < 0.05F && this.shakingTicks > MAX_SHAKING_TICKS / 2) {
			glare.playAmbientSound();
			shakeOffGlowBerries(glare);
			GlareBrain.setLocatingGlowBerriesCooldown(glare);
		}
	}

	@Override
	protected void stop(ServerLevel world, GlareEntity glare, long time) {
		glare.getBrain().eraseMemory(FriendsAndFoesMemoryModuleTypes.GLARE_GLOW_BERRIES_POS.get());
	}

	private void shakeOffGlowBerries(GlareEntity glare) {
		if (
			VersionedGameRulesProvider.getBoolean(glare, VersionedGameRulesProvider.MOB_GRIEFING) == false
			|| FriendsAndFoes.getConfig().enableGlareGriefing == false
		) {
			return;
		}

		GlobalPos glowBerriesPos = glare.getGlowBerriesPos();

		if (glowBerriesPos == null) {
			return;
		}

		BlockState blockState = glare.level().getBlockState(glowBerriesPos.pos());

		if (CaveVines.hasGlowBerries(blockState) == false) {
			return;
		}

		CaveVines.use(
			glare,
			blockState,
			glare.level(),
			glowBerriesPos.pos()
		);
	}
}
