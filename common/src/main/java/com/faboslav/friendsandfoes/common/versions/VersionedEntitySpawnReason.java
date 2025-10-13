package com.faboslav.friendsandfoes.common.versions;

/*? if >=1.21.3 {*/
import net.minecraft.world.entity.EntitySpawnReason;
 /*?} else {*/
/*import net.minecraft.world.entity.MobSpawnType;
*//*?}*/

public class VersionedEntitySpawnReason
{
	/*? if >=1.21.3 {*/
	public static final EntitySpawnReason NATURAL = EntitySpawnReason.NATURAL;
	public static final EntitySpawnReason SPAWNER = EntitySpawnReason.SPAWNER;
	public static final EntitySpawnReason CHUNK_GENERATION = EntitySpawnReason.CHUNK_GENERATION;
	public static final EntitySpawnReason STRUCTURE = EntitySpawnReason.STRUCTURE;
	public static final EntitySpawnReason COMMAND = EntitySpawnReason.COMMAND;
	public static final EntitySpawnReason MOB_SUMMONED = EntitySpawnReason.MOB_SUMMONED;
	public static final EntitySpawnReason PATROL = EntitySpawnReason.PATROL;
	public static final EntitySpawnReason TRIGGERED = EntitySpawnReason.TRIGGERED;
	public static final EntitySpawnReason EVENT = EntitySpawnReason.EVENT;
	public static final EntitySpawnReason BREEDING = EntitySpawnReason.BREEDING;
	public static final EntitySpawnReason CONVERSION = EntitySpawnReason.CONVERSION;
	/*?} else {*/
	/*public static final MobSpawnType NATURAL = MobSpawnType.NATURAL;
	public static final MobSpawnType SPAWNER = MobSpawnType.SPAWNER;
	public static final MobSpawnType CHUNK_GENERATION = MobSpawnType.CHUNK_GENERATION;
	public static final MobSpawnType STRUCTURE = MobSpawnType.STRUCTURE;
	public static final MobSpawnType COMMAND = MobSpawnType.COMMAND;
	public static final MobSpawnType MOB_SUMMONED = MobSpawnType.MOB_SUMMONED;
	public static final MobSpawnType PATROL = MobSpawnType.PATROL;
	public static final MobSpawnType TRIGGERED = MobSpawnType.TRIGGERED;
	public static final MobSpawnType EVENT = MobSpawnType.EVENT;
	public static final MobSpawnType BREEDING = MobSpawnType.BREEDING;
	public static final MobSpawnType CONVERSION = MobSpawnType.CONVERSION;
	*//*?}*/
}
