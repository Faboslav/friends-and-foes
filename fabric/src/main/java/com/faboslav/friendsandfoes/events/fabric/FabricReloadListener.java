package com.faboslav.friendsandfoes.events.fabric;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceReloader;
import net.minecraft.util.Identifier;
import net.minecraft.util.profiler.Profiler;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class FabricReloadListener implements IdentifiableResourceReloadListener
{

	private final Identifier id;
	private final ResourceReloader listener;

	public FabricReloadListener(Identifier id, ResourceReloader listener) {
		this.id = id;
		this.listener = listener;
	}

	@Override
	public Identifier getFabricId() {
		return id;
	}

	@Override
	public CompletableFuture<Void> reload(
		Synchronizer synchronizer,
		ResourceManager manager,
		Profiler prepareProfiler,
		Profiler applyProfiler,
		Executor prepareExecutor,
		Executor applyExecutor
	) {
		return listener.reload(synchronizer, manager, prepareProfiler, applyProfiler, prepareExecutor, applyExecutor);
	}
}