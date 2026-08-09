package com.faboslav.friendsandfoes.fabric.events;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

import net.minecraft.util.profiling.ProfilerFiller;

public class FabricReloadListener implements IdentifiableResourceReloadListener
{

	private final ResourceLocation id;
	private final PreparableReloadListener listener;

	public FabricReloadListener(ResourceLocation id, PreparableReloadListener listener) {
		this.id = id;
		this.listener = listener;
	}

	@Override
	public ResourceLocation getFabricId() {
		return id;
	}

	@Override
	public CompletableFuture<Void> reload(
		PreparationBarrier barrier,
		ResourceManager manager,

		ProfilerFiller prepareProfiler,
		ProfilerFiller applyProfiler,

		Executor backgroundExecutor,
		Executor gameExecutor
	) {
		return listener.reload(barrier, manager, prepareProfiler, applyProfiler,  backgroundExecutor, gameExecutor);
	}

}