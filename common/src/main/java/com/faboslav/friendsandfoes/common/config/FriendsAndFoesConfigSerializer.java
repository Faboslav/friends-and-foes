package com.faboslav.friendsandfoes.common.config;

import com.faboslav.friendsandfoes.common.FriendsAndFoes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;

public final class FriendsAndFoesConfigSerializer
{
	private static final Path CONFIG_PATH = Path.of("config", FriendsAndFoes.MOD_ID + ".json");
	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	public static void load() {
		try {
			if (Files.exists(CONFIG_PATH)) {
				FriendsAndFoesConfig config = FriendsAndFoes.getConfig();
				FriendsAndFoesConfig loadedConfig = GSON.fromJson(Files.readString(CONFIG_PATH), FriendsAndFoesConfig.class);

				for (Field field : FriendsAndFoesConfig.class.getDeclaredFields()) {
					if (Modifier.isStatic(field.getModifiers())) continue;
					field.setAccessible(true);
					field.set(config, field.get(loadedConfig));
				}
			} else {
				save();
			}
		} catch (Exception e) {
			FriendsAndFoes.getLogger().error("Failed to save config.", e);
		}
	}

	public static void save() {
		try {
			Files.createDirectories(CONFIG_PATH.getParent());
			Files.writeString(CONFIG_PATH, GSON.toJson(FriendsAndFoes.getConfig()));
		} catch (Exception e) {
			FriendsAndFoes.getLogger().error("Failed to save config.", e);
		}
	}
}
