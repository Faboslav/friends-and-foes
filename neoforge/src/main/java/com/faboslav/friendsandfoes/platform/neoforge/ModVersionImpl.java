package com.faboslav.friendsandfoes.platform.neoforge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.neoforged.fml.ModList;
import org.jetbrains.annotations.Nullable;

public final class ModVersionImpl
{
	@Nullable
	public static String getModVersion() {
		return ModList.get().getModContainerById(FriendsAndFoes.MOD_ID).map(modContainer -> modContainer.getModInfo().getVersion().toString()).orElse(null);
	}
}
