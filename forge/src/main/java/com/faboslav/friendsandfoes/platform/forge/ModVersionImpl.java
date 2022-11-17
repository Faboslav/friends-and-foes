package com.faboslav.friendsandfoes.platform.forge;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.Nullable;

public final class ModVersionImpl
{
	@Nullable
	public static String getModVersion() {
		return ModList.get().getModContainerById(FriendsAndFoes.MOD_ID).map(modContainer -> modContainer.getModInfo().getVersion().toString()).orElse(null);
	}
}
