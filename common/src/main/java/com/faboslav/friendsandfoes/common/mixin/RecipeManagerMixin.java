package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
//? if >=1.21.3 {
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
//?} else {
/*import net.minecraft.world.item.crafting.RecipeType;
*///?}
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;

@Mixin(RecipeManager.class)
public final class RecipeManagerMixin
{
	@Shadow
	@Final
	@Mutable
	//? if >=1.21.3 {
	private RecipeMap recipes;
	//?} else {
	/*private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes;
	*///?}

	@Unique
	private static final Identifier ACACIA_BEEHIVE_ID = FriendsAndFoes.makeID("acacia_beehive");

	@Inject(
		method = "apply*",
		at = @At("TAIL")
	)
	private void friendsandfoes$applyDynamicRecipes(
		//? if >=1.21.3 {
		RecipeMap recipeMap,
		//?} else {
		/*Map<Identifier, ?> jsons,
		*///?}
		ResourceManager resourceManager,
		ProfilerFiller profiler,
		CallbackInfo ci
	) {

		var config = FriendsAndFoes.getConfig();

		if (!config.enableAcaciaBeehive) {
			removeRecipe(FriendsAndFoesItems.ACACIA_BEEHIVE.getId());
		}

		if (!config.enableBambooBeehive) {
			removeRecipe(FriendsAndFoesItems.BAMBOO_BEEHIVE.getId());
		}

		if (!config.enableBirchBeehive) {
			removeRecipe(FriendsAndFoesItems.BIRCH_BEEHIVE.getId());
		}

		if (!config.enableCherryBeehive) {
			removeRecipe(FriendsAndFoesItems.CHERRY_BEEHIVE.getId());
		}

		if (!config.enableCrimsonBeehive) {
			removeRecipe(FriendsAndFoesItems.CRIMSON_BEEHIVE.getId());
		}

		if (!config.enableDarkOakBeehive) {
			removeRecipe(FriendsAndFoesItems.DARK_OAK_BEEHIVE.getId());
		}

		if (!config.enableJungleBeehive) {
			removeRecipe(FriendsAndFoesItems.JUNGLE_BEEHIVE.getId());
		}

		if (!config.enableMangroveBeehive) {
			removeRecipe(FriendsAndFoesItems.MANGROVE_BEEHIVE.getId());
		}

		if (!config.enableSpruceBeehive) {
			removeRecipe(FriendsAndFoesItems.SPRUCE_BEEHIVE.getId());
		}

		//? if >=1.21.4 {
		if (!config.enablePaleOakBeehive) {
			removeRecipe(FriendsAndFoesItems.PALE_OAK_BEEHIVE.getId());
		}
		//?}

		if (!config.enableWarpedBeehive) {
			removeRecipe(FriendsAndFoesItems.WARPED_BEEHIVE.getId());
		}
	}

	private void removeRecipe(Identifier id) {
		//? if >=1.21.3 {
		RecipeMap recipeMap = this.recipes;
		var byKey = new HashMap<ResourceKey<Recipe<?>>, RecipeHolder<?>>();
		for (var holder : recipeMap.values()) {
			byKey.put(holder.id(), holder);
		}
		ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, id);
		byKey.remove(key);
		this.recipes = RecipeMap.create(byKey.values());
		//?} else {
		/*var map = this.recipes.get(RecipeType.CRAFTING);
		if (map != null) {
			map.remove(id);
		}
		*///?}
	}
}
