package com.faboslav.friendsandfoes.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public final class Platform
{
	@ExpectPlatform
	public static String getProjectSlug() {
		throw new AssertionError();
	}
}

