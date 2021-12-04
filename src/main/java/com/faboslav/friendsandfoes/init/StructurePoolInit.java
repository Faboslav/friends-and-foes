package com.faboslav.friendsandfoes.init;

import com.faboslav.friendsandfoes.config.Settings;
import com.google.common.collect.ImmutableList;
import com.mojang.datafixers.util.Pair;
import net.minecraft.structure.pool.StructurePool;
import net.minecraft.structure.pool.StructurePoolElement;
import net.minecraft.structure.pool.StructurePools;
import net.minecraft.util.Identifier;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

@Environment(EnvType.SERVER)
public class StructurePoolInit {
    public static void init() {
        StructurePools.register(
                new StructurePool(
                        Settings.makeID("village/common/bee"),
                        new Identifier("empty"),
                        ImmutableList.of(Pair.of(StructurePoolElement.ofLegacySingle(Settings.makeStringID("village/common/animals/bee")), 1)),
                        StructurePool.Projection.RIGID
                )
        );
    }
}