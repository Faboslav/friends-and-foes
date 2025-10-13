package com.faboslav.friendsandfoes.common.entity.ai.brain.task.beekeeper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.GlobalPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.behavior.WorkAtPoi;
import net.minecraft.world.entity.ai.memory.MemoryModuleType;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.BeehiveBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public final class BeekeeperWorkTask extends WorkAtPoi
{
	private Villager villagerEntity;

	public BeekeeperWorkTask() {
		super();
	}

	protected void start(ServerLevel serverWorld, Villager villagerEntity, long l) {
		super.start(serverWorld, villagerEntity, l);

		GlobalPos beehiveGlobalPos = getBeehiveGlobalPos(villagerEntity);
		if (beehiveGlobalPos == null) {
			return;
		}

		BlockState beehiveBlockState = serverWorld.getBlockState(beehiveGlobalPos.pos());
		if (this.canHarvestHoney(beehiveBlockState) == false) {
			return;
		}

		villagerEntity.setItemSlot(EquipmentSlot.MAINHAND, new ItemStack(Items.SHEARS));
		villagerEntity.startUsingItem(InteractionHand.MAIN_HAND);

		this.villagerEntity = villagerEntity;
	}

	protected void stop(ServerLevel serverWorld, Villager villagerEntity, long l) {
		villagerEntity.setItemSlot(EquipmentSlot.MAINHAND, ItemStack.EMPTY);
		super.stop(serverWorld, villagerEntity, l);
	}

	protected void useWorkstation(ServerLevel serverWorld, Villager villagerEntity) {
		GlobalPos beehiveGlobalPos = getBeehiveGlobalPos(villagerEntity);
		if (beehiveGlobalPos == null) {
			return;
		}

		BlockState beehiveBlockState = serverWorld.getBlockState(beehiveGlobalPos.pos());
		if (this.canHarvestHoney(beehiveBlockState) == false) {
			return;
		}

		this.harvestHoney(serverWorld, beehiveGlobalPos, beehiveBlockState);
	}

	@Nullable
	private GlobalPos getBeehiveGlobalPos(Villager entity) {
		return entity.getBrain().getMemory(MemoryModuleType.JOB_SITE).orElse(null);
	}

	private boolean canHarvestHoney(BlockState beehiveBlockState) {
		return beehiveBlockState != null
			   && (beehiveBlockState.getBlock() instanceof BeehiveBlock)
			   && beehiveBlockState.getValue(BeehiveBlock.HONEY_LEVEL) == BeehiveBlock.MAX_HONEY_LEVELS;
	}

	private void harvestHoney(ServerLevel world, GlobalPos globalPos, BlockState beehiveState) {
		BlockPos blockPos = globalPos.pos();
		world.setBlock(blockPos, beehiveState.setValue(BeehiveBlock.HONEY_LEVEL, 0), 3);
		world.playSound(null, blockPos.getX(), blockPos.getY(), blockPos.getZ(), SoundEvents.BEEHIVE_SHEAR, SoundSource.NEUTRAL, 1.0F, 1.0F);

		//? if >= 1.21.9 {
		/*BeehiveBlock.dropHoneycomb(world, Items.SHEARS.getDefaultInstance(), beehiveState, world.getBlockEntity(blockPos), this.villagerEntity, blockPos);
		*///?} else {
		BeehiveBlock.dropHoneycomb(world, blockPos);
		//?}
	}
}
