package com.faboslav.friendsandfoes;

import com.faboslav.friendsandfoes.init.ModBlocks;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;

import java.nio.file.Path;

public class FriendsAndFoesPlatform
{
	/**
	 * We can use {@link Platform#getConfigFolder()} but this is just an example of {@link ExpectPlatform}.
	 * <p>
	 * This must be a <b>public static</b> method. The platform-implemented solution must be placed under a
	 * platform sub-package, with its class suffixed with {@code Impl}.
	 * <p>
	 * Example:
	 * Expect: net.examplemod.ExampleExpectPlatform#getConfigDirectory()
	 * Actual Fabric: net.examplemod.fabric.ExampleExpectPlatformImpl#getConfigDirectory()
	 * Actual Forge: net.examplemod.forge.ExampleExpectPlatformImpl#getConfigDirectory()
	 * <p>
	 * <a href="https://plugins.jetbrains.com/plugin/16210-architectury">You should also get the IntelliJ plugin to help with @ExpectPlatform.</a>
	 */
	@ExpectPlatform
	public static Path getConfigDirectory() {
		throw new AssertionError();
	}

	@ExpectPlatform
	public static ModBlocks getModBlocks() {
		throw new AssertionError();
	}
}
