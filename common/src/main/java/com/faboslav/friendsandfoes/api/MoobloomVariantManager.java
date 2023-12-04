package com.faboslav.friendsandfoes.api;

import com.faboslav.friendsandfoes.FriendsAndFoes;
import com.google.common.collect.ImmutableList;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.block.PlantBlock;
import net.minecraft.item.Item;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.tag.TagKey;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MoobloomVariantManager extends JsonDataLoader
{
	private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().setLenient().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().create();
	private static List<MoobloomVariant> MOOBLOOM_VARIANTS = ImmutableList.of();
	private static final String DEFAULT_MOOBLOOM_VARIANT_NAME = "buttercup";

	public static final MoobloomVariantManager MOOBLOOM_VARIANT_MANAGER = new MoobloomVariantManager();

	public MoobloomVariantManager() {
		super(GSON, "moobloom_variants");
	}

	@Override
	protected void apply(Map<Identifier, JsonElement> loader, ResourceManager manager, Profiler profiler) {
		MOOBLOOM_VARIANTS = ImmutableList.of();
		ImmutableList.Builder<MoobloomVariant> builder = ImmutableList.builder();

		loader.forEach((fileIdentifier, jsonElement) -> {
			try {
				JsonObject jsonObject = GSON.fromJson(jsonElement.getAsJsonObject(), JsonObject.class);
				if (jsonObject != null) {
					String name = jsonObject.get("name").getAsString();
					String flowerBlockRaw = jsonObject.get("flower_block").getAsString();
					PlantBlock flowerBlock = (PlantBlock) Registry.BLOCK.get(new Identifier(flowerBlockRaw));
					String biomes = jsonObject.get("biomes").getAsString();

					TagKey<Biome> biomesValue = TagKey.of(Registry.BIOME_KEY, new Identifier(biomes.replaceFirst("#", "")));
					builder.add(new MoobloomVariant(name, flowerBlock, biomesValue));
				}
			} catch (Exception e) {
				FriendsAndFoes.getLogger().error("Friends&Foes Error: Couldn't parse moobloom variant {}", fileIdentifier, e);
			}
		});

		MOOBLOOM_VARIANTS = builder.build();
	}

	public static List<MoobloomVariant> getMoobloomVariants() {
		return MOOBLOOM_VARIANTS;
	}

	public static MoobloomVariant getDefaultMoobloomVariant() {
		return getMoobloomVariantByName(DEFAULT_MOOBLOOM_VARIANT_NAME);
	}

	public static MoobloomVariant getRandomMoobloomVariant(Random random) {
		Object[] values = MOOBLOOM_VARIANTS.toArray();
		int min = 0;
		int max = values.length - 1;
		return (MoobloomVariant) values[random.nextInt((max - min) + 1) + min];
	}

	@Nullable
	public static MoobloomVariant getMoobloomVariantByName(String name) {
		for (MoobloomVariant moobloomVariant : MOOBLOOM_VARIANTS) {
			if (Objects.equals(moobloomVariant.getName(), name)) {
				return moobloomVariant;
			}
		}

		return null;
	}

	@Nullable
	public static MoobloomVariant getRandomBiomeSpecificMoobloomVariant(
		ServerWorldAccess serverWorldAccess,
		BlockPos blockPos
	) {
		List<MoobloomVariant> possibleMoobloomVariants = new ArrayList<>();

		var biome = serverWorldAccess.getBiome(blockPos);

		for (MoobloomVariant moobloomVariant : MOOBLOOM_VARIANTS) {
			if (biome.isIn(moobloomVariant.getBiomes()) == false) {
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
	public static MoobloomVariant getByFlowerItem(Item flowerItem) {
		for (MoobloomVariant moobloomVariant : MOOBLOOM_VARIANTS) {
			if (moobloomVariant.getFlowerAsItem() == flowerItem) {
				return moobloomVariant;
			}
		}

		return null;
	}
}
