package com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import net.minecraft.resources.Identifier;
import org.jetbrains.annotations.Nullable;
import java.util.Map;

/**
 * Holds a single {@link AnimationDefinition} loaded from resource packs. Objects of this class will be automatically updated with new
 * {@link AnimationDefinition}s on reload.
 *
 * Animation loading related code is based on NeoForge code
 *
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
public final class AnimationHolder
{
	public static final AnimationDefinition EMPTY_ANIMATION = new AnimationDefinition("empty", 0f, false, Map.of());

	private final Identifier key;
	@Nullable
	private AnimationDefinition value;
	private boolean absentWarned;

	AnimationHolder(Identifier key) {
		this.key = key;
	}

	void unbind() {
		value = null;
		absentWarned = false;
	}

	void bind(AnimationDefinition value) {
		this.value = value;
	}

	/**
	 * Gets the key associated with this animation.
	 */
	public Identifier key() {
		return key;
	}

	/**
	 * Gets the currently loaded animation. If the animation has not been loaded, returns {@link #EMPTY_ANIMATION}.
	 */
	public AnimationDefinition get() {
		final var result = value;
		if (result == null) {
			if (!absentWarned) {
				absentWarned = true;
				FriendsAndFoes.getLogger().warn("Missing entity animation {}", key);
			}
			return EMPTY_ANIMATION;
		}
		return result;
	}

	/**
	 * Gets the currently loaded animation or null if it has not been loaded.
	 */
	@Nullable
	public AnimationDefinition getOrNull() {
		return value;
	}

	/**
	 * Returns whether the animation has been loaded.
	 */
	public boolean isBound() {
		return value != null;
	}
}