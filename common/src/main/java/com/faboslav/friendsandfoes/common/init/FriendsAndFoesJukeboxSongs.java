package com.faboslav.friendsandfoes.common.init;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.JukeboxSong;

/**
 * @see net.minecraft.world.item.JukeboxSongs
 */
public class FriendsAndFoesJukeboxSongs
{
    public static ResourceKey<JukeboxSong> AROUND_THE_CORNER = create("around_the_corner");

    private static ResourceKey<JukeboxSong> create(String id) {
        return ResourceKey.create(Registries.JUKEBOX_SONG, FriendsAndFoes.makeID(id));
    }
}