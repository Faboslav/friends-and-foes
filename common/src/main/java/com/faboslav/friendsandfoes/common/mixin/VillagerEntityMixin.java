package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesVillagerProfessions;
import net.minecraft.world.entity.npc.Villager;
import net.minecraft.world.entity.npc.VillagerData;
import net.minecraft.world.entity.npc.VillagerProfession;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Villager.class)
public abstract class VillagerEntityMixin
{
	@Shadow
	public abstract void setVillagerData(VillagerData villagerData);

	@Shadow
	public abstract VillagerData getVillagerData();

	@Inject(
		at = @At("HEAD"),
		method = "tick"
	)
	private void friendsandfoes_tick(CallbackInfo ci) {
		if (
			!FriendsAndFoes.getConfig().enableBeekeeperVillagerProfession
			&& this.getVillagerData().getProfession() == FriendsAndFoesVillagerProfessions.BEEKEEPER.get()
		) {
			this.setVillagerData(this.getVillagerData().setProfession(VillagerProfession.NONE));
		}
	}
}
