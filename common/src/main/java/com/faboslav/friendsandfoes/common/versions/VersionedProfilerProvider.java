package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

//? if >=1.21.3 {
import net.minecraft.util.profiling.Profiler;
//?}

public final class VersionedProfilerProvider
{
	public static ProfilerFiller getProfiler(@Nullable Entity entity) {
		ProfilerFiller profiler;

		/*? if >=1.21.3 {*/
		profiler = Profiler.get();
		/*?} else {*/
		/*profiler = entity.level().getProfiler();
		 *//*?}*/

		return profiler;
	}
}