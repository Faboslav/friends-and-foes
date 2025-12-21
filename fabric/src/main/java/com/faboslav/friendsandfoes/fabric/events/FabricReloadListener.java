package com.faboslav.friendsandfoes.fabric.events;

import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

//? if <1.21.3 {
/*import net.minecraft.util.profiling.ProfilerFiller;
 *///?}

public class FabricReloadListener implements IdentifiableResourceReloadListener
{

	private final Identifier id;
	private final PreparableReloadListener listener;

	public FabricReloadListener(Identifier id, PreparableReloadListener listener) {
		this.id = id;
		this.listener = listener;
	}

	@Override
	public Identifier getFabricId() {
		return id;
	}


	//? if <= 1.21.8 {
	/*@Override
	public CompletableFuture<Void> reload(
		PreparationBarrier barrier,
		ResourceManager manager,
		//? if <1.21.3 {
		/^ProfilerFiller prepareProfiler,
		ProfilerFiller applyProfiler,
		^///?}
		Executor backgroundExecutor,
		Executor gameExecutor
	) {
		return listener.reload(barrier, manager, /^? if <1.21.3 {^//^prepareProfiler, applyProfiler, ^//^?}^/ backgroundExecutor, gameExecutor);
	}
	*///?} else {
	@Override
	public CompletableFuture<Void> reload(
		SharedState sharedState,
		Executor exectutor,
		PreparationBarrier barrier,
		Executor applyExectutor
	) {
		return listener.reload(sharedState, exectutor, barrier, applyExectutor);
	}
	//?}
}