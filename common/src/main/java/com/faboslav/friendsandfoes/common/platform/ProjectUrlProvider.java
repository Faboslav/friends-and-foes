package com.faboslav.friendsandfoes.common.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;

public final class ProjectUrlProvider
{
	@ExpectPlatform
	public static String getCurseForgeProjectLink() {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static String getModrinthProjectLink() {
		throw new AssertionError();
	}

	private ProjectUrlProvider() {
	}
}
