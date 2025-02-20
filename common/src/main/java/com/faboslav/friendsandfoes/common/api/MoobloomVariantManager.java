package com.faboslav.friendsandfoes.common.api;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.faboslav.friendsandfoes.common.init.FriendsAndFoesBlocks;
import com.faboslav.friendsandfoes.common.tag.FriendsAndFoesTags;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.tags.TagKey;
import net.minecraft.util.RandomSource;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.BushBlock;

//? >=1.21.3 {
import net.minecraft.util.ExtraCodecs;
//?}

//? >=1.21.3 {
public final class MoobloomVariantManager extends SimpleJsonResourceReloadListener<JsonElement>
//?} else {
/*public final class MoobloomVariantManager extends SimpleJsonResourceReloadListener
*///?}
{
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().setLenient().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
	private static final MoobloomVariant DEFAULT_MOOBLOOM_VARIANT = new MoobloomVariant("buttercup", FriendsAndFoesBlocks.BUTTERCUP.get(), FriendsAndFoesTags.HAS_MOOBLOOMS);
	public static final MoobloomVariantManager MOOBLOOM_VARIANT_MANAGER = new MoobloomVariantManager();

	private List<MoobloomVariant> moobloomVariants = new ArrayList<>()
	{
		{
			add(DEFAULT_MOOBLOOM_VARIANT);
		}
	};

	private MoobloomVariantManager() {
		//? >=1.21.3 {
		super(ExtraCodecs.JSON, "moobloom_variants");
		//?} else {
		/*super(GSON, "moobloom_variants");
		*///?}
	}

	@Override
	//? >=1.21.3 {
	protected void apply(Map<ResourceLocation, JsonElement> loader, ResourceManager resourceManager, ProfilerFiller profiler)
	//?} else {
	/*protected void apply(Map<ResourceLocation, JsonElement> loader, ResourceManager resourceManager, ProfilerFiller profiler)
	*///?}
	{
		List<MoobloomVariant> parsedMoobloomVariants = new ArrayList<>();
		parsedMoobloomVariants.add(DEFAULT_MOOBLOOM_VARIANT);

		loader.forEach((fileIdentifier, jsonElement) -> {
			try {
				JsonObject jsonObject = GSON.fromJson(jsonElement.getAsJsonObject(), JsonObject.class);
				if (jsonObject != null) {
					String name = jsonObject.get("name").getAsString();
					String flowerBlockRaw = jsonObject.get("flower_block").getAsString();
					//? >=1.21.3 {
					BushBlock flowerBlock = (BushBlock) BuiltInRegistries.BLOCK.getValue(ResourceLocation.parse(flowerBlockRaw));
					//?} else {
					/*BushBlock flowerBlock = (BushBlock) BuiltInRegistries.BLOCK.get(ResourceLocation.parse(flowerBlockRaw));
					*///?}
					String biomes = jsonObject.get("biomes").getAsString();

					TagKey<Biome> biomesValue = TagKey.create(Registries.BIOME, ResourceLocation.parse(biomes.replaceFirst("#", "")));
					parsedMoobloomVariants.add(new MoobloomVariant(name, flowerBlock, biomesValue));
				}
			} catch (Exception e) {
				FriendsAndFoes.getLogger().error("Friends&Foes Error: Couldn't parse moobloom variant {}", fileIdentifier, e);
			}
		});

		this.setMoobloomVariants(parsedMoobloomVariants);
	}

	public void setMoobloomVariants(List<MoobloomVariant> moobloomVariants) {
		this.moobloomVariants = moobloomVariants;
	}

	public List<MoobloomVariant> getMoobloomVariants() {
		return this.moobloomVariants;
	}

	public MoobloomVariant getDefaultMoobloomVariant() {
		return this.getMoobloomVariantByName(DEFAULT_MOOBLOOM_VARIANT.getName());
	}

	public MoobloomVariant getRandomMoobloomVariant(RandomSource random) {
		Object[] values = this.getMoobloomVariants().toArray();
		int min = 0;
		int max = values.length - 1;
		return (MoobloomVariant) values[random.nextInt((max - min) + 1) + min];
	}

	@Nullable
	public MoobloomVariant getMoobloomVariantByName(String name) {
		for (MoobloomVariant moobloomVariant : this.getMoobloomVariants()) {
			if (Objects.equals(moobloomVariant.getName(), name)) {
				return moobloomVariant;
			}
		}

		return null;
	}

	@Nullable
	public MoobloomVariant getRandomBiomeSpecificMoobloomVariant(
		ServerLevelAccessor serverWorldAccess,
		BlockPos blockPos
	) {
		List<MoobloomVariant> possibleMoobloomVariants = new ArrayList<>();

		var biome = serverWorldAccess.getBiome(blockPos);

		for (MoobloomVariant moobloomVariant : this.getMoobloomVariants()) {
			if (!biome.is(moobloomVariant.getBiomes())) {
				continue;
			}

			possibleMoobloomVariants.add(moobloomVariant);
		}

		if (possibleMoobloomVariants.isEmpty()) {
			return null;
		}

		return possibleMoobloomVariants.get(serverWorldAccess.getRandom().nextInt(possibleMoobloomVariants.size()));
	}

	@Nullable
	public MoobloomVariant getByFlowerItem(Item flowerItem) {
		for (MoobloomVariant moobloomVariant : this.getMoobloomVariants()) {
			if (moobloomVariant.getFlowerAsItem() == flowerItem) {
				return moobloomVariant;
			}
		}

		return null;
	}
}
