package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.Entity;
import net.minecraft.util.ActionResult;

public final class VersionedActionResult
{
	public static ActionResult success(Entity entity) {
		ActionResult actionResult;

		/*? >=1.21.3 {*/
		actionResult = ActionResult.SUCCESS;
		/*?} else {*/
		/*actionResult = ActionResult.success(entity.getWorld().isClient());
		*//*?}*/

		return actionResult;
	}
}
