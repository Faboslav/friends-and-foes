package com.faboslav.friendsandfoes.common.events.entity;

import com.faboslav.friendsandfoes.common.events.base.CancellableEventHandler;

import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.LevelAccessor;

//? if >=1.21.3 {
import net.minecraft.world.entity.EntitySpawnReason;
//?} else {
/*import net.minecraft.world.entity.MobSpawnType;
*///?}

/**
 * Event related is code based on The Bumblezone/Resourceful Lib mods with permissions from the authors
 *
 * @author TelepathicGrunt
 * <a href="https://github.com/TelepathicGrunt/Bumblezone">https://github.com/TelepathicGrunt/Bumblezone</a>
 * @author ThatGravyBoat
 * <a href="https://github.com/Team-Resourceful/ResourcefulLib">https://github.com/Team-Resourceful/ResourcefulLib</a>
 */
//? if >=1.21.3 {
public record EntitySpawnEvent(Mob entity, LevelAccessor worldAccess, boolean isBaby, EntitySpawnReason spawnReason)
//?} else {
/*public record EntitySpawnEvent(Mob entity, LevelAccessor worldAccess, boolean isBaby, MobSpawnType spawnReason)
*///?}
{
	public static final CancellableEventHandler<EntitySpawnEvent> EVENT = new CancellableEventHandler<>();
}
