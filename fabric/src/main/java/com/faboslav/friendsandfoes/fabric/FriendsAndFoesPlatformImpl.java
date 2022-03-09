package com.faboslav.friendsandfoes.fabric;

import com.faboslav.friendsandfoes.FriendsAndFoesPlatform;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Path;

public class FriendsAndFoesPlatformImpl
{
    /**
     * This is our actual method to {@link FriendsAndFoesPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FabricLoader.getInstance().getConfigDir();
    }
}
