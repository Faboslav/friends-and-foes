package com.faboslav.friendsandfoes.common.versions;

import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public final class VersionedProfilerProvider
{
	public static ProfilerFiller getProfiler(@Nullable Entity entity) {
		ProfilerFiller profiler;

		profiler = entity.level().getProfiler();

		return profiler;
	}
}