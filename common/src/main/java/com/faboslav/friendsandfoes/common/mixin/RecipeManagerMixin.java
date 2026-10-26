package com.faboslav.friendsandfoes.common.mixin;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.crafting.RecipeManager;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesItems;

//? if >=1.21.3 {
import java.util.HashMap;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeHolder;
import net.minecraft.world.item.crafting.RecipeMap;
//?} else {
/*import java.util.Map;
*///?}

//? if >=26.3 {
import net.minecraft.core.HolderLookup;
//?}

@Mixin(RecipeManager.class)
public final class RecipeManagerMixin
{
	//? if >=1.21.3 {
	@Final
	@Mutable
	@Shadow
	private RecipeMap recipes;
	//?} else {
	/*@Unique
	private Map<Identifier, ?> friendsandfoes$jsons;
	*///?}

	@Unique
	private static final Identifier ACACIA_BEEHIVE_ID = FriendsAndFoes.makeID("acacia_beehive");

	//? if >=26.3 {
	@Unique
	private HolderLookup.RegistryLookup<Recipe<?>> friendsandfoes$recipeLookup;

	@Inject(
		method = "<init>",
		at = @At("TAIL")
	)
	private void friendsandfoes$applyDynamicRecipes(
		HolderLookup.Provider registries,
		CallbackInfo ci
	)
	//?} else {
	/*@Inject(
		method = "apply*",
		//? if >= 1.21.3 {
		at = @At("TAIL")
		//?} else {
		/^at = @At("HEAD")
		^///?}
	)
	private void friendsandfoes$applyDynamicRecipes(
		//? if >=1.21.3 {
		RecipeMap recipeMap,
		//?} else {
		/^Map<Identifier, ?> jsons,
		^///?}
		ResourceManager resourceManager,
		ProfilerFiller profiler,
		CallbackInfo ci
	)
	*///?}
	{
		//? if >=26.3 {
		this.friendsandfoes$recipeLookup = registries.lookupOrThrow(Registries.RECIPE);
		//?} else if < 1.21.3 {
		/*this.friendsandfoes$jsons = jsons;
		*///?}

		var config = FriendsAndFoes.getConfig();

		if (!config.enableAcaciaBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.ACACIA_BEEHIVE.getId());
		}

		if (!config.enableBambooBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.BAMBOO_BEEHIVE.getId());
		}

		if (!config.enableBirchBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.BIRCH_BEEHIVE.getId());
		}

		if (!config.enableCherryBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.CHERRY_BEEHIVE.getId());
		}

		if (!config.enableCrimsonBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.CRIMSON_BEEHIVE.getId());
		}

		if (!config.enableDarkOakBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.DARK_OAK_BEEHIVE.getId());
		}

		if (!config.enableJungleBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.JUNGLE_BEEHIVE.getId());
		}

		if (!config.enableMangroveBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.MANGROVE_BEEHIVE.getId());
		}

		if (!config.enableSpruceBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.SPRUCE_BEEHIVE.getId());
		}

		//? if >=1.21.4 {
		if (!config.enablePaleOakBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.PALE_OAK_BEEHIVE.getId());
		}
		//?}

		//? if >=26.3 {
		if (!config.enablePoplarBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.POPLAR_BEEHIVE.getId());
		}
		//?}

		if (!config.enableWarpedBeehive) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.WARPED_BEEHIVE.getId());
		}

		if(!config.enableCopperButtons) {
			friendsandfoes$removeRecipe(FriendsAndFoesItems.COPPER_BUTTON.getId());
			friendsandfoes$removeRecipe(FriendsAndFoes.makeID("waxed_copper_button_from_honeycomb"));
			friendsandfoes$removeRecipe(FriendsAndFoes.makeID("waxed_exposed_copper_button_from_honeycomb"));
			friendsandfoes$removeRecipe(FriendsAndFoes.makeID("waxed_weathered_copper_button_from_honeycomb"));
			friendsandfoes$removeRecipe(FriendsAndFoes.makeID("waxed_oxidized_copper_button_from_honeycomb"));
		}

		//? if < 1.21.3 {
		/*this.friendsandfoes$jsons = null;
		*///?}
	}

	private void friendsandfoes$removeRecipe(Identifier id) {
		//? if >=26.3 {
		ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, id);
		this.friendsandfoes$recipeLookup.get(key).ifPresent(recipe -> {
			this.friendsandfoes$recipeLookup = this.friendsandfoes$recipeLookup.filterElements(element -> element != recipe.value());
			this.recipes = RecipeMap.create(this.friendsandfoes$recipeLookup);
		});
		//?} else if >=1.21.3 {
		/*RecipeMap recipeMap = this.recipes;
		var byKey = new HashMap<ResourceKey<Recipe<?>>, RecipeHolder<?>>();
		for (var holder : recipeMap.values()) {
			byKey.put(holder.id(), holder);
		}
		ResourceKey<Recipe<?>> key = ResourceKey.create(Registries.RECIPE, id);
		byKey.remove(key);
		this.recipes = RecipeMap.create(byKey.values());
		*///?} else {
		/*this.friendsandfoes$jsons.remove(id);
		*///?}
	}
}
