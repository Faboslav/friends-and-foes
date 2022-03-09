package com.faboslav.friendsandfoes.forge;

import com.faboslav.friendsandfoes.FriendsAndFoesPlatform;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;

public class FriendsAndFoesPlatformImpl
{
    /**
     * This is our actual method to {@link FriendsAndFoesPlatform#getConfigDirectory()}.
     */
    public static Path getConfigDirectory() {
        return FMLPaths.CONFIGDIR.get();
    }
}
