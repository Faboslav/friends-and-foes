package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.beekeeper.BeekeeperWorkTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesVillagerProfessions;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.behavior.WorkAtPoi;
import net.minecraft.world.entity.npc.villager.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(VillagerGoalPackages.class)
public final class VillagerTaskListProviderMixin
{
	@ModifyVariable(
		method = "getWorkPackage",
		at = @At("STORE"),
		ordinal = 0
	)
	private static WorkAtPoi friendsandfoes_setSecondVillagerWorkTask(
		WorkAtPoi originalTask,
		//? if >=1.21.5 {
		@Local(argsOnly = true) Holder<VillagerProfession> professionHolder
		//?} else {
		/*@Local(argsOnly = true) VillagerProfession profession
		*///?}
	) {
		//? if >=1.21.5 {
		var profession = professionHolder.value();
		//?}
		if (
			FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession
			&& FriendsAndFoesVillagerProfessions.BEEKEEPER != null
			&& profession == FriendsAndFoesVillagerProfessions.BEEKEEPER.get()
		) {
			return new BeekeeperWorkTask();
		}

		return originalTask;
	}
}
