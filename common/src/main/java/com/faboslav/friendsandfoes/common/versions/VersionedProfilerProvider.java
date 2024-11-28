package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.entity.Entity;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.util.profiler.Profiler;
/*? >=1.21.3 {*/
import net.minecraft.util.profiler.Profilers;
/*?}*/

public final class VersionedProfilerProvider
{
	public static Profiler getProfiler(Entity entity) {
		Profiler profiler;

		/*? >=1.21.3 {*/
		profiler = Profilers.get();
		 /*?} else {*/
		/*profiler = entity.getWorld().getProfiler();
		*//*?}*/

		return profiler;
	}
}
