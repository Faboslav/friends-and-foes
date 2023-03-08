package com.faboslav.friendsandfoes.client.render.entity.animation.animator.context;

import net.minecraft.entity.AnimationState;

public final class KeyframeAnimationContext
{
	private final AnimationState animationState;
	private final int totalTicks;
	private int initialTick;
	private int currentTick;

	public KeyframeAnimationContext(
		int totalTicks
	) {
		this.totalTicks = totalTicks;
		this.initialTick = 0;
		this.currentTick = 0;
		this.animationState = new AnimationState();
	}

	public void setInitialTick(int initialTick) {
		this.initialTick = initialTick;
	}

	public void setCurrentTick(int currentTick) {
		this.currentTick = currentTick;
	}

	public boolean isRunning() {
		return this.initialTick != 0;
	}

	public boolean isAtLastKeyframe() {
		return initialTick + totalTicks > currentTick;
	}

	public AnimationState getAnimationState() {
		return this.animationState;
	}
}
