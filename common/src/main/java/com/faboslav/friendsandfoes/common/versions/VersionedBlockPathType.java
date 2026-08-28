package com.faboslav.friendsandfoes.common.versions;

//? if >= 1.21 {
import net.minecraft.world.level.pathfinder.PathType;
//?} else {
/*import net.minecraft.world.level.pathfinder.BlockPathTypes;
*///?}

public final class VersionedBlockPathType
{
	//? if >= 1.21 {
	public static final PathType RAIL = PathType.RAIL;
	public static final PathType UNPASSABLE_RAIL = PathType.UNPASSABLE_RAIL;
	public static final PathType WATER = PathType.WATER;
	public static final PathType WATER_BORDER = PathType.WATER_BORDER;
	public static final PathType LAVA = PathType.LAVA;
	public static final PathType DOOR_IRON_CLOSED = PathType.DOOR_IRON_CLOSED;
	public static final PathType DOOR_WOOD_CLOSED = PathType.DOOR_WOOD_CLOSED;
	public static final PathType DOOR_OPEN = PathType.DOOR_OPEN;
	public static final PathType COCOA = PathType.COCOA;
	public static final PathType FENCE = PathType.FENCE;
	//? if >= 26.1.2 {
	public static final PathType DANGER_FIRE = PathType.FIRE_IN_NEIGHBOR;
	public static final PathType DAMAGE_FIRE = PathType.FIRE;
	//?} else {
	/*public static final PathType DANGER_FIRE = PathType.DANGER_FIRE;
	public static final PathType DAMAGE_FIRE = PathType.DAMAGE_FIRE;
	*///?}
	//?} else {
	/*public static final BlockPathTypes RAIL = BlockPathTypes.RAIL;
	public static final BlockPathTypes UNPASSABLE_RAIL = BlockPathTypes.UNPASSABLE_RAIL;
	public static final BlockPathTypes WATER = BlockPathTypes.WATER;
	public static final BlockPathTypes WATER_BORDER = BlockPathTypes.WATER_BORDER;
	public static final BlockPathTypes LAVA = BlockPathTypes.LAVA;
	public static final BlockPathTypes DOOR_IRON_CLOSED = BlockPathTypes.DOOR_IRON_CLOSED;
	public static final BlockPathTypes DOOR_WOOD_CLOSED = BlockPathTypes.DOOR_WOOD_CLOSED;
	public static final BlockPathTypes DOOR_OPEN = BlockPathTypes.DOOR_OPEN;
	public static final BlockPathTypes COCOA = BlockPathTypes.COCOA;
	public static final BlockPathTypes FENCE = BlockPathTypes.FENCE;
	public static final BlockPathTypes DANGER_FIRE = BlockPathTypes.DANGER_FIRE;
	public static final BlockPathTypes DAMAGE_FIRE = BlockPathTypes.DAMAGE_FIRE;
	*///?}
}
