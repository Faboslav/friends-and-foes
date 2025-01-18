package com.faboslav.friendsandfoes.common.entity.animation;

import java.util.function.Consumer;
import net.minecraft.util.Mth;

/**
 * Animation loading related code is based on NeoForge code
 *
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
public class AnimationState {
	private static final long STOPPED = Long.MAX_VALUE;
	private long lastTime = Long.MAX_VALUE;
	private long accumulatedTime;

	public AnimationState() {
	}

	public void start(int i) {
		this.lastTime = (long)i * 1000L / 20L;
		this.accumulatedTime = 0L;
	}

	public void startIfStopped(int i) {
		if (!this.isStarted()) {
			this.start(i);
		}
	}

	public void animateWhen(boolean bl, int i) {
		if (bl) {
			this.startIfStopped(i);
		} else {
			this.stop();
		}
	}

	public void stop() {
		this.lastTime = Long.MAX_VALUE;
	}

	public void ifStarted(Consumer<AnimationState> consumer) {
		if (this.isStarted()) {
			consumer.accept(this);
		}

	}

	public void updateTime(float f, float g) {
		if (this.isStarted()) {
			long l = Mth.lfloor((double)(f * 1000.0F / 20.0F));
			this.accumulatedTime += (long)((float)(l - this.lastTime) * g);
			this.lastTime = l;
		}
	}

	public void fastForward(int i, float f) {
		if (this.isStarted()) {
			this.accumulatedTime += (long)((float)(i * 1000) * f) / 20L;
		}
	}

	public long getAccumulatedTime() {
		return this.accumulatedTime;
	}

	public boolean isStarted() {
		return this.lastTime != Long.MAX_VALUE;
	}
}

