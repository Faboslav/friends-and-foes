package com.faboslav.friendsandfoes.common.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.jetbrains.annotations.Nullable;

public final class ModVersion
{
	@ExpectPlatform
	@Nullable
	public static String getModVersion() {
		throw new AssertionError();
	}

	private ModVersion() {
	}
}
