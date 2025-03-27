package com.faboslav.friendsandfoes.common.platform;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;

import java.util.ServiceLoader;

public final class PlatformHooks
{
	public static final PlatformCompat PLATFORM_COMPAT = load(PlatformCompat.class);
	public static final PlatformHelper PLATFORM_HELPER = load(PlatformHelper.class);
	public static final ProcessorTypes PROCESSOR_TYPES = load(ProcessorTypes.class);
	public static final BiomeModifications BIOME_MODIFICATIONS = load(BiomeModifications.class);

	public static <T> T load(Class<T> service) {
		T loadedService = ServiceLoader.load(service)
			.findFirst()
			.orElseThrow(() -> new NullPointerException("No implementation found for " + service.getName()));
		FriendsAndFoes.getLogger().info("Loaded {} for service {}", loadedService, service);
		return loadedService;
	}
}
