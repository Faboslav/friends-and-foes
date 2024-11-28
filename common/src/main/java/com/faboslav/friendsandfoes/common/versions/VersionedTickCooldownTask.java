package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.ai.brain.MemoryModuleType;
import net.minecraft.entity.ai.brain.task.MultiTickTask;

/*? >=1.21.2 {*/
import net.minecraft.entity.ai.brain.task.TickCooldownTask;
import net.minecraft.entity.ai.brain.task.UpdateLookControlTask;
/*?} else {*/
/*import net.minecraft.entity.ai.brain.task.TemptationCooldownTask;
 *//*?}*/

public final class VersionedTickCooldownTask
{
	/*? >=1.21.2 {*/
	public static TickCooldownTask create(MemoryModuleType<Integer> memoryModuleType)
	/*?} else {*/
	/*public static TemptationCooldownTask create(MemoryModuleType<Integer> memoryModuleType)
	*//*?}*/
	{
		/*? >=1.21.2 {*/
		return new TickCooldownTask(memoryModuleType);
		/*?} else {*/
		/*return new TemptationCooldownTask(memoryModuleType);
		*//*?}*/
	}
}