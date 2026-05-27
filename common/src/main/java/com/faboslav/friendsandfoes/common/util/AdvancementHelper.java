package com.faboslav.friendsandfoes.common.util;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.advancements.AdvancementProgress;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

public final class AdvancementHelper
{
	public static void triggerWaxOn(Level level, ServerPlayer player) {
		triggerWaxAdvancement(level, player, "minecraft:husbandry/wax_on");
	}

	public static void triggerWaxOff(Level level, ServerPlayer player) {
		triggerWaxAdvancement(level, player, "minecraft:husbandry/wax_off");
	}

	public static void triggerWaxAdvancement(Level level, ServerPlayer player, String stringAdvancementId) {
		if(level.isClientSide()) {
			return;
		}

		var server = level.getServer();

		if(server == null) {
			return;
		}

		Identifier advancementId = FriendsAndFoes.makeNamespacedId(stringAdvancementId);

		//? if >= 1.21.1 {
		var advancement = server.getAdvancements().get(advancementId);
		//?} else {
		/*var advancement = server.getAdvancements().getAdvancement(advancementId);
		 *///?}

		if (advancement == null){
			return;
		}

		AdvancementProgress progress = player.getAdvancements().getOrStartProgress(advancement);
		if (progress.isDone()) {
			return;
		}

		for (String criterion : progress.getRemainingCriteria()) {
			player.getAdvancements().award(advancement, criterion);
		}
	}
}
