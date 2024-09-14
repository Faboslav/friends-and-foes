package com.faboslav.friendsandfoes.common.platform.neoforge;

import com.faboslav.friendsandfoes.common.platform.ConfigDirectory;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public final class ConfigDirectoryImpl
{
	/**
	 * @see ConfigDirectory#getConfigDirectory()
	 */
	public static Path getConfigDirectory() {
		return FMLPaths.CONFIGDIR.get();
	}
}