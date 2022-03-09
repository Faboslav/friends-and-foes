package com.faboslav.friendsandfoes.util;

public class ServerTickDeltaCounter
{
	public float tickDelta;
	public float lastFrameDuration;
	private long prevTimeMillis;
	private final float tickTime;

	public ServerTickDeltaCounter(
		float f,
		long l
	) {
		this.tickTime = 1000.0F / f;
		this.prevTimeMillis = l;
	}

	public int beginRenderTick(long timeMillis) {
		this.lastFrameDuration = (float) (timeMillis - this.prevTimeMillis) / this.tickTime;
		this.prevTimeMillis = timeMillis;
		this.tickDelta += this.lastFrameDuration;
		int i = (int) this.tickDelta;
		this.tickDelta -= (float) i;
		return i;
	}
}
