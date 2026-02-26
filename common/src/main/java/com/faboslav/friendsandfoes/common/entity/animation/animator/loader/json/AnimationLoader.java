package com.faboslav.friendsandfoes.common.entity.animation.animator.loader.json;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.entity.animation.AnimationDefinition;
import com.google.common.collect.MapMaker;

import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//? if >=1.21.3 {
import net.minecraft.resources.FileToIdConverter;
//?} else {
/*import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mojang.serialization.JsonOps;
*///?}

/**
 * A loader for entity animations written in JSON. You can also get parsed animations from this class.
 *
 * Animation loading related code is based on NeoForge code
 *
 * @author NeoForge team
 * <a href="https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation">https://github.com/neoforged/NeoForge/tree/1.21.x/src/main/java/net/neoforged/neoforge/client/entity/animation</a>
 */
//? if >=1.21.3 {
public final class AnimationLoader extends SimpleJsonResourceReloadListener<AnimationDefinition>
//?} else {
/*public final class AnimationLoader extends SimpleJsonResourceReloadListener
*///?}
{
	public static final AnimationLoader INSTANCE = new AnimationLoader();

	private Map<Identifier, AnimationHolder> animations = new MapMaker().weakValues().concurrencyLevel(1).makeMap();
	@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
	private final List<AnimationHolder> strongHolderReferences = new ArrayList<>();

	private AnimationLoader() {
		//? if >=1.21.4 {
		super(AnimationParser.CODEC, FileToIdConverter.json("friendsandfoes/animations/entity"));
		//?} else {
		/*super(new Gson(), "friendsandfoes/animations/entity");
		*///?}
	}

	public Map<Identifier, AnimationHolder> getAnimations() {
		return animations;
	}

	public void setAnimations(Map<Identifier, AnimationHolder> animations) {
		this.animations = animations;
	}

	/**
	 * Gets a loaded {@link AnimationDefinition} with the specified {@code key}.
	 */
	@Nullable
	public AnimationDefinition getAnimation(Identifier key) {
		final var holder = animations.get(key);
		return holder != null ? holder.getOrNull() : null;
	}

	/**
	 * Returns an {@link AnimationHolder} for an animation. If the specified animation has not been loaded, the holder
	 * will be unbound, but may be bound in the future.
	 */
	public AnimationHolder getAnimationHolder(Identifier key) {
		return animations.computeIfAbsent(key, AnimationHolder::new);
	}

	@Override
	//? if >=1.21.3 {
	protected void apply(Map<Identifier, AnimationDefinition> entityAnimations, ResourceManager resourceManager, ProfilerFiller profiler)
	//?} else {
	/*protected void apply(Map<ResourceLocation, JsonElement> entityAnimationsJson, ResourceManager resourceManager, ProfilerFiller profiler)
	*///?}
	{
		//? if <1.21.3 {
		/*Map<ResourceLocation, AnimationDefinition> entityAnimations = new HashMap<>();

		for (Map.Entry<ResourceLocation, JsonElement> entry : entityAnimationsJson.entrySet()) {
			ResourceLocation resourceLocation = entry.getKey();
			JsonElement animationDefinitionJson = entry.getValue();

			AnimationDefinition animationDefinition = AnimationParser.CODEC.parse(JsonOps.INSTANCE, animationDefinitionJson).getOrThrow();

			entityAnimations.put(resourceLocation, animationDefinition);
		}
		*///?}

		apply(entityAnimations);
	}

	public void apply(Map<Identifier, AnimationDefinition> entityAnimations) {
		animations.values().forEach(AnimationHolder::unbind);
		strongHolderReferences.clear();
		int loaded = 0;
		for (final var entry : entityAnimations.entrySet()) {
			try {
				String animationName = entry.getKey().getPath().substring(entry.getKey().getPath().lastIndexOf('/') + 1);
				final var animationHolder = getAnimationHolder(entry.getKey());
				AnimationDefinition animation;

				var parsedAnimation = entry.getValue();
				animation = new AnimationDefinition(animationName, parsedAnimation.lengthInSeconds(), parsedAnimation.looping(), parsedAnimation.boneAnimations());
				animationHolder.bind(animation);
				strongHolderReferences.add(animationHolder);
				loaded++;
			} catch (Exception e) {
				FriendsAndFoes.getLogger().error("Failed to load animation {}", entry.getKey(), e);
			}
		}
		FriendsAndFoes.getLogger().info("Loaded {} entity animations", loaded);
	}
}