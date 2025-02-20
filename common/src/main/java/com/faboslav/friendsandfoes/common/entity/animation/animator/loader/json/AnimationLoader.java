package com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json;

import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import com.google.common.collect.MapMaker;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.logging.LogUtils;
import com.mojang.serialization.JsonOps;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//? >=1.21.3 {
import com.faboslav.friendsandfoes.common.client.render.entity.state.GlareRenderState;
//?} else {
//?}

/**
 * A loader for entity animations written in JSON. You can also get parsed animations from this class.
 *
 * Animation loading related code is based on NeoForge code
 *
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
//? >=1.21.3 {
public final class AnimationLoader extends SimpleJsonResourceReloadListener<AnimationDefinition>
//?} else {
/*public final class AnimationLoader extends SimpleJsonResourceReloadListener
*///?}
{
	private static final Logger LOGGER = LogUtils.getLogger();

	public static final AnimationLoader INSTANCE = new AnimationLoader();

	private final Map<ResourceLocation, AnimationHolder> animations = new MapMaker().weakValues().concurrencyLevel(1).makeMap();
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final List<AnimationHolder> strongHolderReferences = new ArrayList<>();

	private AnimationLoader() {
		//? >=1.21.3 {
		super(AnimationParser.CODEC, "animations/entity");
		//?} else {
		/*super(new Gson(), "animations/entity");
		*///?}
	}

	/**
	 * Gets a loaded {@link AnimationDefinition} with the specified {@code key}.
	 */
	@Nullable
	public AnimationDefinition getAnimation(ResourceLocation key) {
		final var holder = animations.get(key);
		return holder != null ? holder.getOrNull() : null;
	}

	/**
	 * Returns an {@link AnimationHolder} for an animation. If the specified animation has not been loaded, the holder
	 * will be unbound, but may be bound in the future.
	 */
	public AnimationHolder getAnimationHolder(ResourceLocation key) {
		return animations.computeIfAbsent(key, AnimationHolder::new);
	}

	@Override
	//? >=1.21.3 {
	protected void apply(Map<ResourceLocation, AnimationDefinition> animationJsons, ResourceManager resourceManager, ProfilerFiller profiler)
	//?} else {
	/*protected void apply(Map<ResourceLocation, JsonElement> animationJsons, ResourceManager resourceManager, ProfilerFiller profiler)
	*///?}
	{
		animations.values().forEach(AnimationHolder::unbind);
		strongHolderReferences.clear();
		int loaded = 0;
		for (final var entry : animationJsons.entrySet()) {
			try {
				String animationName = entry.getKey().getPath().substring(entry.getKey().getPath().lastIndexOf('/') + 1);
				final var animationHolder = getAnimationHolder(entry.getKey());
				AnimationDefinition animation;

				//? >=1.21.3 {
				var parsedAnimation = animationHolder.get();
				animation = new AnimationDefinition(animationName, parsedAnimation.lengthInSeconds(), parsedAnimation.looping(), parsedAnimation.boneAnimations());
				//?} else {
				/*animation = AnimationParser.withName(animationName)
					.parse(JsonOps.INSTANCE, entry.getValue())
					.getOrThrow(JsonParseException::new);
				*///?}
				animationHolder.bind(animation);
				strongHolderReferences.add(animationHolder);
				loaded++;
			} catch (Exception e) {
				LOGGER.error("Failed to load animation {}", entry.getKey(), e);
			}
		}
		LOGGER.info("Loaded {} entity animations", loaded);
	}
}