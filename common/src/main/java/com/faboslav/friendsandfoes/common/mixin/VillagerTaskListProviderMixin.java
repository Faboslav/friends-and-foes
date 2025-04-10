package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.ai.brain.task.beekeeper.BeekeeperWorkTask;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesVillagerProfessions;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.ai.behavior.VillagerGoalPackages;
import net.minecraft.world.entity.ai.behavior.WorkAtPoi;
import net.minecraft.world.entity.npc.VillagerProfession;
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
		//? >=1.21.5 {
		Holder<VillagerProfession> professionHolder,
		//?} else {
		/*VillagerProfession profession,
		*///?}
		float f
	) {
		//? >=1.21.5 {
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
