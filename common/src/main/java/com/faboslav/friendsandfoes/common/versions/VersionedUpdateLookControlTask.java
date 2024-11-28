package com.faboslav.friendsandfoes.common.versions;

/*? >=1.21.2 {*/
import net.minecraft.entity.ai.brain.task.UpdateLookControlTask;
/*?} else {*/
/*import net.minecraft.entity.ai.brain.task.LookAroundTask;
*//*?}*/

public final class VersionedUpdateLookControlTask
{
	/*? >=1.21.2 {*/
	public static UpdateLookControlTask create(int minRunTime, int maxRunTime)
	/*?} else {*/
	/*public static LookAroundTask create(int minRunTime, int maxRunTime)
	*//*?}*/
	{
		/*? >=1.21.2 {*/
		return new UpdateLookControlTask(minRunTime, maxRunTime);
		/*?} else {*/
		/*return new LookAroundTask(minRunTime, maxRunTime);
		*//*?}*/
	}
}